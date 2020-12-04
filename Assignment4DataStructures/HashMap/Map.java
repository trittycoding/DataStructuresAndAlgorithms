package HashMap;
import java.util.Iterator;

public interface Map<K, V> {
    public int size();
    public boolean isEmpty();
    public void clear();
    public V get(K key);
    public V put(K key, V value);
    public V remove(K key);
    public Iterator<K> keys();
    public Iterator<V> values();
}
