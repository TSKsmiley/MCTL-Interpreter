package net.abaaja.mctl.command;

import com.mojang.brigadier.CommandDispatcher;
import net.abaaja.mctl.command.commands.EditFileCommand;
import net.abaaja.mctl.command.commands.LoadFileCommand;
import net.minecraft.commands.CommandSourceStack;

public class ModCommands {
    public static void register(final CommandDispatcher<CommandSourceStack> dispatcher){
        new LoadFileCommand(dispatcher);
        new EditFileCommand(dispatcher);
    }
}
