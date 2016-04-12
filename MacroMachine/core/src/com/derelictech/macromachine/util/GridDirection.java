package com.derelictech.macromachine.util;

/**
 * Created by Tim on 4/11/2016.
 */
public enum GridDirection {
    RIGHT,
    UP,
    LEFT,
    DOWN;

    public GridDirection inv() {
        switch(this) {
            case RIGHT:
                return LEFT;
            case UP:
                return DOWN;
            case LEFT:
                return RIGHT;
            case DOWN:
                return UP;
            default:
                return null;
        }
    }
}
