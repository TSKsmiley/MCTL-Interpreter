package net.abaaja.mctl.event;

import net.abaaja.mctl.MCTL;
import net.abaaja.mctl.bridge.GameBridge;
import net.abaaja.mctl.command.ModCommands;
import net.abaaja.mctl.entity.ModEntityTypes;
import net.abaaja.mctl.entity.custom.TurtleEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = MCTL.MOD_ID)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onCommandRegister(RegisterCommandsEvent event) {
            System.out.println("Registering commands");
            ModCommands.register(event.getDispatcher());

            ConfigCommand.register(event.getDispatcher());
        }

        @SubscribeEvent
        public static void ServerChatEvent(ServerChatEvent event) {
            GameBridge.gameBridges.forEach((gameBridge) -> {
                gameBridge.ChatMessage(event.getMessage(), event.getPlayer());
            });
        }
    }

    @Mod.EventBusSubscriber(modid = MCTL.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {

        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event){
            event.put(ModEntityTypes.TURTLE.get(), TurtleEntity.setAttributes());
        }
    }
}
