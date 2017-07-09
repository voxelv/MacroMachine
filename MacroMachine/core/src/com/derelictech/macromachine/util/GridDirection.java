package com.derelictech.macromachine.util;

/**
 * A simple enumeration for 90 degree directions
 * @author Tim Slippy, voxelv
 */
public enum GridDirection {
    RIGHT,
    UP,
    LEFT,
    DOWN;

    /**
     * Gets the spacial opposite of this direction's direction
     * (like left to right and vice versa, up to down and vice versa)
     * @return Returns the spacial opposite.
     */
    public GridDirection invert() {
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
