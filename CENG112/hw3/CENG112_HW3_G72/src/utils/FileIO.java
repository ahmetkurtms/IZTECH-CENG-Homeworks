package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import classes.Task;
import enums.ManagementType;
import implementations.LinkedList;
import interfaces.ListInterface;

public class FileIO {
    public static ListInterface<Task> readTasksFromFile(String filePath) {
        ListInterface<Task> tasks = new LinkedList<Task>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    ManagementType managementType = ManagementType.valueOf(parts[0].trim().toUpperCase(Locale.ENGLISH).replaceAll(" ", "_"));
                    int burstTime = Integer.parseInt(parts[1].trim());
                    LocalDateTime dateTime = LocalDateTime.parse(parts[2].trim() + " " + parts[3].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

                    Task task = new Task(managementType, burstTime, dateTime);
                    tasks.add(task);
                }
            }
        } catch (IOException e) {   
            // Prints the exception's stack trace to the standard error stream
            e.printStackTrace();
        }

        return tasks;
    }
}
