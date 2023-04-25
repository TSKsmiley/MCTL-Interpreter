package net.abaaja.mctl.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.abaaja.mctl.MCTL;
import net.abaaja.mctl.command.getSuggestionsMCTL;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.antlr.runtime.ANTLRFileStream;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.io.*;
import java.util.concurrent.CompletableFuture;

public class LoadFileCommand {


    public LoadFileCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("loadfile")
                .then(Commands.argument("filename", StringArgumentType.string())
                        .suggests(getSuggestionsMCTL::getSuggestions)
                    .executes(this::run)));
    }

    private int run(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
         // write contents of file to chat
        System.out.println("Loading file: " + StringArgumentType.getString(ctx, "filename"));
        sendChatFromFile(ctx.getSource(), StringArgumentType.getString(ctx, "filename"));


        return 0;
    }

    public void sendChatFromFile(CommandSourceStack source, String filename){
        InputStream is = GetFile(filename);

        if (is == null) {
            source.sendFailure(Component.literal("File not found"));
            return;
        }


        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                source.sendSuccess(Component.literal(line), false);
            }
            br.close();
        } catch (IOException e) {
            source.sendFailure(Component.literal("Error reading file"));
            e.printStackTrace();
        }
    }

    @Nullable
    public InputStream GetFile(String filename){
        String completePath = MCTL.FileLocation + filename + MCTL.FileExtension;

        return getClass().getResourceAsStream("/"+ completePath);
    }

}
