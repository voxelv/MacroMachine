package com.derelictech.macromachine.tiles;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.derelictech.macromachine.util.Grid;
import com.derelictech.macromachine.util.GridDirection;

/**
 * Created by Tim on 4/14/2016.
 */
public class Tile extends Group {
    protected Sprite sprite;
    protected Grid grid;
    protected int gridX = 0, gridY = 0;

    public Tile() {
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
    public void setGrid(Grid grid) {
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
                unit = (Tile) grid.getItemAt(gridX + 1, gridY);    // Get the unit
                break;

            case UP:
                if (gridY == grid.getCols() - 1) break;      // Stop at edge of grid
                unit = (Tile) grid.getItemAt(gridX, gridY + 1);    // Get the unit
                break;

            case LEFT:
                if (gridX == 0) break;                           // Stop at edge of grid
                unit = (Tile) grid.getItemAt(gridX - 1, gridY);    // Get the unit
                break;

            case DOWN:
                if (gridY == 0) break;                           // Stop at edge of grid
                unit = (Tile) grid.getItemAt(gridX, gridY - 1);    // Get the unit
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

    /**
     * Executes before this is added to a {@link Grid}
     * @param grid The {@link Grid} this is about to be added to
     * @param x The x coordinate of the {@link Grid} this is about to be added to
     * @param y The y coordinate of the {@link Grid} this is about to be added to
     */
    public void preAdditionToGrid(Grid grid, int x, int y) {
        this.setGrid(grid);
        this.setGridPos(x, y);
    }

    /**
     * Executes after this is added to a {@link Grid}
     * @param grid The {@link Grid} that this was added to.
     * @param x The {@link Grid} x coordinate this was added to
     * @param y The {@link Grid} y coordinate this was added to
     */
    public void postAdditionToGrid(Grid grid, int x, int y) {

    }

    /**
     * Executes before this is removed from a {@link Grid}
     * @param grid The {@link Grid} this is about to be removed from
     */
    public void preRemovalFromGrid(Grid grid) {

    }

    /**
     * Executes after this is removed from a {@link Grid}
     * @param grid The {@link Grid} this was removed from
     */
    public void postRemovalFromGrid(Grid grid) {

    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        sprite.setSize(width, height);
    }
}
