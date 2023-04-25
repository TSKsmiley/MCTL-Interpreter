package net.abaaja.mctl.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.abaaja.mctl.MCTL;
import net.abaaja.mctl.command.getSuggestionsMCTL;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FormatFileCommand {


    public FormatFileCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("formatfile")
                .then(Commands.argument("filename", StringArgumentType.string())
                        .suggests(getSuggestionsMCTL::getSuggestions)
                    .executes(this::run)));
    }

    private int run(CommandContext<CommandSourceStack> ctx) {
         // write contents of file to chat
        String filename = StringArgumentType.getString(ctx, "filename");
        // call the pretty printer to reformat the file


        return 0;
    }

}
