package com.derelictech.macromachine.tiles.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.StringBuilder;
import com.derelictech.macromachine.tiles.Tile;
import com.derelictech.macromachine.util.*;

/**
 * A Cell
 * @author Tim Slippy, voxelv
 */
public class Cell extends MultiTile {
    private Sprite cellBackground;
    private ControlUnit controlUnit;

    private Array<TextureRegion> cellCloseTextures;
    private Animation closeAnimation;
    private float closeAnimSpeed = 0.25f;
    private Sprite closeAnimCurrentFrame;

    private class CloseAction extends Action {
        @Override
        public boolean act(float delta) {
            return false;
        }
    }

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

        cellCloseTextures = Assets.inst.getCellCloseAnimation();
        closeAnimation = new Animation(closeAnimSpeed, cellCloseTextures);
        closeAnimCurrentFrame = new Sprite(closeAnimation.getKeyFrame(0.25f)); //TODO: This is for testing, the CloseAction will change which frame to show
        closeAnimCurrentFrame.setPosition(-2.0f/ Const.TEXTURE_RESOLUTION, -2.0f/Const.TEXTURE_RESOLUTION);
        closeAnimCurrentFrame.setSize(cellBackground.getWidth(), cellBackground.getHeight());

        // The ControlUnit
        controlUnit = new ControlUnit(this);
        addUnitAt(controlUnit, gridX + 2, gridY + 2);

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

    public Grid<Unit> getUnits() {
        System.out.println("GETTING UNITS FROM CELL *****");
        Grid<Unit> unitGrid = new Grid<Unit>(this.gridWidth, this.gridHeight);
        for(int x = this.gridX; x < this.gridX + this.gridWidth; x++){
        for(int y = this.gridY; y < this.gridY + this.gridHeight; y++) {
            Tile t = tileGrid.getTileAt(x, y);
            if(t instanceof Unit) {
                System.out.println("Found unit: " + t + " at x: "+x+" y: "+y);
                unitGrid.addItemAt(((Unit) t), x - this.gridX, y - this.gridY);
            }
        }
        }

        System.out.println(unitGrid.toString());
        return unitGrid;
    }

    @Override
    public void mtDraw(Batch batch, float parentAlpha) {
        cellBackground.draw(batch, parentAlpha);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        applyTransform(batch, computeTransform());
        closeAnimCurrentFrame.draw(batch, parentAlpha);
        resetTransform(batch);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        System.out.println("CELL GRID POS: x: " + gridX + " y: " + gridY);
        if(cellBackground != null)
            cellBackground.setPosition(this.getX() - 2.0f/ Const.TEXTURE_RESOLUTION, this.getY() - 2.0f/Const.TEXTURE_RESOLUTION);
    }
}
