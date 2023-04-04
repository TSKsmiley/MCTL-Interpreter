package net.abaaja.mctl.event;

import net.abaaja.mctl.MCTL;
import net.abaaja.mctl.entity.ModEntityTypes;
import net.abaaja.mctl.entity.custom.TurtleEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = MCTL.MOD_ID)
    public static class ForgeEvents {

    }

    @Mod.EventBusSubscriber(modid = MCTL.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {

        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event){
            event.put(ModEntityTypes.TURTLE.get(), TurtleEntity.setAttributes());
        }
    }
}
