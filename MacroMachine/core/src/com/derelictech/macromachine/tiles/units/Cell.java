package com.derelictech.macromachine.tiles.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.StringBuilder;
import com.derelictech.macromachine.e_net.AbstractEUnit;
import com.derelictech.macromachine.e_net.ENetwork;
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

    private Array<ENetwork> eNetworks;
    private boolean networksDirty = false;
    private float netTickRate = 1;
    private Timer.Task netTickTask;

    private class CloseAction extends Action {
        private float timer = 0;
        @Override
        public boolean act(float delta) {
            closeAnimCurrentFrame.setRegion(closeAnimation.getKeyFrame(timer));
            timer += delta;
            if(closeAnimation.isAnimationFinished(timer)) {
                timer = 0;
                Cell.this.removeAction(this);
                return true;
            }
            else return false;
        }
    }
    private CloseAction closeAction = new CloseAction();


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
        closeAnimCurrentFrame = new Sprite(closeAnimation.getKeyFrames()[0]);
        closeAnimCurrentFrame.setPosition(-2.0f/ Const.TEXTURE_RESOLUTION, -2.0f/Const.TEXTURE_RESOLUTION);
        closeAnimCurrentFrame.setSize(cellBackground.getWidth(), cellBackground.getHeight());

        // The ControlUnit
        controlUnit = new ControlUnit(this, 0, 0, 1, 0, 100);
        addUnitAt(controlUnit, gridX + 2, gridY + 2);

        addListener(new InputListener(){
            int count = 0;
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Clicked Cell! " + count++);
                return false;
            }
        });

        // ENetworks
        eNetworks = new Array<ENetwork>();
        netTickTask = new Timer.Task() {
            @Override
            public void run() {
                if(networksDirty) recalculateNetworks();
                for(ENetwork net : eNetworks) {
                    ENetwork.tickNetwork(net);
                    Gdx.app.log("CELL NET", "Ticked " + net.toString());
                }
                Gdx.app.log("CELL", "Ticked " + eNetworks.size + " nets.----------------------------------------------\n");
                Gdx.app.log("CELL", "Control Unit Storage: " + controlUnit.amountStored());
            }
        };
        Timer.schedule(netTickTask, netTickRate, netTickRate);
    }

    public boolean move(GridDirection dir) {
        return tileGrid.moveMultitile(this, dir);
    }

    public void addUnitAt(Unit unit, int gridX, int gridY) {
        addTileAt(unit, gridX, gridY);
        networksDirty = true;
    }

    public Unit removeUnitAt(int gridX, int gridY) {
        networksDirty = true;

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

    public void closeCell() {
        Gdx.app.log("CELL", "Closing Cell Now");
        this.addAction(closeAction);
    }

    private void recalculateNetworks() {
        Gdx.app.log("CELL", "RECALCULATING NETS <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        eNetworks.clear();
        for(int x = this.gridX; x < this.gridX + this.gridWidth; x++){
        for(int y = this.gridY; y < this.gridY + this.gridHeight; y++) { // Iterate through all Units in Cell
            Tile t = tileGrid.getTileAt(x, y); // Get the Unit
            if(t instanceof AbstractEUnit) { // If it's an AbstractEUnit
                if(!eNetworks.contains(((AbstractEUnit) t).getNetwork(), true)) { // If eNetworks doesn't have it
                    eNetworks.add(((AbstractEUnit) t).getNetwork()); // Add it
                }
            }
        }
        }
        networksDirty = false;
    }

    public void setNetTickRate(float perSecond) {
        float timeToNextTick = netTickTask.getExecuteTimeMillis();
        netTickRate = 1.0f/perSecond;
        netTickTask.cancel();
        Timer.schedule(netTickTask, timeToNextTick/1000, netTickRate);
    }

    @Override
    public boolean addTileAt(Tile tile, int gridX, int gridY) {
        networksDirty = true;
        return super.addTileAt(tile, gridX, gridY);
    }

    @Override
    public Tile removeTileAt(int gridX, int gridY) {
        networksDirty = true;
        return super.removeTileAt(gridX, gridY);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
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
