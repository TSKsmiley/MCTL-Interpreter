package net.abaaja.mctl.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dk.aau.p4.abaaja.MCTLFormatter;
import net.abaaja.mctl.MCTL;
import net.abaaja.mctl.bridge.GameBridge;
import net.abaaja.mctl.command.getSuggestionsMCTL;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.antlr.shadow.v4.runtime.CharStream;
import org.antlr.shadow.v4.runtime.CharStreams;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.file.Path;

public class FormatFileCommand {


    public FormatFileCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("formatfile")
                .then(Commands.argument("filename", StringArgumentType.string())
                        .suggests(getSuggestionsMCTL::getSuggestions)
                    .executes(this::run)));
    }

    private int run(CommandContext<CommandSourceStack> ctx) {
        String filename = StringArgumentType.getString(ctx, "filename");
        System.out.println("Formatting file: " + filename);
        FormatScript(ctx.getSource(), filename);
        return 0;
    }

    public void FormatScript(CommandSourceStack source, String filename){

        Path filePath = GetFile(filename);
        CharStream stream = null;
        try {
            stream = CharStreams.fromPath(filePath);
        } catch (IOException ignored) {}

        if (stream == null) {
            source.sendFailure(Component.literal("File not found"));
            return;
        }

        Player player = source.getPlayer();

        if(player == null) {
            source.sendFailure(Component.literal("Command must be executed by player"));
            return;
        }

        testThread(player, stream, filename);

    }

    @Nullable
    public Path GetFile(String filename) {
        String completePath = MCTL.FileLocation + filename + MCTL.FileExtension;
        var file = new File("/"+ completePath);
        return file.toPath();
    }

    public void WriteFile(String filename, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(MCTL.FileLocation + filename + ".formatted" + MCTL.FileExtension));
            writer.write(content);
            writer.close();
            System.out.println("Successfully wrote formatted file");
        } catch (IOException e) {
            System.err.println("Could not write formatted file: " + e.getMessage());
        }
    }

    private void testThread(Player player, CharStream stream, String filename){
        // this is in its own var to avoid being collected by the garbage collector
        var thread = new Thread(()->{

            MCTLFormatter formatter = new MCTLFormatter(new GameBridge(player));

            // sleep 200 milliseconds
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}

            String formatted = formatter.run(stream);

            WriteFile(filename, formatted);
        });
        thread.start();
    }

}
