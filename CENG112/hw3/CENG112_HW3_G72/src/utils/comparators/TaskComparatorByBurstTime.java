package utils.comparators;

import java.util.Comparator;

import classes.Task;

public class TaskComparatorByBurstTime implements Comparator<Task> {

    @Override
    public int compare(Task task1, Task task2) {
        int burstTimeComparison = - Integer.compare(task1.getBurstTime(), task2.getBurstTime()); // added "-" sign because we want the shortest burst time to be the highest priority
        if (burstTimeComparison != 0) {
            return burstTimeComparison;
        }

        int dateTimeComparison = - task1.getDateTime().compareTo(task2.getDateTime()); // added "-" sign because we want the earliest date/time to be the highest priority
        if (dateTimeComparison != 0) {
            return dateTimeComparison;
        }

        return task1.getManagementType().compareTo(task2.getManagementType());
    }
}
