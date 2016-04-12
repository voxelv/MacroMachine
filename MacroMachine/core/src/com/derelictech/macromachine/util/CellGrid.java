package com.derelictech.macromachine.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.derelictech.macromachine.units.Unit;

/**
 * Created by Tim on 4/11/2016.
 */
public class CellGrid extends Group {

    private Unit[][] units;
    private int cols, rows;

    public CellGrid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        units = new Unit[cols][rows];
    }

    public Unit getUnitAt(int x, int y) {
        return units[x][y];
    }

    public boolean addUnit(Unit u, int x, int y) {
        if(x > cols - 1) return false; // Unit not placed
        if(y > rows - 1) return false; // Unit not placed
        u.setGridPos(x, y);
        units[u.getGridX()][u.getGridY()] = u;
        this.addActor(u);
        return true; // Unit was placed
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        applyTransform(batch, computeTransform());
        drawChildren(batch, parentAlpha);
        resetTransform(batch);
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
}
