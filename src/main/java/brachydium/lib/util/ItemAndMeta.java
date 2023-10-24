package brachydium.lib.util;


import java.util.Objects;

public class ItemAndMeta {

    public static int getMeta(ItemStack stack) {
        return Items.DIAMOND.getDamage(stack);
    }

    public static int getMeta(Object o) {
        if (o.getClass()==ItemStack.class) {
            return getMeta((ItemStack) o);
        }
        if (o.getClass()==ItemAndMeta.class) {
            return ((ItemAndMeta) o).getMeta();
        }
        throw new IllegalArgumentException();
    }

    public static Item getItem(Object o) {
        if (o.getClass()==ItemStack.class) {
            return ((ItemStack) o).getItem();
        }
        if (o.getClass()==ItemAndMeta.class) {
            return ((ItemAndMeta) o).getItem();
        }
        throw new IllegalArgumentException();
    }

    public static boolean areEqual(Object o1, Object o2) {
        if (o1.getClass()==ItemAndMeta.class) {
            if (o2.getClass()==ItemAndMeta.class) {
                return o1.equals(o2);
            }
            if (o2.getClass()==ItemStack.class) {
                return areEqual((ItemAndMeta) o1, (ItemStack) o2);
            }
            throw new IllegalArgumentException();
        }
        if (o1.getClass()==ItemStack.class) {
            if (o2.getClass()==ItemStack.class) {
                return ItemStack.areItemsEqual((ItemStack) o1, (ItemStack) o2);
            }
            if (o2.getClass()==ItemAndMeta.class) {
                return areEqual((ItemAndMeta) o2, (ItemStack) o1);
            }
        }
        throw new IllegalArgumentException();
    }

    public static boolean areEqual(ItemAndMeta itemAndMeta, ItemStack itemStack) {
        return itemAndMeta.meta==getMeta(itemStack) && itemAndMeta.item==itemStack.getItem();
    }

    private final Item item;
    private final int meta;

    public ItemAndMeta(Item item, int meta) {
        this.item = item;
        this.meta = meta;
    }

    public ItemAndMeta(ItemStack itemStack) {
        this(itemStack.getItem(), Items.DIAMOND.getDamage(itemStack));
    }

    public Item getItem() {
        return item;
    }

    public int getMeta() {
        return meta;
    }

    public ItemStack toStack(int count) {
        return new ItemStack(this.item, count, this.meta);
    }

    public ItemStack toStack() {
        return toStack(1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.item, this.meta);
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        ItemAndMeta that = (ItemAndMeta) o;
        return meta==that.meta && Objects.equals(item, that.item);
    }
}
