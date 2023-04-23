package net.abaaja.mctl.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.abaaja.mctl.entity.ModEntityTypes;
import net.abaaja.mctl.entity.custom.TurtleEntity;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class TurtleCommand {

    static TurtleEntity activeTurtle;

    public TurtleCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("turtle")
                .executes((context) -> run(context.getSource()))
                .then(Commands.literal("move")
                        .executes((context) -> move(context.getSource()))));
    }

    private int run(CommandSourceStack source) throws CommandSyntaxException {
        if (source.getPlayer() == null)
            throw new CommandSyntaxException(null, Component.literal("Command can only be executed by a player!"));
        source.sendSuccess(Component.literal("test running"), false);

        TurtleEntity turtle = ModEntityTypes.TURTLE.get().create(source.getLevel());

        if(turtle == null)
            throw new CommandSyntaxException(null, Component.literal("Turtle is not defined in the code!"));

        turtle.moveTo(source.getPosition());
        turtle.setYRot(source.getPlayer().getYRot());
        turtle.moveForward();
        source.getLevel().addFreshEntity(turtle);
        TurtleEntity newTurtle = (TurtleEntity) source.getLevel().getEntity(turtle.getId());
        assert newTurtle != null;
        activeTurtle = newTurtle;
        source.sendSuccess(Component.literal("test done"), false);

        return 0;
    }

    private int move(CommandSourceStack source) throws CommandSyntaxException {
        if (source.getPlayer() == null)
            throw new CommandSyntaxException(null, Component.literal("Command can only be executed by a player!"));
        source.sendSuccess(Component.literal("test running"), false);

        if(activeTurtle == null)
            throw new CommandSyntaxException(null, Component.literal("No active turtle!"));

        activeTurtle.turnLeft();
        source.sendSuccess(Component.literal("test done"), false);

        return 0;
    }
}
