package net.abaaja.mctl.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoadFileCommand {

    public LiteralArgumentBuilder<CommandSourceStack> commandName = Commands.literal("loadfile");

    public LoadFileCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(commandName
                .executes((context) -> run(context.getSource())));
    }

    private int run(CommandSourceStack source) throws CommandSyntaxException {
         // write contents of file to chat
        source.sendSuccess(Component.literal("Reading file..."), false);
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("fileTest.txt");
        try {
            String data = readFromInputStream(inputStream);

            source.sendSuccess(Component.literal(data), false);
        } catch (IOException e) {
            source.sendFailure(Component.literal("Error reading file!"));
        }

        return 0;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
