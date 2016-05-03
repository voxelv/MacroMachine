package com.derelictech.macromachine.util;

import com.badlogic.gdx.math.Vector2;
import com.derelictech.macromachine.tiles.Tile;

/**
 * Created by Tim on 4/15/2016.
 */
public class SlotGrid extends Grid<Slot> {

    private float edgePad;
    private float inPad;
    /**
     * Constructor for SlotGrid
     *
     * @param cols The number of columns this will have
     * @param rows The number of rows this will have
     */
    public SlotGrid(int cols, int rows, float edgePad, float inPad) {
        super(cols, rows);

        this.edgePad = edgePad;
        this.inPad = inPad;

        for(int x = 0; x < cols; x++) {
            for(int y = 0; y < rows; y++){
                Slot s = new Slot(this, x, y, 1, 1);
                s.setPosition(xSnap(x), ySnap(y));
                addItemAt(s, x, y);
                addActor(s);
            }
        }
    }

    public boolean addTileAt(Tile tile, int x, int y) {
        Slot s = getItemAt(x, y);
        if(s.getTile() == null) {
            s.setTile(tile);
            s.setPosition(xSnap(x), ySnap(y));  // Set Position
            s.setGridPos(x, y);                 // Set Grid Index
            return true;
        }
        else return false;
    }

    public boolean canAddTileAt(int x, int y) {
        Slot s = getItemAt(x, y);
        return (s.getTile() == null);
    }

    public boolean canRemoveTileAt(int x, int y) {
        return !canAddTileAt(x, y);
    }

    public Tile getTileAt(int x, int y) {
        return getItemAt(x, y).getTile();
    }

    public Tile removeTileAt(int x, int y) {
        return getItemAt(x, y).removeTile();
    }

    public Vector2 posFromCoords(int x, int y) {
        return new Vector2(edgePad + x + x* inPad, edgePad + y + y* inPad);
    }

    public float xSnap(int x) {
        return edgePad + x + x*inPad;
    }
    public float ySnap(int y) {
        return edgePad + y + y*inPad;
    }

    public float getInPad() {
        return inPad;
    }

    public float getEdgePad() {
        return edgePad;
    }
}
