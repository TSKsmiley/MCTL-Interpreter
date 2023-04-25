package net.abaaja.mctl.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.abaaja.mctl.MCTL;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.SystemUtils;

import java.io.*;


public class EditFileCommand {


    public EditFileCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("editfile")
                .executes(this::run));
    }



    private int run(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
         // write contents of file to chat
        ctx.getSource().sendSuccess(Component.literal("Launching VS-Code"), false);
        execProgram(ctx.getSource(), "code", MCTL.FileLocation);


        return 0;
    }

    private void execProgram(CommandSourceStack source, String program, String args) {
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                Runtime.getRuntime()
                        .exec(String.format("%s.exe %s", program, args));
            } else {
                Runtime.getRuntime()
                        .exec(String.format("%s %s", program, args));
            }
        } catch (IOException e) {
            source.sendFailure(Component.literal("Error running program"));
        }
    }

}
