package com.derelictech.macromachine.util;


import com.derelictech.macromachine.tiles.Tile;

/**
 * A Slot contains a {@link Tile}
 */
public class Slot {
    private int gridX, gridY;
    private float width, height;

    private Tile tile;

    public Slot(int gridX, int gridY, float width, float height) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.width = width;
        this.height = height;

        this.tile = new Tile();
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile removeTile() {
        Tile t = this.tile;
        this.tile = null;
        return t;
    }
}
