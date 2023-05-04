package net.abaaja.mctl.bridge;

import dk.aau.p4.abaaja.Lib.Interpreter.IGameBridge;
import net.abaaja.mctl.entity.custom.TurtleEntity;

public class GameBridge implements IGameBridge {

    public TurtleEntity turtle;
    public int MoveDelay = 500;
    public int BlockInteractDelay = 500;

    public GameBridge(TurtleEntity _turtle) {
        turtle = _turtle;
    }
    @Override
    public void print(String s) {

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
        sleep(MoveDelay);
    }

    @Override
    public void turnRight() {
        turtle.turnRight();
        sleep(MoveDelay);
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

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
