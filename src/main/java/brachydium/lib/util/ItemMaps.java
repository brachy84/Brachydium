package brachydium.lib.util;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Objects;

public class ItemMaps {


    public static final Hash.Strategy<Object> ITEM_AND_META_HASH_STRAT = new Hash.Strategy<Object>() {

        @Override
        public int hashCode(Object o) {
            if (o==null) return 0;
            if (o.getClass()==ItemAndMeta.class) return o.hashCode();
            if (o.getClass()==ItemStack.class) {
                ItemStack stack = (ItemStack) o;
                return Objects.hash(stack.getItem(), Items.DIAMOND.getDamage(stack));
            }
            return 0;
        }

        @Override
        public boolean equals(Object a, Object b) {
            return ItemAndMeta.areEqual(a, b);
        }
    };

    public static class ItemAndMetaMap<T> extends Object2ObjectOpenCustomHashMap<Object, T> {

        private final boolean wildcardAware;

        public ItemAndMetaMap(boolean wildcardAware) {
            super(ITEM_AND_META_HASH_STRAT);
            this.wildcardAware = wildcardAware;
        }

        @Override
        public T put(Object o, T t) {
            if (o.getClass()==ItemStack.class) {
                o = new ItemAndMeta((ItemStack) o);
            } else if (o.getClass()!=ItemAndMeta.class) {
                throw new IllegalArgumentException();
            }
            return super.put(o, t);
        }

        @Override
        public T get(Object k) {
            T value = super.get(k);
            if (value==null && this.wildcardAware) {
                value = super.get(new ItemAndMeta(ItemAndMeta.getItem(k), OreDictionary.WILDCARD_VALUE));
            }
            return value;
        }

        @Override
        public boolean containsKey(Object k) {
            boolean b = super.containsKey(k);
            if (!b && this.wildcardAware) {
                b = super.containsKey(new ItemAndMeta(ItemAndMeta.getItem(k), OreDictionary.WILDCARD_VALUE));
            }
            return b;
        }
    }
}
