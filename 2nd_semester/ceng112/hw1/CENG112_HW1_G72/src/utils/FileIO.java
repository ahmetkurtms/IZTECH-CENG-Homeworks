package utils;

import classes.Garbage;
import classes.KeyValuePair;
import classes.KeyValuePairArray;
import classes.ibagimpl.TrashCan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class FileIO {

    private static String filepath = "src/resources/garbage.txt";

    public static TrashCan readTrashCan()
    {
        TrashCan trashCan = new TrashCan();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String type = parts[1].trim();
                int amount = Integer.parseInt(parts[2].trim());

                for (int i = 0; i < amount; i++) {
                    Garbage garbage = new Garbage(name, type);
                    trashCan.add(garbage);
                }

                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return trashCan;
    }

    public static boolean updateTrashCan(TrashCan trashCan)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath, false))) // set to false to overwrite.
        {
            KeyValuePairArray contents = trashCan.getContentsMap();
            for (int i = 0; i < contents.getSize(); i++) {
                KeyValuePair pair = contents.getKeyValuePairAtIndex(i);
                Garbage garbage = pair.getKey();
                int count = pair.getValue();
                writer.println(garbage.getName() + ", " + garbage.getType() + ", " + count);          
        }
            return true;

        }
        catch (IOException e)
        {
            System.out.println("Error writing to file: " + e.getMessage());
            
            return false;
        }
    }
    
}

