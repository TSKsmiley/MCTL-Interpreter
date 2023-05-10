package net.abaaja.mctl.bridge;

import dk.aau.p4.abaaja.Lib.Interpreter.IGameBridge;
import net.abaaja.mctl.entity.custom.TurtleEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class GameBridge implements IGameBridge {

    public TurtleEntity turtle;
    public Player player;
    public int MoveDelay = 500;
    public int BlockInteractDelay = 500;

    public GameBridge(TurtleEntity _turtle, Player _player) {
        turtle = _turtle;
        player = _player;
    }

    public void print(String s) {
        player.sendSystemMessage(Component.literal(s));
    }

    @Override
    public String read() {
        return "not implemented";
    }

    public boolean moveForward() {
        var ret = turtle.moveForward();
        sleep(MoveDelay);
        return ret;
    }

    public boolean moveUp() {
        var ret = turtle.moveUp();
        sleep(MoveDelay);
        return ret;
    }

    public boolean moveDown() {
        var ret = turtle.moveDown();
        sleep(MoveDelay);
        return ret;
    }

    public boolean moveBackward() {
        var ret = turtle.moveBackward();
        sleep(MoveDelay);
        return ret;
    }

    public void turnLeft() {
        turtle.turnLeft();
        sleep(MoveDelay);
    }

    public void turnRight() {
        turtle.turnRight();
        sleep(MoveDelay);
    }

    public void breakFront() {
        turtle.breakFront();
        sleep(BlockInteractDelay);
    }

    public void breakAbove() {
        turtle.breakAbove();
        sleep(BlockInteractDelay);
    }

    public void breakUnder() {
        turtle.breakUnder();
        sleep(BlockInteractDelay);
    }

    public void placeFront(String s) {
        turtle.placeFront(s);
        sleep(BlockInteractDelay);
    }

    public void placeAbove(String s) {
        turtle.placeAbove(s);
        sleep(BlockInteractDelay);
    }

    public void placeUnder(String s) {
        turtle.placeUnder(s);
        sleep(BlockInteractDelay);
    }

    public String blockFront() {
        var ret = turtle.blockFront();
        sleep(BlockInteractDelay);
        return ret;
    }

    public String blockAbove() {
        var ret = turtle.blockAbove();
        sleep(BlockInteractDelay);
        return ret;
    }

    public String blockUnder() {
        var ret = turtle.blockUnder();
        sleep(BlockInteractDelay);
        return ret;
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
