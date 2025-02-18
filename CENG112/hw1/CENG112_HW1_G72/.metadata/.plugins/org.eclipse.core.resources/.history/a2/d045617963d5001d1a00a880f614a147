package classes;

import java.util.Objects;


public class Garbage 
{
    private String name;
    private String type;

    public Garbage(String name, String type)
    {
        this.name = name;
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    @Override
    public String toString()
    {
        return name + " (" + type + ")";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Garbage)) return false;

        // Casting is safe since we checked o is an instance of Garbage.
        Garbage garbage = (Garbage) o;

        return name.equals(garbage.name) && type.equals(garbage.type);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, type);
    }
}
