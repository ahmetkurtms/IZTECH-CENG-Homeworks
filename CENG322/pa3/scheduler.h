#ifndef SCHEDULER_H
#define SCHEDULER_H

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <string.h>
#include <time.h>

#define NUM_PRIORITIES 3 // 0: High, 1: Medium  , 2: Low

typedef struct {
    int task_id;
    int producer_id;
    int priority;
} Task;

typedef struct {
    Task *tasks;
    int head, tail, count, max_size;
    pthread_mutex_t mutex;
    sem_t items;
    sem_t slots;
} TaskQueue;

void log_enqueue(int producer_id, int task_id, int priority, int qsize, int qmax);
void log_dequeue(int consumer_id, int task_id, int priority, int qsize, int qmax);
void queue_init(TaskQueue *q, int max_size);
void queue_destroy(TaskQueue *q);
void queue_enqueue(TaskQueue *q, Task t);
Task queue_dequeue(TaskQueue *q);
void *producer_thread(void *arg);
void *consumer_thread(void *arg);

#endif // SCHEDULER_H
