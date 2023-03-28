package net.abaaja.mctl.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab MCTL_TAB = new CreativeModeTab("mctltab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.BURGER.get());
        }
    };
}
