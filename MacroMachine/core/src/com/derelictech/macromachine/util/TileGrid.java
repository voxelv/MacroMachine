package com.derelictech.macromachine.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.derelictech.macromachine.tiles.Tile;
import com.derelictech.macromachine.tiles.units.MultiTile;

/**
 * Created by Tim on 4/14/2016.
 */
public class TileGrid extends SlotGrid {

    private Sprite gridBackground;
    private Array<MultiTile> multitiles;


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
                Tile t = new Tile();
                addTileAt(t, i, j);
                addActor(t);
            }
        }
    }

    @Override
    public boolean addTileAt(Tile t, int x, int y) {
        t.preAdditionToGrid(this, x, y);

        if(!super.addTileAt(t, x, y)) return false; // Add to the grid
        this.addActor(t);                           // Add to children

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
                removeTileAt(multiTile.getGridX() + i, multiTile.getGridY() + j);
            }
        }
        return true;
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

