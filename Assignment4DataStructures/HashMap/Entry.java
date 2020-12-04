package HashMap;
// A class representing an Entry in the HashTable
public class Entry<K, V>{
    // Entries have a key and value
    private K key;
    private V value;

    // Constructor
    public Entry(K key, V value){
        this.key = key;
        this.value = value;
    }

    // Accessor Methods
    public K getKey(){return this.key;}
    public V getValue(){return this.value;}

    // Mutator Methods
    public void setKey(K key){this.key = key;}
    public void setValue(V value){this.value = value;}
}
