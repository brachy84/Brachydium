package brachydium.lib.util.registry;

public interface IdValueMapper<V> {

    int getId(V value);

    V getValue(int id);
}
