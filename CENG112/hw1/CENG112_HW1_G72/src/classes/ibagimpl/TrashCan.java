package classes.ibagimpl;

import classes.Garbage;
import interfaces.IBag;

public class TrashCan extends GarbageBag {

    private static int SIZE = 450;

    public TrashCan() {
        super(SIZE);
    }

    public boolean separate(Garbage item, IBag<Garbage> plastic, IBag<Garbage> paper, IBag<Garbage> glass, IBag<Garbage> fabric, IBag<Garbage> metal, IBag<Garbage> organic)
    {
        switch (item.getType()) {
            case "plastic":
                return transferTo(plastic, item);
            case "paper":
                return transferTo(paper, item);
            case "glass":
                return transferTo(glass, item);
            case "fabric":
                return transferTo(fabric, item);
            case "metal":
                return transferTo(metal, item);
            case "organic":
                return transferTo(organic, item);
            default:
                return false;
        }
    }
}
