package com.derelictech.macromachine.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.derelictech.macromachine.units.Unit;

/**
 * Created by Tim on 4/11/2016.
 */
public class CellGrid extends Grid<Unit> {

    public CellGrid(int cols, int rows) {
        super(cols, rows);
    }

    public Unit getUnitAt(int x, int y) {
        return getItemAt(x, y);
    }

    @Override
    public boolean add(Unit u, int x, int y) {
        u.setGridPos(x, y);
        boolean b = super.add(u, x, y);
        this.addActor(u);
        return b;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        applyTransform(batch, computeTransform());
        drawChildren(batch, parentAlpha);
        resetTransform(batch);
    }
}
