package com.derelictech.macromachine.tiles;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.Grid;
import com.derelictech.macromachine.util.GridDirection;
import com.derelictech.macromachine.util.TileGrid;

/**
 * Created by Tim on 4/14/2016.
 */
public class Tile extends Group {
    public Sprite sprite;
    protected TileGrid grid;
    protected int gridX = 0, gridY = 0;

    public Tile() {
        sprite = new Sprite(Assets.inst.getRegion("tile_placeholder"));
        setSize(1, 1);
        setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        applyTransform(batch, computeTransform());
        sprite.draw(batch, parentAlpha);
        resetTransform(batch);
        super.draw(batch, parentAlpha);
    }

    /**
     * Returns the
     * @return
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Sets the {@link Grid} this Unit is a part of
     * @param grid The {@link Grid} to set this Unit's {@link Grid} to
     */
    public void setGrid(TileGrid grid) {
        this.grid = grid;
    }

    /**
     * @return Returns the x coordinate of this Unit in its {@link Grid}
     */
    public int getGridX() {
        return gridX;
    }

    /**
     * @return Returns the y coordinate of this Unit in its {@link Grid}
     */
    public int getGridY() {
        return gridY;
    }

    /**
     * Gets a neighbor of this Unit
     * @param dir The direction to get the neighbor from
     * @return Returns the Unit adjacent to this Unit at direction {@link Tile#getNeighbor(GridDirection)}.
     * Returns null if no Unit at that position or out of the {@link Grid} bounds
     */
    protected Tile getNeighbor(GridDirection dir) {
        Tile unit = null;
        switch (dir) {
            case RIGHT:
                if (gridX == grid.getRows() - 1) break;      // Stop at edge of grid
                unit = grid.getTileAt(gridX + 1, gridY);    // Get the unit
                break;

            case UP:
                if (gridY == grid.getCols() - 1) break;      // Stop at edge of grid
                unit = grid.getTileAt(gridX, gridY + 1);    // Get the unit
                break;

            case LEFT:
                if (gridX == 0) break;                           // Stop at edge of grid
                unit = grid.getTileAt(gridX - 1, gridY);    // Get the unit
                break;

            case DOWN:
                if (gridY == 0) break;                           // Stop at edge of grid
                unit = grid.getTileAt(gridX, gridY - 1);    // Get the unit
                break;

            default:
                break;
        }
        return unit;
    }

    public void setGridPos(int x, int y) {
        gridX = x;
        gridY = y;
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        sprite.setSize(width, height);
    }

    /**
     * Executes before this is added to a {@link TileGrid}
     * @param grid The {@link TileGrid} this is about to be added to
     * @param x The x coordinate of the {@link TileGrid} this is about to be added to
     * @param y The y coordinate of the {@link TileGrid} this is about to be added to
     */
    public void preAdditionToGrid(TileGrid grid, int x, int y) {
        this.setGrid(grid);
        this.setGridPos(x, y);
    }

    /**
     * Executes after this is added to a {@link TileGrid}
     * @param grid The {@link Grid} that this was added to.
     * @param x The {@link TileGrid} x coordinate this was added to
     * @param y The {@link TileGrid} y coordinate this was added to
     */
    public void postAdditionToGrid(TileGrid grid, int x, int y) {

    }

    /**
     * Executes before this is removed from a {@link TileGrid}
     * @param grid The {@link TileGrid} this is about to be removed from
     */
    public void preRemovalFromGrid(TileGrid grid) {

    }

    /**
     * Executes after this is removed from a {@link TileGrid}
     * @param grid The {@link TileGrid} this was removed from
     */
    public void postRemovalFromGrid(TileGrid grid) {

    }
}
