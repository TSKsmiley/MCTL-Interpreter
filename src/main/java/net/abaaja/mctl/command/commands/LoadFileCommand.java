package net.abaaja.mctl.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.antlr.runtime.ANTLRFileStream;
import net.minecraft.network.chat.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoadFileCommand extends ICommand {

    public LiteralArgumentBuilder<CommandSourceStack> commandName = Commands.literal("loadfile")
            .then(Commands.argument("filename", StringArgumentType.string()));

    public LoadFileCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(commandName
                .executes(this::run));
    }

    private int run(CommandContext<CommandSourceStack> source) throws CommandSyntaxException {
         // write contents of file to chat
        sendChatFromFile(source.getSource(), StringArgumentType.getString(source, "filename"));


        return 0;
    }

    public void sendChatFromFile(CommandSourceStack source, String filename){
        try {
            String userDir = System.getProperty("user.dir");
            String completePath = userDir + "\\mctl-scripts\\" + filename + ".mctl";

            InputStream is = getClass().getResourceAsStream("/"+ completePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                source.sendSuccess(Component.literal(line), false);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
