package net.abaaja.mctl.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dk.aau.p4.abaaja.MCTLInterpreter;
import net.abaaja.mctl.MCTL;
import net.abaaja.mctl.command.getSuggestionsMCTL;
import net.abaaja.mctl.entity.ModEntityTypes;
import net.abaaja.mctl.entity.custom.TurtleEntity;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.abaaja.mctl.bridge.GameBridge;
import org.antlr.v4.runtime.CharStream;

import javax.annotation.Nullable;
import java.io.*;

public class LoadFileCommand {


    public LoadFileCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("loadfile")
                .then(Commands.argument("filename", StringArgumentType.string())
                    .suggests(getSuggestionsMCTL::getSuggestions)
                .executes(this::run)));
    }

    private int run(CommandContext<CommandSourceStack> ctx) {
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

        TurtleEntity turtle = spawnTurtle(source);

        testThread(turtle, is);
    }

    private TurtleEntity spawnTurtle(CommandSourceStack source){
        TurtleEntity turtle = ModEntityTypes.TURTLE.get().create(source.getLevel());
        source.getLevel().addFreshEntity(turtle);
        return (TurtleEntity) source.getLevel().getEntity(turtle.getId());
    }

    @Nullable
    public InputStream GetFile(String filename){
        String completePath = MCTL.FileLocation + filename + MCTL.FileExtension;

        return getClass().getResourceAsStream("/"+ completePath);
    }

    private void testThread(TurtleEntity turtle, InputStream is){
        new Thread(()->{
            //var bridge = new GameBridge(turtle);
            var interp = new MCTLInterpreter(new GameBridge(turtle));
            interp.run((CharStream) is);
        }).start();
    }

}
