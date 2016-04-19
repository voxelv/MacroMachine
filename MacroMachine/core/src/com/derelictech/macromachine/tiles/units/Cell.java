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
import com.derelictech.macromachine.util.TileGrid;

/**
 * A Cell
 * @author Tim Slippy, voxelv
 */
public class Cell extends MultiTile {
    private Sprite cellBackground;
    public boolean started  = false;
    public int degrees = 0;

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
        this.setBounds(0, 0, cellBackground.getWidth(), cellBackground.getHeight());
        setTouchable(Touchable.enabled);

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
            int count = 0;
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Clicked Cell! " + count++);
                return true;
            }
        });
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
        return (gridX >= this.gridX && gridX < this.gridWidth &&
                gridY >= this.gridY && gridY < this.gridHeight);
    }

    @Override
    public void mtDraw(Batch batch, float parentAlpha) {
        cellBackground.draw(batch, parentAlpha);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }
}
