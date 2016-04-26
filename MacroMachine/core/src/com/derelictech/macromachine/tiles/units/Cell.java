package com.derelictech.macromachine.tiles.units;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.StringBuilder;
import com.derelictech.macromachine.tiles.Tile;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.Const;
import com.derelictech.macromachine.util.GridDirection;
import com.derelictech.macromachine.util.TileGrid;

/**
 * A Cell
 * @author Tim Slippy, voxelv
 */
public class Cell extends MultiTile {
    private Sprite cellBackground;

    /**
     * Constructor for Cell
     *
     * TODO: TESTING FUNCTIONALITY TO BE REMOVED
     * Adds some Wires and the ControlUnit for testing purposes
     */
    public Cell(TileGrid tileGrid, int gridX, int gridY, int gridWidth, int gridHeight) {
        super(tileGrid, gridX, gridY, gridWidth, gridHeight);

        cellBackground = new Sprite(Assets.inst.getRegion("cell_edge2_pad1"));
        cellBackground.setPosition(this.getX() - 2.0f/ Const.TEXTURE_RESOLUTION, this.getY() - 2.0f/Const.TEXTURE_RESOLUTION);
        cellBackground.setSize(2*(2.0f/Const.TEXTURE_RESOLUTION) + (this.gridWidth - 1)*3.0f/Const.TEXTURE_RESOLUTION + this.gridWidth,
                2*(2.0f/Const.TEXTURE_RESOLUTION) + (this.gridHeight - 1)*3.0f/Const.TEXTURE_RESOLUTION + this.gridHeight);

        // The ControlUnit
        addUnitAt(new ControlUnit(this), gridX + 2, gridY + 2);

        addListener(new InputListener(){
            int count = 0;
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Clicked Cell! " + count++);
                return false;
            }
        });
    }

    public boolean move(GridDirection dir) {
        return tileGrid.moveMultitile(this, dir);
    }

    public void addUnitAt(Unit unit, int gridX, int gridY) {
        addTileAt(unit, gridX, gridY);
    }

    public Unit removeUnitAt(int gridX, int gridY) {
        Tile t = removeTileAt(gridX, gridY);
        if(t instanceof Unit) return (Unit) t;
        else return null;
    }

    public boolean containsUnitAt(int gridX, int gridY) {
        return (gridX >= this.gridX && gridX < this.gridX + this.gridWidth &&
                gridY >= this.gridY && gridY < this.gridY + this.gridHeight);
    }

    @Override
    public void mtDraw(Batch batch, float parentAlpha) {
        cellBackground.draw(batch, parentAlpha);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        System.out.println("CELL GRID POS: x: " + gridX + " y: " + gridY);
        if(cellBackground != null)
            cellBackground.setPosition(this.getX() - 2.0f/ Const.TEXTURE_RESOLUTION, this.getY() - 2.0f/Const.TEXTURE_RESOLUTION);
    }
}
