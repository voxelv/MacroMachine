package com.derelictech.macromachine.tiles.units;

import com.derelictech.macromachine.tiles.Tile;
import com.derelictech.macromachine.util.TileGrid;

/**
 * Created by Tim on 4/15/2016.
 */
public class MultiTile extends Tile {
    protected TileGrid tileGrid;
    protected int gridWidth;
    protected int gridHeight;

    public MultiTile(TileGrid tileGrid, int gridWidth, int gridHeight) {
        super();
        this.tileGrid = tileGrid;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    protected boolean addTileAt(Tile tile, int gridX, int gridY) {
        if(gridX > this.gridWidth - 1 || gridX < 0) return false;
        if(gridY > this.gridHeight - 1 || gridY < 0) return false;

        return tileGrid.addTileAt(tile, this.gridX + gridX, this.gridY + gridY);
    }

    protected Tile removeTileAt(int gridX, int gridY) {
        if(gridX > this.gridWidth - 1 || gridX < 0) return null;
        if(gridY > this.gridHeight - 1 || gridY < 0) return null;

        return tileGrid.removeTileAt(this.gridX + gridX, this.gridY + gridY);
    }
}
