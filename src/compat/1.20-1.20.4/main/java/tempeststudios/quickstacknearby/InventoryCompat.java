package tempeststudios.quickstacknearby;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

final class InventoryCompat {
    private InventoryCompat() {
    }

    static boolean sameItemAndComponents(ItemStack first, ItemStack second) {
        return ItemStack.isSameItemSameTags(first, second);
    }

    static int maxStackSize(Container container, ItemStack stack) {
        return container.getMaxStackSize();
    }

    static Object stackIdentity(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return new StackIdentity(stack.getItem(), tag == null ? null : tag.copy());
    }

    private record StackIdentity(Item item, CompoundTag tag) {
    }
}
