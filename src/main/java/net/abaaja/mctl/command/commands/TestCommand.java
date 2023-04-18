package net.abaaja.mctl.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.abaaja.mctl.entity.ModEntityTypes;
import net.abaaja.mctl.entity.custom.TurtleEntity;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

public class TestCommand {

    public TestCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("testburger")
                .executes((context) -> run(context.getSource())));
    }

    private static int run(CommandSourceStack source) throws CommandSyntaxException {
        if (source.getPlayer() == null)
            throw new CommandSyntaxException(null, Component.literal("Command can only be executed by a player!"));
        source.sendSuccess(Component.literal("test executed"), false);

        Entity turtle = ModEntityTypes.TURTLE.get().create(source.getLevel());

        if(turtle == null)
            throw new CommandSyntaxException(null, Component.literal("Turtle is not defined in the code!"));

        turtle.moveTo(source.getPosition());
        turtle.setYRot(source.getPlayer().getYRot());
        source.getLevel().addFreshEntity(turtle);
        return 0;
    }
}