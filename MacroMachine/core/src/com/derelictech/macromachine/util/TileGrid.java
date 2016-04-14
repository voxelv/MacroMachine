package com.derelictech.macromachine.util;

import com.derelictech.macromachine.tiles.Tile;

/**
 * Created by Tim on 4/14/2016.
 */
public class TileGrid extends PaddedGrid<Tile> {

    public TileGrid(int cols, int rows) {
        super(cols, rows, 0, 0, "tile_placeholder");
    }
}

