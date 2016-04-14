package com.derelictech.macromachine.util;

import com.derelictech.macromachine.tiles.Tile;

/**
 * Created by Tim on 4/14/2016.
 */
public class TileGrid extends PaddedGrid<Tile> {

    public TileGrid(int cols, int rows, boolean initWithTiles) {
        super(cols, rows, 0, 0, "tile_placeholder");

        if(initWithTiles) addAllTiles();
    }

    private void addAllTiles() {
        for(int i = 0; i < cols; i++) {
            for(int j = 0; j < rows; j++) {
                add(new Tile(), i, j);
            }
        }
    }
}

