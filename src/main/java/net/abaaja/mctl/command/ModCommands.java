package net.abaaja.mctl.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.abaaja.mctl.MCTL;
import net.abaaja.mctl.command.commands.LoadFileCommand;
import net.abaaja.mctl.command.commands.TestCommand;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.ArrayList;

public class ModCommands {
    public static void register(final CommandDispatcher<CommandSourceStack> dispatcher){
        new LoadFileCommand(dispatcher);
        new TestCommand(dispatcher);
    }
}
