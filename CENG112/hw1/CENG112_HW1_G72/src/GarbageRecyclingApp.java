import classes.Garbage;
import classes.ibagimpl.GarbageBag;
import classes.ibagimpl.TrashCan;
import classes.ibagimpl.RecycleBins.RecycleBin;
import utils.FileIO;

public class GarbageRecyclingApp {
    public static void main(String[] args)
    {

        TrashCan trashCan = FileIO.readTrashCan();
        RecycleBin recycleBin = new RecycleBin();   // This class contains all of the Recycle Bins as a field.
        

        System.out.println( "\n" + "Name: Trash Can" + "\n" + "Size: " + trashCan.getSize() + "\n" + "Item Count: " + trashCan.getItemCount());
        System.out.println("Contents:");
        trashCan.displayItems();

        System.out.println(" \nNow I will separate the garbage...\n");

        for (Garbage garbage : trashCan.toArray())
        {
            trashCan.separate(garbage, recycleBin.plastic, recycleBin.paper,  recycleBin.glass, recycleBin.fabric, recycleBin.metal, recycleBin.organic);

        }
        // This will check if all the bins are filled or not.
        // If you try to execute the program multiple times with the updated garbage.txt,
        // Eventually it will give an error since there is not enough trash to fill a bin.
        recycleBin.checkBinsAreFilled();


        System.out.println(" \nDone! Here are the informations about the bins after separation:\n");

        for (GarbageBag bin : recycleBin.allBinsArray)
        {
            System.out.println("\n" + "Name: " + bin.getRandomItem().getType() + " recycling bin" + "\n" + "Size: " + bin.getSize() + "\n" + "Item Count: " + bin.getItemCount());
            System.out.println("Contents:");

            bin.displayItems();
        }

        System.out.println("\nAfter all these operations, let's check the status of Trash Can.");
        
        System.out.println( "\n" + "Name: Trash Can" + "\n" + "Size: " + trashCan.getSize() + "\n" + "Item Count: " + trashCan.getItemCount());
        System.out.println("Contents:");
        trashCan.displayItems();

        
        System.out.println("\nLet's update the garbage.txt file according to this too...");

        FileIO.updateTrashCan(trashCan);

        System.out.println("\nDone! garbage.txt has been updated successfully, you can check it!");
    }
}
