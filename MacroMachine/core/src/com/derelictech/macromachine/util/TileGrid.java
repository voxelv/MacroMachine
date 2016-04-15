package com.derelictech.macromachine.util;

import com.derelictech.macromachine.tiles.Tile;

/**
 * Created by Tim on 4/14/2016.
 */
public class TileGrid extends PaddedGrid<Tile> {

    public TileGrid(int cols, int rows, float edgePad, float inPad, boolean initWithTiles) {
        super(cols, rows, edgePad, inPad, "game_grid_edge5_pad3");

        if(initWithTiles) addAllTiles();
    }

    private void addAllTiles() {
        for(int i = 0; i < cols; i++) {
            for(int j = 0; j < rows; j++) {
                Tile t = new Tile();
                addTileAt(t, i, j);
                addActor(t);
            }
        }
    }

    public Tile getTileAt(int x, int y) {
        return getItemAt(x, y);
    }

    public boolean addTileAt(Tile t, int x, int y) {
        t.preAdditionToGrid(this, x, y);

        t.setPosition(edgePad + x + x* inPad, edgePad + y + y* inPad);        // Set Position
        boolean b = super.addItemAt(t, x, y);                                         // Add to the grid
        this.addActor(t);                                                       // Add to children

        t.postAdditionToGrid(this, x, y);
        return b;
    }

    public Tile removeTileAt(int x, int y) {
        Tile tile = getTileAt(x, y);

        tile.preRemovalFromGrid(this);

        deleteItemAt(x, y);
        removeActor(tile);

        tile.postRemovalFromGrid(this);
        return tile;
    }
}

