package com.derelictech.macromachine.units;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.derelictech.macromachine.items.Item;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.CellGrid;
import com.derelictech.macromachine.util.Grid;
import com.derelictech.macromachine.util.GridDirection;

/**
 * Created by Tim on 4/5/2016.
 */
public abstract class Unit extends Group implements Item {
    protected Sprite sprite;
    protected Grid grid;
    protected int gridX = 0, gridY = 0;

    public Unit(String unit_name) {
        sprite = new Sprite(Assets.inst.getRegion(unit_name));
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    protected Unit getNeighbor(GridDirection dir) {
        Unit unit = null;
        switch (dir) {
            case RIGHT:
                if (gridX == grid.getRows() - 1) break;      // Stop at edge of grid
                unit = (Unit) grid.getItemAt(gridX + 1, gridY);    // Get the unit
                break;

            case UP:
                if (gridY == grid.getCols() - 1) break;      // Stop at edge of grid
                unit = (Unit) grid.getItemAt(gridX, gridY + 1);    // Get the unit
                break;

            case LEFT:
                if (gridX == 0) break;                           // Stop at edge of grid
                unit = (Unit) grid.getItemAt(gridX - 1, gridY);    // Get the unit
                break;

            case DOWN:
                if (gridY == 0) break;                           // Stop at edge of grid
                unit = (Unit) grid.getItemAt(gridX, gridY - 1);    // Get the unit
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

    public abstract void preAdditionToGrid(Grid grid, int x, int y);
    public abstract void postAdditionToGrid(Grid grid, int x, int y);
    public abstract void preRemovalFromGrid(Grid grid);
    public abstract void postRemovalFromGrid(Grid grid);

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        sprite.setSize(width, height);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x, y);
    }
}
