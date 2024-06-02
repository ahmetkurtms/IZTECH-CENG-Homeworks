package classes.ibagimpl;

import java.util.NoSuchElementException;
import java.util.Random;

import classes.Garbage;
import classes.KeyValuePair;
import classes.KeyValuePairArray;
import interfaces.IBag;


public class GarbageBag implements IBag<Garbage>
{
    private final int SIZE;
    private int itemCount;
    private Garbage[] items;

    public GarbageBag(int size)
    {
        SIZE = size;
        itemCount = 0;
        items = new Garbage[SIZE];
    }

    public int getSize()
    {
        return SIZE;
    }

    public boolean add(Garbage newItem)
    {
        if (isFull())
        {
            return false;
        }

        items[itemCount] = newItem;
        itemCount++;

        return true;
    }

    public boolean isEmpty()
    {
        return itemCount == 0;
    }

    public boolean isFull()
    {
        return itemCount == SIZE;
    }

    public Garbage removeByIndex(int index)
    {
        if (index < 0 || index >= itemCount)
        {
            throw new IndexOutOfBoundsException();
        }
        
        Garbage removedItem = items[index];

        // The order doesn't matter in the bag but,
        // implementing removeByIndex in this way makes it easier to debug.
        for (int i = index; i < itemCount - 1; i++) 
        {
            items[i] = items[i + 1];
        }

        items[itemCount - 1] = null;
        itemCount--;

        return removedItem;
        
    }

    public Garbage remove()
    {
        return removeByIndex(itemCount - 1);
    }

    public Garbage remove(Garbage item) 
    {  
        if (item == null)
        {
            throw new NullPointerException();
        }

        int index = getIndexOf(item);

        if (index == -1)
        {
            throw new NoSuchElementException();
        }

        return removeByIndex(index);
    }

    public int getItemCount()
    {
        return itemCount;
    }

    public int getIndexOf(Garbage item)
    {
        if (item == null)
        {
            throw new NullPointerException();
        }

        for (int i = 0; i < itemCount; i++)
        {
            if (items[i].equals(item))
            {
                return i;
            }
        }

        return -1;
    }

    public boolean contains(Garbage item) 
    {
        if (item == null)
        {
            throw new NullPointerException();
        }

        return getIndexOf(item) != -1;
    }

    public void displayItems() {
        if (isEmpty()) {
            System.out.println("It is empty.");
            return;
        }
    
        KeyValuePairArray contents = getContentsMap();
    
        for (int i = 0; i < contents.getSize(); i++) {
            KeyValuePair pair = contents.getKeyValuePairAtIndex(i);
            Garbage garbage = pair.getKey();
            int count = pair.getValue();
            System.out.println("- " + count + " " + garbage);
        }
    }
    

    public void dump()
    {
        for (int i = 0; i < itemCount; i++)
        {
            items[i] = null;
        }

        itemCount = 0;
    }
  
    public boolean transferTo(IBag<Garbage> targetBag, Garbage item)
    {
        if (targetBag == null || item == null)
        {
            throw new NullPointerException();
        }
        else if(targetBag.isFull())
        {
            return false;
        }

        targetBag.add(item);
        remove(item);

        return true;
    }

    public Garbage getRandomItem()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        Random random = new Random();

        return items[random.nextInt(itemCount)];
    }

    public KeyValuePairArray getContentsMap() {
        KeyValuePairArray contents = new KeyValuePairArray();
        for (int i = 0; i < itemCount; i++) {
            Garbage garbage = items[i];
    
            if (contents.containsKey(garbage)) {
                KeyValuePair pair = contents.getPairByKey(garbage);
                pair.setValue(pair.getValue() + 1);
            } else {
                contents.put(new KeyValuePair(garbage, 1));
            }
        }
    
        return contents;
    }

    public Garbage[] toArray()
    {
        Garbage[] result = new Garbage[itemCount];
        for (int i = 0; i < itemCount; i++)
        {
            result[i] = items[i];
        }
        
        return result;
    }
    
}
