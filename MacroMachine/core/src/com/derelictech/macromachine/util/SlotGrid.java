package com.derelictech.macromachine.util;

import com.derelictech.macromachine.tiles.Tile;

/**
 * Created by Tim on 4/15/2016.
 */
public class SlotGrid extends Grid<Slot> {
    /**
     * Constructor for SlotGrid
     *
     * @param cols The number of columns this will have
     * @param rows The number of rows this will have
     */
    public SlotGrid(int cols, int rows) {
        super(cols, rows);

        for(int i = 0; i < cols; i++) {
            for(int j = 0; j < rows; j++){
                addItemAt(new Slot(i, j, 1, 1), i, j);
            }
        }
    }

    public boolean addTileAt(Tile tile, int x, int y) {
        Slot s = getItemAt(x, y);
        if(s.getTile() == null) {
            s.setTile(tile);
            return true;
        }
        else return false;
    }

    public Tile getTileAt(int x, int y) {
        return getItemAt(x, y).getTile();
    }

    public Tile removeTileAt(int x, int y) {
        return getItemAt(x, y).removeTile();
    }
}
