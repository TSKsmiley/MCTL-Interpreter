package net.abaaja.mctl.item;

import net.abaaja.mctl.MCTL;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MCTL.MOD_ID);

    public static final RegistryObject<Item> BURGER = ITEMS.register("burger",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
