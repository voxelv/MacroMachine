package com.derelictech.macromachine.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.derelictech.macromachine.tiles.Material;
import com.derelictech.macromachine.tiles.Tile;
import com.derelictech.macromachine.tiles.materials.BasicMaterial;
import com.derelictech.macromachine.tiles.materials.MetalicMaterial;
import com.derelictech.macromachine.tiles.materials.RadicalMaterial;
import com.derelictech.macromachine.tiles.units.MultiTile;
import com.derelictech.macromachine.tiles.units.Unit;

/**
 * Created by Tim on 4/14/2016.
 */
public class TileGrid extends SlotGrid {

    private Sprite gridBackground;
    private Array<MultiTile> multitiles;
    public int radical = 0;


    public TileGrid(int cols, int rows, float edgePad, float inPad, boolean initWithTiles, String bgFileName) {
        super(cols, rows, edgePad, inPad);
        setTouchable(Touchable.enabled);

        gridBackground = new Sprite(Assets.inst.getRegion(bgFileName));
        multitiles = new Array<MultiTile>();

        setSize((2*edgePad + (rows - 1)*inPad + rows), (2*edgePad + (cols - 1)*inPad + cols));

        if(initWithTiles) addAllTiles();
    }

    private void addAllTiles() {
        for(int i = 0; i < cols; i++) {
            for(int j = 0; j < rows; j++) {

                Material gen = genMaterial();
                addTileAt(gen, i, j);
                addActor(gen);
            }
        }
    }

    private Material genMaterial() {
        float r =  MathUtils.random(0, 99);
        Material m;
        if(r == 0){
            m = new RadicalMaterial();
            radical++;
        }
        else if(r >= 1 && r < 26) m = new MetalicMaterial();
        else m = new BasicMaterial();
        return m;
    }

    @Override
    public boolean addTileAt(Tile t, int x, int y) {
        t.preAdditionToGrid(this, x, y);

        if(!super.addTileAt(t, x, y)) return false; // Add to the grid

        t.postAdditionToGrid(this, x, y);
        return true;
    }

    @Override
    public Tile removeTileAt(int x, int y) {
        Tile tile = getTileAt(x, y);
        if(tile != null) {
            tile.preRemovalFromGrid(this);

            for(Actor a : getChildren()) {
                if(a instanceof Tile) {
                    if(((Tile) a).getGridX() == x && ((Tile) a).getGridY() == y) {
                        removeActor(a);
                    }
                }
            }
            super.removeTileAt(x, y);

            tile.postRemovalFromGrid(this);
        }
        return tile;
    }

    // TODO: Removes tiles unsafely, what if those tiles have important stuff??
    public boolean addMultiTile(MultiTile multiTile, int x, int y) {
        multitiles.add(multiTile);
        multiTile.setPosition(xSnap(x), ySnap(y));

        for(int i = 0; i < multiTile.getGridWidth(); i++) {
            for(int j = 0; j < multiTile.getGridHeight(); j++) {
                if(removeTileAt(multiTile.getGridX() + i, multiTile.getGridY() + j) instanceof RadicalMaterial)
                    radical--;

            }
        }
        return true;
    }

