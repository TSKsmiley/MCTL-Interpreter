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
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.abaaja.mctl.bridge.GameBridge;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.antlr.shadow.v4.runtime.CharStream;
import org.antlr.shadow.v4.runtime.CharStreams;

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
        PrepareAndRunScript(ctx.getSource(), StringArgumentType.getString(ctx, "filename"));


        return 0;
    }

    public void PrepareAndRunScript(CommandSourceStack source, String filename){

        CharStream stream = null;
        try {
            stream = GetFile(filename);
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

        TurtleEntity turtle = spawnTurtle(source, player);

        testThread(turtle, player, stream);
    }

    private TurtleEntity spawnTurtle(CommandSourceStack source, Player player){
        TurtleEntity turtle = ModEntityTypes.TURTLE.get().create(source.getLevel());
        assert turtle != null;
        turtle.setPos(blockToVec3(player.blockPosition()));
        turtle.setYRot(player.getYHeadRot());
        turtle.moveForward(); // spawns the turtle in the correct position
        source.getLevel().addFreshEntity(turtle);
        return (TurtleEntity) source.getLevel().getEntity(turtle.getId());
    }

    private Vec3 blockToVec3(BlockPos pos){
        return new Vec3(pos.getX(),pos.getY(),pos.getZ());
    }

    @Nullable
    public CharStream GetFile(String filename) throws IOException {
        String completePath = MCTL.FileLocation + filename + MCTL.FileExtension;
        var file = new File("/"+ completePath);
        InputStream is = getClass().getResourceAsStream("/"+ completePath);
        if (is == null) throw new IOException();
        return CharStreams.fromStream(is);
    }

    private void testThread(TurtleEntity turtle, Player player, CharStream stream){
        // this is in its own var to avoid being collected by the garbage collector
        var thread = new Thread(()->{

            var interp = new MCTLInterpreter(new GameBridge(turtle, player));

            // sleep 200 milliseconds
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}

            interp.run(stream);
        });
        thread.start();
    }

}
