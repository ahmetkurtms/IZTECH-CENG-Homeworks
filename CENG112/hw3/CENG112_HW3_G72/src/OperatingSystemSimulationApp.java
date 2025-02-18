import classes.Task;

import implementations.LinkedPriorityQueue;
import implementations.LinkedSortedList;

import interfaces.ListInterface;
import interfaces.PriorityQueueInterface;
import interfaces.SortedListInterface;
import interfaces.StackInterface;

import utils.FileIO;
import utils.comparators.TaskComparatorByBurstTime;
import utils.comparators.TaskComparatorByDateTime;
import utils.comparators.TaskComparatorByManagementType;


/**
 * The OperatingSystemSimulationApp class contains the main method.
 * This class is responsible for reading the tasks from the file, sorting them, and then
 * simulating the operating system by processing the tasks in the order of their priority.
 * 
 * The priority of a task is determined by its management type, burst time, and date/time.
 * Therefore the compareTo method in the Task class returns positive if the task has a higher priority than the other task.
 * Since the compareTo method of DateTime returns negative if the date/time is earlier than the other date/time, 
 * we added a "-" sign to the result of the compareTo method to make the date/time with the earliest date/time to have the highest priority.
 * We also added a "-" sign to the result of the compareTo method of the burst time to make the task with the shortest burst time to have the highest priority.
 * 
 * Also we implemented SortedList in descending order, so that the task with the highest priority is at the head of the list.
 * This was done to make it parallel to the other data structures that we used in this project.
 * 
 */
public class OperatingSystemSimulationApp
{
    public static void main(String[] args)
    {   
        // A list to store the tasks read from the file
        // The order of the tasks in this list is the order of the tasks in the file
        ListInterface<Task> taskList = FileIO.readTasksFromFile("src/resources/tasks.txt");

        // A sorted list to store the tasks sorted by their date/time then management type then burst time
        SortedListInterface<Task> sortedTaskListByDateTime = new LinkedSortedList<Task>(new TaskComparatorByDateTime());

        // A sorted list to store the tasks sorted by their burst time then date/time then management type
        // We will use it for the waiting pile of tasks which is implemented as a stack
        SortedListInterface<Task> sortedTaskListByBurstTime = new LinkedSortedList<Task>(new TaskComparatorByBurstTime());

        // Add all the tasks to the sorted lists
        for (Task task : taskList) {
            sortedTaskListByDateTime.add(task);
            sortedTaskListByBurstTime.add(task);
        }

        System.out.println("Listed Tasks (2):" + "\n");

        System.out.println("----------------------------------------------------");
        System.out.println("   Management Type   | Burst Time |    Date/Time    ");
        System.out.println("----------------------------------------------------");
        for (Task task : sortedTaskListByDateTime) {
            System.out.println(task);
        }
        System.out.println("----------------------------------------------------");


        // ====================================================================================================


        // A priority queue to store the tasks sorted by their management type (priority)
        PriorityQueueInterface<Task> waitingPriorityLine = new LinkedPriorityQueue<Task>(new TaskComparatorByManagementType());

        // Add all the tasks to the priority queue
        for (Task task : taskList)  {
            waitingPriorityLine.offer(task);
        }

        // Print the tasks in the priority queue after every 5 tasks are executed
        int numberOfTasksInQueue = waitingPriorityLine.size();
        int pc = 0;
        for (int i = 0; i <= numberOfTasksInQueue; i+=5) {

            if (i == 0) {
                System.out.println("\n\n" + "Waiting priority line according to the execution time (4.a.):" + "\n");
            } else if (i == 5) {
                System.out.println("\n\n" + "Remaining tasks after every 5 execution in the waiting priority line (6):" + "\n");
            }
            
            pc = i;
            System.out.println("------------------------------------------------------------");
            System.out.println(" P.C. |   Management Type    | Burst Time |    Date/Time    ");
            System.out.println("------------------------------------------------------------");
            for (Task task : waitingPriorityLine) {
                System.out.println(String.format(" %02X   | ", pc) + task);
                pc++;
            }

            if (waitingPriorityLine.isEmpty()) {
                System.out.println("             All tasks have been executed.              ");
            } else {
                for (int j = 0; j < 5; j++) {
                    waitingPriorityLine.poll();
                }
            }
            System.out.println("------------------------------------------------------------" + "\n");
        }


        // ====================================================================================================


        // A stack to store the tasks sorted by their burst time then date/time then management type
        StackInterface<Task> pileOfWaitingBurstTime = new implementations.LinkedStack<Task>();

        // Add all the tasks to the stack
        // Since the stack is LIFO, we need to push the tasks in reverse order
        for (int i = sortedTaskListByBurstTime.size() - 1; i >= 0; i--) {   
            pileOfWaitingBurstTime.push(sortedTaskListByBurstTime.get(i));
        }

        int numberOfTasksInStack = pileOfWaitingBurstTime.size();
        pc = 0;
        for (int i = 0; i <= numberOfTasksInStack; i+=5) {

            if (i == 0) {
                System.out.println("\n\n" + "Pile of waiting burst time according to the execution time (4.b.):" + "\n");
            } else if (i == 5) {
                System.out.println("\n\n" + "Remaining tasks after every 5 execution in the pile of waiting burst time (6):" + "\n");
            }
            
            pc = i;
            System.out.println("------------------------------------------------------------");
            System.out.println(" P.C. |   Management Type    | Burst Time |    Date/Time    ");
            System.out.println("------------------------------------------------------------");
            for (Task task : pileOfWaitingBurstTime) {
                System.out.println(String.format(" %02X   | ", pc) + task);
                pc++;
            }

            if (pileOfWaitingBurstTime.isEmpty()) {
                System.out.println("             All tasks have been executed.              ");
            } else {
                for (int j = 0; j < 5; j++) {
                    pileOfWaitingBurstTime.pop();
                }
            }
            System.out.println("------------------------------------------------------------" + "\n");
        }
    }
}
