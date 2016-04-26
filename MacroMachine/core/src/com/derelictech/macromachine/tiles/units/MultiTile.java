package com.derelictech.macromachine.tiles.units;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.derelictech.macromachine.tiles.Tile;
import com.derelictech.macromachine.util.TileGrid;

/**
 * Created by Tim on 4/15/2016.
 */
public abstract class MultiTile extends Group {
    protected TileGrid tileGrid;
    protected int gridX;
    protected int gridY;
    protected int gridWidth;
    protected int gridHeight;

    public MultiTile(TileGrid tileGrid, int gridX, int gridY, int gridWidth, int gridHeight) {
        super();
        this.tileGrid = tileGrid;
        this.gridX = gridX;
        this.gridY = gridY;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;

        tileGrid.addMultiTile(this, gridX, gridY);
    }

    protected boolean addTileAt(Tile tile, int gridX, int gridY) {
        if(gridX > this.gridX + this.gridWidth - 1 || gridX < this.gridX) return false;
        if(gridY > this.gridY + this.gridHeight - 1 || gridY < this.gridY) return false;

        return tileGrid.addTileAt(tile, gridX, gridY);
    }

    protected Tile removeTileAt(int gridX, int gridY) {
        if(gridX > this.gridX + this.gridWidth - 1 || gridX < this.gridX) return null;
        if(gridY > this.gridY + this.gridHeight - 1 || gridY < this.gridY) return null;

        return tileGrid.removeTileAt(gridX, gridY);
    }

    /**
     * To draw the multitile background
     * @param batch the batch to draw to.
     * @param parentAlpha the alpha to draw with
     */
    public abstract void mtDraw(Batch batch, float parentAlpha);

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridPos(int x, int y) {
        this.gridX = x;
        this.gridY = y;
    }
}
