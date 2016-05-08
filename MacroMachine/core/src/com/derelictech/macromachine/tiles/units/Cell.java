package com.derelictech.macromachine.tiles.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
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
    private float closeAnimSpeed = 0.15f;
    private Sprite closeAnimCurrentFrame;
    private boolean closed = false;

    private Array<ENetwork> eNetworks;
    private boolean networksDirty = false;
    private float netTickRate = 1;
    private Timer.Task netTickTask;

    private long currentHP = 300;
    private long maxHP = 300;

    public void doMining() {
        Gdx.app.log("CELL", "MINE ALL THE THINGS WOO");
        for(ENetwork en : eNetworks) {
            en.doConsumption();
        }
    }

    private class CloseAction extends Action {
        private float timer = 0;
        @Override
        public boolean act(float delta) {
            if(timer == 0) {
                Cell.this.disableNetTick(); // Disable Net Ticking
                closed = true;              // Set Closed
            }
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

        cellCloseTextures = Assets.inst.getFrameSequence("cell_anim/cell_edge2_pad1");
        closeAnimation = new Animation(closeAnimSpeed, cellCloseTextures);
        closeAnimCurrentFrame = new Sprite(closeAnimation.getKeyFrames()[0]);
        closeAnimCurrentFrame.setPosition(-2.0f/ Const.TEXTURE_RESOLUTION, -2.0f/Const.TEXTURE_RESOLUTION);
        closeAnimCurrentFrame.setSize(cellBackground.getWidth(), cellBackground.getHeight());

        // The ControlUnit
        controlUnit = new ControlUnit(this, 0, 0, 5, 0, 500);
        addUnitAt(controlUnit, gridX + 2, gridY + 2);


        addUnitAt(new Wire(this), gridX + 3, gridY + 2);
        addUnitAt(new Generator(this), gridX + 3, gridY + 1);
        addUnitAt(new Generator(this), gridX + 2, gridY + 1);
        addUnitAt(new Generator(this), gridX + 1, gridY + 1);
        addUnitAt(new EBattery(this), gridX + 2, gridY + 3);

        addListener(new InputListener(){
            int count = 0;
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.debug("CELL", "Clicked Cell! " + count++);
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
                    Gdx.app.debug("CELL NET", "Ticked " + net.toString());
                }
                Gdx.app.debug("CELL", "Control Unit Storage: " + controlUnit.amountStored() + "/" + controlUnit.getCapacity());
                Gdx.app.debug("CELL", "Ticked " + eNetworks.size + " nets.----------------------------------------------\n");
            }
        };
        Timer.schedule(netTickTask, netTickRate, netTickRate);
    }

    public boolean move(GridDirection dir) {
        if(!closed) {
            return tileGrid.moveMultitile(this, dir);
        }
        else return false;
    }

    public void addUnitAt(Unit unit, int gridX, int gridY) {
        addTileAt(unit, gridX, gridY);
        networksDirty = true;
    }

    public Unit removeUnitAt(int gridX, int gridY) {
        Tile temp = tileGrid.getTileAt(gridX, gridY);
        if(temp instanceof ControlUnit && (temp == controlUnit))
        {
            Gdx.app.log("CELL", "Cannot remove the Cell's ControlUnit.");
            return null;
        }
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
        Gdx.app.debug("CELL", "GETTING UNITS FROM CELL *****");
        Grid<Unit> unitGrid = new Grid<Unit>(this.gridWidth, this.gridHeight);
        for(int x = this.gridX; x < this.gridX + this.gridWidth; x++){
        for(int y = this.gridY; y < this.gridY + this.gridHeight; y++) {
            Tile t = tileGrid.getTileAt(x, y);
            if(t instanceof Unit) {
                Gdx.app.debug("CELL", "Found unit: " + t + " at x: "+x+" y: "+y);
                unitGrid.addItemAt(((Unit) t), x - this.gridX, y - this.gridY);
            }
        }
        }

        Gdx.app.debug("CELL", unitGrid.toString());
        return unitGrid;
    }

    public ControlUnit getControlUnit() {
        return controlUnit;
    }

    public long getHP() {
        return currentHP;
    }

    public void setHP(long currentHP) {
        this.currentHP = currentHP;
    }

    public void takeDamage(long damage) {
        Gdx.app.log("CELL", "HP: " + currentHP + "/" + maxHP);
        if(damage >= currentHP) {
            currentHP = 0;
            disableNetTick();
            fire(new MacroMachineEvent(MacroMachineEvent.Type.cellDeath));
        }
        else
            currentHP -= damage;
    }

    public void closeCell() {
        Gdx.app.log("CELL", "Closing Cell Now");
        this.addAction(closeAction);
    }

    public float timeTillClosed() {
        return closeAnimation.getAnimationDuration() + 0.3f;
    }

    public boolean isClosed() {
        return closed;
    }

    private void recalculateNetworks() {
        Gdx.app.debug("CELL", "RECALCULATING NETS <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
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

    public void disableNetTick() {
        netTickTask.cancel();
    }
    public void enableNetTick() {
        Timer.schedule(netTickTask, netTickRate, netTickRate);
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
        Gdx.app.debug("CELL", "CELL GRID POS: x: " + gridX + " y: " + gridY);
        if(cellBackground != null)
            cellBackground.setPosition(this.getX() - 2.0f/ Const.TEXTURE_RESOLUTION, this.getY() - 2.0f/Const.TEXTURE_RESOLUTION);
    }
}
