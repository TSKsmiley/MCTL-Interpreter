package net.abaaja.mctl.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class TestCommand {

    public TestCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("testburger")
                .executes((context) -> run(context.getSource())));
    }

    private static int run(CommandSourceStack source) throws CommandSyntaxException {
        source.sendSuccess(Component.literal("test executed"), false);

        return 0;
    }
}
