package brachydium.lib.util;

import brachydium.lib.util.registry.IRegistry;
import brachydium.lib.util.registry.IdValueMapper;
import com.google.common.collect.Iterators;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;

public abstract class BrachydiumRegistry<K, V> implements IRegistry<K, V>, IdValueMapper<V> {

    private final Map<K, V> ENTRY_MAP = new Object2ObjectOpenHashMap<>();
    private final Int2ObjectMap<V> idMap = new Int2ObjectOpenHashMap<>();

    private boolean frozen;

    public boolean isFrozen() {
        return frozen;
    }

    public void freeze() {
        this.frozen = true;
    }

    @Override
    public V get(K key) {
        return null;
    }

    public void register(K key, V value, int id) {
        if (isFrozen()) {
            throw new IllegalStateException();
        }
        if (id < 0 || idMap.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        if (containsKey(key)) {
            throw new IllegalArgumentException();
        }
        ENTRY_MAP.put(key, value);
        idMap.put(id, value);
    }

    @Override
    public V getValue(int id) {
        return idMap.get(id);
    }

    public boolean containsKey(K key) {
        return this.ENTRY_MAP.containsKey(key);
    }

    @NotNull
    @Override
    public Iterator<V> iterator() {
        return Iterators.unmodifiableIterator(this.ENTRY_MAP.values().iterator());
    }
}
