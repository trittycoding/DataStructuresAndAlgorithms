package HashMap;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

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
        // Spot is blank
        if(entries[hashValue] == null){
            return hashValue;
        }

        // Spot isn't blank, move to next spot
        else{
            if(hashValue != entries.length-1) {
                hashValue++;
            }

            // If next spot is open, return next spot
            if(entries[hashValue] == null){
                return hashValue;
            }

            boolean spotNotFound = true;
            K entryKey;

            // Search for next spot
            while(spotNotFound){
                entryKey = entries[hashValue].getKey();
                System.out.println("Key - " + key.toString());
                System.out.println("Entry Key - " + entryKey.toString());
                if(entryKey != null && !(key.equals(entryKey))){
                    if(hashValue == entries.length-1){
                        hashValue = 0;
                    }
                    else {
                        hashValue++;
                    }
                }
                else{
                    spotNotFound = false;
                }
            }
            return hashValue;
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
        // Check to see if array needs to be resized
        checkLoad();

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
            if(entries[hashValue].getKey().equals(key)){
                placeholders--;
            }
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
        // Check to see if the new Entry can fit
        double currentLoad = (double)(size+placeholders+1);
        double loadF = Math.floor(capacity * load_factor);
        System.out.println("Load is - " + currentLoad);
        System.out.println("LF - " + loadF);
        if(currentLoad >= loadF){
            rehashEntries();
        }
    }

    // Returns new array size
    public int resizeArray(){
        // New size is doubled plus one to be prime
        double new_size = entries.length * 2 + 1;
        double root = Math.sqrt(new_size);
        boolean isNotPrime = true;
        int dividableByDivisor = 0;
        double divisor = 3;

        /* Checking if new size is prime:
            Do not check 2 or 1 as sqroot of 2 means
            number is not prime and everything is divisble by 1,
            meaning it won't break the loop.
            If the new size is dividable by the divisor, then it's not prime! */
        do {
            // Check all possible divisors
            while(root > divisor){
                if(new_size % divisor == 0){
                    dividableByDivisor++;
                }
                divisor++;
            }

            // If found dividable divisor, increase size and reset counter to zero
            if(dividableByDivisor != 0){
                new_size += 2;
                dividableByDivisor = 0;
            }

            // If no dividable divisor found, number is prime
            else{
                isNotPrime = false;
            }
        } while (isNotPrime);
        int prime = (int)new_size;
        return prime;
    }

    // Places existing entries in new resized array
    public void rehashEntries(){
        // Cloning existing entries array
        Entry<K, V> [] entriesCopy = entries;
        int newSize = this.resizeArray();
        // Creating new entries array using private field
        this.entries = (Entry<K, V>[]) new Entry[newSize];
        int hash;
        Entry <K, V> currentEntry;
        this.capacity = newSize;

        // Copying entries to new array
        for(int i = 0; i < entriesCopy.length-1; i++){
            currentEntry = entriesCopy[i];
            if(entriesCopy[i] != null) {
                hash = getMatchingOrNextAvailableBucket(currentEntry.getKey());
                entries[hash] = currentEntry;
            }
        }
    }

    @Override
    public Iterator<K> keys() {
        Iterator<K> keys = this.keys();
        return keys;
    }

    @Override
    public Iterator<V> values() {
        Iterator<V> values = this.values();
        return values;
    }

    public Entry<K, V>[] getTable(){
        return this.entries;
    }
}
