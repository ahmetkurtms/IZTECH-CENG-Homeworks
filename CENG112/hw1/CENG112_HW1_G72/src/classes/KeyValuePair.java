package classes;

public class KeyValuePair {
    private Garbage key;
    private Integer value;

    public KeyValuePair(Garbage key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public Garbage getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
