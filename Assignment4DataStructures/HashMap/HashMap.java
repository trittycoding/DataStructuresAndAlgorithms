package HashMap;
import java.util.Iterator;

public class HashMap<K, V> implements Map<K, V>{

    //Constants
    private final int DEFAULT_CAPACITY = 11;
    private final double DEFAULT_LOAD_FACTOR = 0.75;
    private final int INITIAL_SIZE = 0;
    private final int INITIAL_PLACEHOLDERS = 0;

    // Class Fields
    private int capacity = 1;
    private double load_factor;
    private int size = 1;
    private int placeholders;
    private Entry<K, V> [] entries;
    private double load = size % capacity;

    public HashMap(){
        this.capacity = DEFAULT_CAPACITY;
        this.load_factor = DEFAULT_LOAD_FACTOR;
        this.entries = (Entry <K, V> []) new Entry[DEFAULT_CAPACITY];
        this.size = INITIAL_SIZE;
        this.placeholders = INITIAL_PLACEHOLDERS;
    }

    public HashMap(int capacity){
        if(capacity <= 0){
            throw new IllegalArgumentException("Capacity cannot be less than or equal to ero");
        }
        this.capacity = capacity;
        this.load_factor = DEFAULT_LOAD_FACTOR;
        this.entries = (Entry <K, V> []) new Entry[capacity];
        this.size = INITIAL_SIZE;
        this.placeholders = INITIAL_PLACEHOLDERS;
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
        this.entries = (Entry <K, V> []) new Entry[capacity];
        this.size = INITIAL_SIZE;
        this.placeholders = INITIAL_PLACEHOLDERS;
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
        if(this.size == 0){
            return true;
        }
        return false;
    }

    // Resets array to initial values
    @Override
    public void clear() {
        for(int i = 0; i < entries.length; i++){
            if(entries[i] != null) {
               Entry<K, V> entry =  entries[i];
               entry.setValue(null);
            }
        }
        //entries = (Entry <K, V> []) new Entry[entries.length];
        setSize(INITIAL_SIZE);
    }

    // Gets the next available spot in the table for a given key
    public int getMatchingOrNextAvailableBucket(K key){
        int hashValue = startingBucket(key);
        if(entries[hashValue] == null){
            return hashValue;
        }
        else{
            int i = hashValue;
            if(hashValue != entries.length-1) {
                i = hashValue + 1;
            }
            boolean spotNotFound = true;
            while(spotNotFound){
                if(entries[i].getKey() != null && !entries[i].getKey().equals(key)){
                    if(i == entries.length-1){
                        i = 0;
                    }
                    else {
                        i++;
                    }
                }
                else{
                    spotNotFound = false;
                }
            }
            return i;
        }
    }

    // Creates hash code for a given key
    public int startingBucket(K key){
        return key.hashCode() % getCapacity();
    }

    @Override
    public V get(K key) {
        int hash = getMatchingOrNextAvailableBucket(key);
        if(entries[hash] == null){
            return null;
        }
        else {
            return entries[hash].getValue();
        }
    }

    @Override
    public V put(K key, V value) {
        if(key == null || value == null){
            throw new NullPointerException("Key or value cannot be null");
        }

        Entry<K, V> newEntry = new Entry<K, V>(key, value);
        int hashValue = getMatchingOrNextAvailableBucket(key);
        // New Entry
        if (entries[hashValue] == null) {
            size++;
            entries[hashValue] = newEntry;
            return null;
        }
        // Overriding existing value
        else{
            V existingValue = entries[hashValue].getValue();
            entries[hashValue].setValue(value);
            return existingValue;
        }
    }

    @Override
    public V remove(K key) {
        int hash = startingBucket(key);
        Entry<K, V> entry = entries[hash];
        if(entries[hash] == null){
            return null;
        }
        V value = entry.getValue();
        // Removing the value
        entries[hash].setValue(null);
        placeholders++;
        size--;
        return value;
    }

    // Checks array threshold to see if resizing is needed
    public void checkLoad(){
        if(load >= DEFAULT_LOAD_FACTOR){
            resizeArray();
        }
    }

    // Resizes the array with new size and copies all existing values
    public void resizeArray(){

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
