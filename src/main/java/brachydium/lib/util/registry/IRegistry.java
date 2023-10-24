package brachydium.lib.util.registry;

public interface IRegistry<K, V> extends Iterable<V> {

    boolean isFrozen();

    void freeze();

    V get(K key);

    K getKey(V value);

    void register(K key, V value, int id);

    boolean containsKey(K key);
}
