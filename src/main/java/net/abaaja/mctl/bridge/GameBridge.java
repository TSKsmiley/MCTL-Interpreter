package net.abaaja.mctl.bridge;

import dk.aau.p4.abaaja.Lib.Interpreter.IGameBridge;
import net.abaaja.mctl.entity.custom.TurtleEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameBridge implements IGameBridge {

    public static ArrayList<GameBridge> gameBridges = new ArrayList<>();

    public TurtleEntity turtle;
    public Player player;
    public int MoveDelay = 200;
    public int BlockInteractDelay = 200;
    public int RotateDelay = 200;
    private int RotateMinDelay = 100; // If set to less than this, the mod breaks

    private Boolean isReading = false;
    private String readBuffer = "";

    public GameBridge(TurtleEntity _turtle, Player _player) {
        turtle = _turtle;
        player = _player;
        gameBridges.add(this);
    }

    @Override
    public void print(String s) {
        player.sendSystemMessage(Component.literal(s));
    }

    @Override
    public String read() {
        isReading = true;
        String _readBuffer = null;
        while (isReading) {

            if (!Objects.equals(readBuffer, "")){
                _readBuffer = readBuffer;
                readBuffer = "";
                isReading = false;
            }
            
            sleep(100);
        }
        assert _readBuffer != null;
        return _readBuffer;
    }

    @Override
    public void setDelay(int delay) {
        this.MoveDelay = delay;
        this.BlockInteractDelay = delay;
        this.RotateDelay = Math.max(delay, this.RotateMinDelay);
    }

    @Override
    public boolean moveForward() {
        var ret = turtle.moveForward();
        sleep(MoveDelay);
        return ret;
    }

    @Override
    public boolean moveUp() {
        var ret = turtle.moveUp();
        sleep(MoveDelay);
        return ret;
    }

    @Override
    public boolean moveDown() {
        var ret = turtle.moveDown();
        sleep(MoveDelay);
        return ret;
    }

    @Override
    public boolean moveBackward() {
        var ret = turtle.moveBackward();
        sleep(MoveDelay);
        return ret;
    }

    @Override
    public void turnLeft() {
        turtle.turnLeft();
        sleep(RotateDelay);
    }

    @Override
    public void turnRight() {
        turtle.turnRight();
        sleep(RotateDelay);
    }

    @Override
    public void breakFront() {
        turtle.breakFront();
        sleep(BlockInteractDelay);
    }

    @Override
    public void breakAbove() {
        turtle.breakAbove();
        sleep(BlockInteractDelay);
    }

    @Override
    public void breakUnder() {
        turtle.breakUnder();
        sleep(BlockInteractDelay);
    }

    @Override
    public void placeFront(String s) {
        turtle.placeFront(s);
        sleep(BlockInteractDelay);
    }

    @Override
    public void placeAbove(String s) {
        turtle.placeAbove(s);
        sleep(BlockInteractDelay);
    }

    @Override
    public void placeUnder(String s) {
        turtle.placeUnder(s);
        sleep(BlockInteractDelay);
    }

    @Override
    public String blockFront() {
        var ret = turtle.blockFront();
        sleep(BlockInteractDelay);
        return ret;
    }

    @Override
    public String blockAbove() {
        var ret = turtle.blockAbove();
        sleep(BlockInteractDelay);
        return ret;
    }

    @Override
    public String blockUnder() {
        var ret = turtle.blockUnder();
        sleep(BlockInteractDelay);
        return ret;
    }

    @Override
    public void internal_terminate() {
        gameBridges.remove(this);
        turtle.Terminate();
    }

    public void ChatMessage(String s, @Nullable Player _player) {
        if (isReading && player.equals(_player))
            readBuffer = s;
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
