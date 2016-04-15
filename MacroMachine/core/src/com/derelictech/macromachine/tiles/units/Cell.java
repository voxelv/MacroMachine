package com.derelictech.macromachine.tiles.units;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.CellGrid;
import com.derelictech.macromachine.util.TileGrid;

/**
 * A Cell
 * @author Tim Slippy, voxelv
 */
public class Cell extends MultiTile {
    public boolean started  = false;
    public int degrees = 0;

    /**
     * Constructor for Cell
     * Creates a {@link CellGrid}
     * Sets its size to the size of its {@link CellGrid}
     * Adds the {@link CellGrid} to its children
     *
     * TODO: TESTING FUNCTIONALITY TO BE REMOVED
     * Adds some Wires and the ControlUnit for testing purposes
     */
    public Cell(TileGrid tileGrid, int gridWidth, int gridHeight) {
        super(tileGrid, gridWidth, gridHeight);

        sprite = new Sprite(Assets.inst.getRegion("cell_edge2_pad1"));
        sprite.setSize(this.gridWidth, this.gridHeight);

        for(int i = 0; i < tileGrid.getCols(); i++) {
            for(int j = 0; j < tileGrid.getRows(); j++) {
                addUnitAt(new Wire(), i, j);
            }
        }

        removeUnitAt(0, 1);
        removeUnitAt(1, 4);
        removeUnitAt(4, 3);
        removeUnitAt(3, 0);
        removeUnitAt(2, 2);
        addUnitAt(new ControlUnit(), 2, 2);

        addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(started)
                    started = false;
                else
                    started = true;
                return true;
            }
        });
    }

    public void addUnitAt(Unit unit, int gridX, int gridY) {
        addTileAt(unit, gridX, gridY);
    }

    public Unit removeUnitAt(int gridX, int gridY) {
        return (Unit) removeTileAt(gridX, gridY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        applyTransform(batch, computeTransform());
        sprite.draw(batch);
        resetTransform(batch);
        super.draw(batch, parentAlpha);
    }
}
