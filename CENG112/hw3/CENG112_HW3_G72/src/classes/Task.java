package classes;

import java.time.LocalDateTime;
import java.util.Objects;

import enums.ManagementType;

public class Task implements Comparable<Task>
{
    private ManagementType managementType;
    private int burstTime;
    private LocalDateTime dateTime;

    public Task(ManagementType managementType, int burstTime, LocalDateTime dateTime)
    {
        this.managementType = managementType;
        this.dateTime = dateTime;
        this.burstTime = burstTime;

    }

    public ManagementType getManagementType()
    {
        return managementType;
    }

    public int getBurstTime()
    {
        return burstTime;
    }

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    // Natural ordering of tasks is by earliest date/time, then management type, then shortest burst time.
    @Override
    public int compareTo(Task other)
    {
        int result = - dateTime.compareTo(other.dateTime); // added "-" sign because we want the earliest date/time to be the highest priority
        if (result == 0) {
            result = managementType.compareTo(other.managementType);
            if (result == 0) {
                result = - Integer.compare(burstTime, other.burstTime); // added "-" sign because we want the shortest burst time to be the highest priority
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Task other = (Task) obj;
        return burstTime == other.burstTime && managementType == other.managementType && Objects.equals(dateTime, other.dateTime);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(managementType, burstTime, dateTime);
    }

    @Override
    public String toString() {
        String format = "%-20s | %-10d | %s";
        return String.format(format, managementType, burstTime, dateTime.toString().replaceAll("T", " "));
    }
}
