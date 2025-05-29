#include "scheduler.h"
/* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Global Variables
Circular queue for each priority level
Parameters taken from command line (num_consumers, queue_size, num_tasks)
Tasks produced, consumed, and next task id.
Mutexes for producer, coonsumer and id.
Volatile integer to check if all tasks are produced and consumed.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - */
TaskQueue queues[NUM_PRIORITIES];
int num_producers, num_consumers, queue_size, num_tasks;
int tasks_produced = 0;
int tasks_consumed = 0;
int next_task_id = 1;
pthread_mutex_t prod_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t cons_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t id_mutex = PTHREAD_MUTEX_INITIALIZER;
volatile int all_tasks_produced = 0;
int *producer_counts; // Her producer'ın ürettiği task sayısı
int *consumer_counts; // Her consumer'ın consume ettiği task sayısı



/* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Log functions to print producer and consumer messages.
I used ANSI escape codes to color the messages.
I used fflush(stdout) to flush the output buffer
- - - - - - - - - - - - - - - - - - - - - - - - - - - - */
void log_enqueue(int producer_id, int task_id, int priority, int qsize, int qmax) {
    printf("\033[1;32m[Producer %d]\033[0m \033[1;37m-> TaskID: %2d | Priority: %d |\033[0m \033[1;33mQueue[%d]: %2d/%2d\033[0m\n",
          producer_id, task_id, priority, priority, qsize, qmax);
    fflush(stdout);
}

void log_dequeue(int consumer_id, int task_id, int priority, int qsize, int qmax) {
    printf("\033[1;34m[Consumer %d]\033[0m \033[1;37m<- TaskID: %2d | Priority: %d |\033[0m \033[1;33mQueue[%d]: %2d/%2d\033[0m\n",
          consumer_id, task_id, priority, priority, qsize, qmax);
    fflush(stdout);
}



/* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Queue operations:
queue init: Starts with empty queue, mutex, semaphores.
queue destroy: Frees the queue, destroys the mutex and semaphores.
queue enqueue: Adds a task to the queue.
queue dequeue: Removes a task from the queue.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - */
void queue_init(TaskQueue *q, int max_size) {
    q->tasks = (Task *)malloc(sizeof(Task) * max_size);
    q->head = q->tail = q->count = 0;
    q->max_size = max_size;
    pthread_mutex_init(&q->mutex, NULL); // Mutex for protecting the queue
    sem_init(&q->items, 0, 0); // Semaphore for counting items in the queue
    sem_init(&q->slots, 0, max_size); // Semaphore for counting available slots in the queue (max_size)
}

void queue_destroy(TaskQueue *q) {
    free(q->tasks);
    pthread_mutex_destroy(&q->mutex);  // Destroy the mutex
    sem_destroy(&q->items); // Destroy the items semaphore
    sem_destroy(&q->slots); // Destroy the slots semaphore
}

void queue_enqueue(TaskQueue *q, Task t) {
    q->tasks[q->tail] = t; // Add task to the tail of the queue
    q->tail = (q->tail + 1) % q->max_size; // Move tail forward in a circular manner
    q->count++; // Increment the count of tasks in the queue
}

Task queue_dequeue(TaskQueue *q) {
    Task t = q->tasks[q->head]; // Get the task from the head of the queue
    q->head = (q->head + 1) % q->max_size; // Move head forward in a circular manner if necessary goes to the beginning
    q->count--; // Decrement the count of tasks in the queue
    return t;
}



/* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Producer Thread
This thread produces tasks and enqueues them into the appropriate priority queue.
It generates a task ID, assigns a random priority, and enqueues the task.
It uses semaphores to manage the queue slots and items.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - */
void *producer_thread(void *arg) {
    int producer_id = *(int *)arg;
    int produced = 0; // Number of tasks produced by each producer
    while (1) {
        pthread_mutex_lock(&prod_mutex);
        if (tasks_produced >= num_tasks) { // Check if all tasks are produced
            pthread_mutex_unlock(&prod_mutex); // Unlock before exiting
            break; // Exit if all tasks are produced
        }
        int task_id;
        pthread_mutex_lock(&id_mutex); // Lock the ID mutex to safely increment task_id
        task_id = next_task_id++; // Get the next task ID
        pthread_mutex_unlock(&id_mutex); // Unlock the ID mutex
        tasks_produced++; // Increment the total number of tasks produced
        pthread_mutex_unlock(&prod_mutex); // Unlock the producer mutex

        int priority = rand() % NUM_PRIORITIES; // Randomly assign a priority (0: High, 1: Medium, 2: Low)
        Task t = {task_id, producer_id, priority}; // Create a new task with the generated ID, producer ID, and priority


        sem_wait(&queues[priority].slots); // Wait for an available slot in the queue
        pthread_mutex_lock(&queues[priority].mutex); // Lock the queue mutex to safely enqueue the task
        queue_enqueue(&queues[priority], t); // Enqueue the task into the appropriate priority queue
        log_enqueue(producer_id, task_id, priority, queues[priority].count, queues[priority].max_size); // Log the enqueue operation
        pthread_mutex_unlock(&queues[priority].mutex); // Unlock the queue mutex
        sem_post(&queues[priority].items); // Signal that an item has been added to the queue, consumer waits for this semaphore

        produced++; // Increment the number of tasks produced by each producer
        usleep(100000); // 100ms
    }
    producer_counts[producer_id - 1] = produced; // Store the number of tasks produced by this producer
    free(arg); // Free the allocated memory from the argument taken by the malloc() in main
    return NULL;
}



/* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Consumer Thread
This thread consumes tasks from the highest priority queue available.
It tries to dequeue tasks from the queues in order of priority (High, Medium, Low).
It uses semaphores to manage the queue items and slots.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - */
void *consumer_thread(void *arg) {
    int consumer_id = *(int *)arg;
    int consumed = 0; // Number of tasks consumed by each consumer

    while (1) {
        int found = 0;

        for (int p = 0; p < NUM_PRIORITIES; ++p) {
            int value;
            sem_getvalue(&queues[p].items, &value); // Get the current number of items in the queue
            if (value > 0) { // If there are items in the queue
                sem_wait(&queues[p].items); // Wait for an item to be available in the queue
                pthread_mutex_lock(&queues[p].mutex); // Lock the queue mutex to safely dequeue the task
                Task t = queue_dequeue(&queues[p]); // Dequeue the task from the queue
                pthread_mutex_unlock(&queues[p].mutex); // Unlock the queue mutex
                sem_post(&queues[p].slots); // Signal that a slot has been freed in the queue

                pthread_mutex_lock(&cons_mutex); // Lock the consumer mutex to safely update shared variables
                tasks_consumed++; // Increment the total number of tasks consumed
                int done = (tasks_consumed >= num_tasks); // Check if all tasks have been consumed (donet = true)
                pthread_mutex_unlock(&cons_mutex); // Unlock the consumer mutex

                log_dequeue(consumer_id, t.task_id, t.priority, queues[p].count, queues[p].max_size);
                usleep(150000); // 150ms
                consumed++; // Increment the number of tasks consumed by each consumer
                found = 1; // Mark that a task was found and consumed

                if (done) {
                    consumer_counts[consumer_id - 1] = consumed; // Store the number of tasks consumed by this consumer
                    free(arg); // Free the allocated memory from the argument taken by the malloc() in main
                    return NULL;
                }
                break; // Exit the for loop after consuming a task
            }
        }

        if (!found) { // If no tasks were found in any queue
            pthread_mutex_lock(&cons_mutex); // Lock the consumer mutex to safely check if all tasks are consumed
            int done = (tasks_consumed >= num_tasks); // Check if all tasks have been consumed
            pthread_mutex_unlock(&cons_mutex); // Unlock the consumer mutex
            if (done) {
                consumer_counts[consumer_id - 1] = consumed; // Store the number of tasks consumed by this consumer
                free(arg); // Free the allocated memory from the argument taken by the malloc() in main
                return NULL;
            }
            usleep(50000); // 50ms
        }
    }
}



/* - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Main
This is the entry point of the program.
It initializes the queues, creates producer and consumer threads, and waits for them to finish.
It also handles command line arguments for the number of producers, consumers, queue size, and number of tasks.
I used ANSI escape codes to color the error messages and final output.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - */
int main(int argc, char *argv[]) {
    if (argc != 5) { // Check if the correct number of arguments is provided (num_producers, num_consumers, queue_size, num_tasks)
        fprintf(stderr, "\033[1;31m");
        fprintf(stderr, "\n===============================================\n");
        fprintf(stderr, "ERROR: Invalid usage!\n");
        fprintf(stderr, "Usage: %s <num_producers> <num_consumers> <queue_size> <num_tasks>\n", argv[0]);
        fprintf(stderr, "===============================================\n\033[0m\n");
        exit(1);
    }
    num_producers = atoi(argv[1]); // Convert command line arguments to integers
    num_consumers = atoi(argv[2]);
    queue_size = atoi(argv[3]);
    num_tasks = atoi(argv[4]);
    if (num_producers <= 0 || num_consumers <= 0 || queue_size <= 0 || num_tasks <= 0) { // Check if all arguments are positive integers
        fprintf(stderr, "\033[1;31m");
        fprintf(stderr, "\n===============================================\n");
        fprintf(stderr, "ERROR: All arguments must be positive integers.\n");
        fprintf(stderr, "===============================================\n\033[0m\n");
        exit(1);
    }
    srand(time(NULL)); // Seed the random number generator for task priority assignment


    for (int i = 0; i < NUM_PRIORITIES; ++i) { // Initialize queues for each priority level
        queue_init(&queues[i], queue_size); // Each one has its own semaphore and mutex
    }

    // Allocate memory for producer and consumer counts
    producer_counts = calloc(num_producers, sizeof(int));
    consumer_counts = calloc(num_consumers, sizeof(int));

    // Create producer and consumer threads
    pthread_t producers[num_producers];
    pthread_t consumers[num_consumers];

    // Create producer threads
    for (int i = 0; i < num_producers; ++i) {
        int *pid = malloc(sizeof(int)); // Allocate memory for producer ID
        *pid = i + 1; // Assign producer ID starting from 1
        pthread_create(&producers[i], NULL, producer_thread, pid); // Create producer thread
    }
    // Create consumer threads
    for (int i = 0; i < num_consumers; ++i) {
        int *cid = malloc(sizeof(int)); // Allocate memory for consumer ID
        *cid = i + 1; // Assign consumer ID starting from 1
        pthread_create(&consumers[i], NULL, consumer_thread, cid); // Create consumer thread
    }

    // Wait for all producers to finish
    for (int i = 0; i < num_producers; ++i) {
        pthread_join(producers[i], NULL);
    }
    // Wait for all consumers to finish
    for (int i = 0; i < num_consumers; ++i) {
        pthread_join(consumers[i], NULL);
    }

    // Destroy queues
    for (int i = 0; i < NUM_PRIORITIES; ++i) {
        queue_destroy(&queues[i]);
    }
    pthread_mutex_destroy(&prod_mutex); // Destroy the producer mutex
    pthread_mutex_destroy(&cons_mutex); // Destroy the consumer mutex
    pthread_mutex_destroy(&id_mutex); // Destroy the ID mutex

    // Final output
    printf("\033[1;35m");
    printf("\n===============================================\n");
    printf("All tasks produced and consumed.\n");
    int max_lines = num_producers > num_consumers ? num_producers : num_consumers; // Maximum number of lines to print
    for (int i = 0; i < max_lines; ++i) {
        // Producer part
        if (i < num_producers) {
            printf("\033[1;32m[Producer%d]:\033[0m\033[1;29m%2d tasks\033[0m", i + 1, producer_counts[i]);
        } else {
            printf("                   "); // If no producer, leave space
        }
        // Consumer part
        if (i < num_consumers) {
            printf(" | \033[1;34m[Consumer%d]:\033[0m \033[1;29m%2d tasks\033[0m", i + 1, consumer_counts[i]);
        }
        printf("\n\033[1;35m");
    }
    printf("Exiting...\n");
    printf("===============================================\n");
    printf("\033[0m");
    free(producer_counts); // Free the allocated memory for producer counts
    free(consumer_counts); // Free the allocated memory for consumer counts
    sleep(1);
    return 0;
}