    public boolean moveMultitile(MultiTile multitile, GridDirection dir) {
        if(!canMoveMultitile(multitile, dir)) return false;

        switch(dir) {
            case RIGHT:
                for(int x = multitile.getGridX() + multitile.getGridWidth() - 1; x >= multitile.getGridX(); x--) {
                    for(int y = multitile.getGridY(); y < multitile.getGridY() + multitile.getGridHeight(); y++) {
                        if(getTileAt(x, y) != null) {
                            Tile tile = getTileAt(x, y);
                            moveTile(tile, x + 1, y);
                        }
                    }
                }
                multitile.setGridPos(multitile.getGridX() + 1, multitile.getGridY());
                break;
            case UP:
                for(int y = multitile.getGridY() + multitile.getGridHeight() - 1; y >= multitile.getGridY(); y--) {
                    for(int x = multitile.getGridX(); x < multitile.getGridX() + multitile.getGridWidth(); x++) {
                        if(getTileAt(x, y) != null) {
                            Tile tile = getTileAt(x, y);
                            moveTile(tile, x, y + 1);
                        }
                    }
                }
                multitile.setGridPos(multitile.getGridX(), multitile.getGridY() + 1);
                break;
            case LEFT:
                for(int x = multitile.getGridX(); x < multitile.getGridX() + multitile.getGridWidth(); x++) {
                    for(int y = multitile.getGridY(); y < multitile.getGridY() + multitile.getGridHeight(); y++) {
                        if(getTileAt(x, y) != null) {
                            Tile tile = getTileAt(x, y);
                            moveTile(tile, x - 1, y);
                        }
                    }
                }
                multitile.setGridPos(multitile.getGridX() - 1, multitile.getGridY());
                break;
            case DOWN:
                for(int y = multitile.getGridY(); y < multitile.getGridY() + multitile.getGridHeight(); y++) {
                    for(int x = multitile.getGridX(); x < multitile.getGridX() + multitile.getGridWidth(); x++) {
                        if(getTileAt(x, y) != null) {
                            Tile tile = getTileAt(x, y);
                            moveTile(tile, x, y - 1);
                        }
                    }
                }
                multitile.setGridPos(multitile.getGridX(), multitile.getGridY() - 1);
                break;
            default:
                break;
        }
        multitile.setPosition(xSnap(multitile.getGridX()), ySnap(multitile.getGridY()));
        return true;
    }

    public boolean canMoveMultitile(MultiTile multitile, GridDirection dir) {
        switch(dir) {
            case RIGHT:
                if(multitile.getGridX() + multitile.getGridWidth() == this.cols) return false; // Edge of grid
                for(int i = 0; i < multitile.getGridHeight(); i++) {
                    if(getTileAt(multitile.getGridX() + multitile.getGridWidth(), multitile.getGridY() + i) != null) return false;
                }
                break;
            case UP:
                if(multitile.getGridY() + multitile.getGridHeight() == this.rows) return false; // Edge of grid
                for(int i = 0; i < multitile.getGridHeight(); i++) {
                    if(getTileAt(multitile.getGridX() + i, multitile.getGridY() + multitile.getGridHeight()) != null) return false;
                }
                break;
            case LEFT:
                if(multitile.getGridX() == 0) return false; // Edge of grid
                for(int i = 0; i < multitile.getGridHeight(); i++) {
                    if(getTileAt(multitile.getGridX() - 1, multitile.getGridY() + i) != null) return false;
                }
                break;
            case DOWN:
                if(multitile.getGridY() == 0) return false; // Edge of grid
                for(int i = 0; i < multitile.getGridHeight(); i++) {
                    if(getTileAt(multitile.getGridX() + i, multitile.getGridY() - 1) != null) return false;
                }
                break;
            default:
                break;
        }
        return true;
    }

    public boolean isEmpty() {
        boolean empty = true;
        for(int x = 0; x < cols; x++) {
            for(int y = 0; y < rows; y++) {
                Tile t = getTileAt(x, y);
                if(t != null && !(t instanceof Unit)) {
                    empty = false;
                }
            }
        }
        return empty;
    }

    public void clearMaterials() {
        for(int x = 0; x < cols; x++) {
            for(int y = 0; y < rows; y++) {
                Tile t = getTileAt(x, y);
                if(t != null && t instanceof Material) {
                    removeTileAt(x, y);
                }
            }
        }
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        gridBackground.setSize(width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        applyTransform(batch, computeTransform());
        gridBackground.draw(batch, parentAlpha);
        for(MultiTile m : multitiles) {
            m.mtDraw(batch, parentAlpha);
        }
        resetTransform(batch);
        super.draw(batch, parentAlpha);
    }

}

