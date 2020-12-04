package HashMap;
import java.util.Iterator;

public class HashMap<K, V> implements Map<K, V>{
    //Constants
    private final int DEFAULT_CAPACITY = 11;
    private final double DEFAULT_LOAD_FACTOR = 0.75;
    private final int INITIAL_SIZE = 0;

    // Class Fields
    private int capacity;;
    private double load_factor;
    private int size;
    private Entry<K, V> [] entries;

    public HashMap(){
        this.capacity = DEFAULT_CAPACITY;
        this.load_factor = DEFAULT_LOAD_FACTOR;
        this.entries = new Entry[DEFAULT_CAPACITY];
        this.size = INITIAL_SIZE;
    }

    public HashMap(int capacity){
        if(capacity <= 0){
            throw new IllegalArgumentException("Capacity cannot be less than or equal to ero");
        }
        this.capacity = capacity;
        this.load_factor = DEFAULT_LOAD_FACTOR;
        this.entries = new Entry[capacity];
        this.size = INITIAL_SIZE;
    }

    public HashMap(int capacity, double loadFactor){
        if(capacity <= 0){
            throw new IllegalArgumentException("Capacity cannot be less than or equal to zero");
        }
        if(loadFactor <= 0 || loadFactor > 1){
            throw new IllegalArgumentException("Load factor out of bounds");
        }
        this.capacity = capacity;
        this.load_factor = loadFactor;
        this.entries = new Entry[capacity];
        this.size = INITIAL_SIZE;
    }

    // Mutator Methods
    public void setCapacity(int capacity){this.capacity = capacity;}
    public void setLoadFactor(double loadFactor){this.load_factor = loadFactor;}
    public void setSize(int size){this.size = size;}

    // Accessor Methods
    @Override
    public int size() {return this.size;}
    public int getCapacity(){return this.capacity;}
    public double getLoadFactor(){return this.load_factor;}

    // Checks array to see if it is empty
    @Override
    public boolean isEmpty() {
        if(size == 0){
            return true;
        }
        return false;
    }

    // Resets array to initial values
    @Override
    public void clear() {
        for(int i = 0; i < getCapacity(); i++){
            entries[i] = null;
        }
        setSize(0);
        setCapacity(DEFAULT_CAPACITY);
        setLoadFactor(DEFAULT_LOAD_FACTOR);
    }

    // Gets the next available spot in the table for a given key
    public int getMatchingOrNextAvailableBucket(K key){
        int hashValue = createHashCode(key);
        if(entries[hashValue].equals(null)){
            return hashValue;
        }
        else{
            int i = hashValue+1;
            boolean spotNotFound = true;
            while(spotNotFound){
                if(!entries[i].equals(null)){
                    i++;
                }
                else{
                    spotNotFound = false;
                }
            }
            return i;
        }
    }

    // Creates hash code for a given key
    public int createHashCode(K key){
        StringKey newStringKey = new StringKey(key.toString());
        return Math.abs(newStringKey.hashCode() % getCapacity());
    }

    @Override
    public V get(K key) {
        int hash = createHashCode(key);
        Entry<K, V> entry = entries[hash];
        if(entries[hash].equals(null)){
            throw new NullPointerException("Specified position returns null");
        }
        return entry.getValue();
    }

    @Override
    public V put(K key, V value) {
        Entry<K, V> newEntry = new Entry<K, V>(key, value);
        int hashValue = createHashCode(key);
        if (entries[hashValue].equals(null)) {
            size++;
            entries[hashValue] = newEntry;
        }
        return newEntry.getValue();
    }

    @Override
    public V remove(K key) {
        int hash = createHashCode(key);
        Entry<K, V> entry = entries[hash];
        if(entries[hash].equals(null)){
            throw new NullPointerException("Specified position is null");
        }
        V value = entry.getValue();
        entries[hash] = null;
        size--;
        return value;
    }

    @Override
    public Iterator<K> keys() {
        return null;
    }

    @Override
    public Iterator<V> values() {
        return null;
    }

    public Entry<K, V>[] getTable(){
        return this.entries;
    }
}
