package classes;

// Since It was restricted to use Java Collections Framework,
// I coded a similar data structure to Map from scratch.

public class KeyValuePairArray {
    private KeyValuePair[] keyValuePairs;
    private int size;

    public KeyValuePairArray() {
        keyValuePairs = new KeyValuePair[100];
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public KeyValuePair getKeyValuePairAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return keyValuePairs[index];
    }

    public boolean containsKey(Garbage key) {
        for (int i = 0; i < size; i++) {
            if (keyValuePairs[i].getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public void put(KeyValuePair pair) {
        Garbage key = pair.getKey();
        for (int i = 0; i < size; i++) {
            if (keyValuePairs[i].getKey().equals(key)) {
                keyValuePairs[i].setValue(pair.getValue());
                return;
            }
        }
        if (size == keyValuePairs.length) {
            KeyValuePair[] newKeyValuePairs = new KeyValuePair[size * 2];
            System.arraycopy(keyValuePairs, 0, newKeyValuePairs, 0, size);
            keyValuePairs = newKeyValuePairs;
        }
        keyValuePairs[size++] = pair;
    }

    public KeyValuePair getPairByKey(Garbage key) {
        for (int i = 0; i < size; i++) {
            if (keyValuePairs[i].getKey().equals(key)) {
                return keyValuePairs[i];
            }
        }
        return null;
    }
}
