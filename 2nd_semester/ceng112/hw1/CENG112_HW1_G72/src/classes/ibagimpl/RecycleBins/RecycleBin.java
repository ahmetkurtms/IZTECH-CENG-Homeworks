package classes.ibagimpl.RecycleBins;

import java.util.Random;

import classes.ibagimpl.GarbageBag;

public class RecycleBin {

    public RecycleBin(){}

    private Random random = new Random();
    private int[] sizeOptions = {5, 10, 15};
    
    public GarbageBag plastic = new PlasticRecycleBin(sizeOptions[random.nextInt(sizeOptions.length)]);
    public GarbageBag paper = new PaperRecycleBin(sizeOptions[random.nextInt(sizeOptions.length)]);
    public GarbageBag glass = new GlassRecycleBin(sizeOptions[random.nextInt(sizeOptions.length)]);
    public GarbageBag fabric = new FabricRecycleBin(sizeOptions[random.nextInt(sizeOptions.length)]);
    public GarbageBag metal = new MetalRecycleBin(sizeOptions[random.nextInt(sizeOptions.length)]);
    public GarbageBag organic = new OrganicRecycleBin(sizeOptions[random.nextInt(sizeOptions.length)]);
    
    public GarbageBag[] allBinsArray = {this.plastic, this.paper, this.glass, this.fabric, this.metal, this.organic};

    public void checkBinsAreFilled(){
        for (GarbageBag bin : allBinsArray)
        {
            if (!bin.isFull())
            {
                System.out.println(" \n Error: " + bin.getRandomItem().getType() + " recycle bin is not filled. \n");
                System.out.println("According to specifications of the homework every bin must be filled, but in this case it is not.");
                System.out.println("It is not filled because there wasn't enough " + bin.getRandomItem().getType() + " in the garbage.txt");
                System.out.println("Please restart the program with enough garbage.");
                System.exit(0);
            }
        }
    }
}
