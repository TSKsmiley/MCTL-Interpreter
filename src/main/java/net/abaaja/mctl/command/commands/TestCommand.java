package net.abaaja.mctl.command.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class TestCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("test").executes(TestCommand::execute);
    }

    private static int execute(final CommandContext<CommandSourceStack> context) {
        System.out.println("test command executed");
        context.getSource().sendSuccess(Component.literal("test command executed"), false);
        return 0;
    }
}
