package com.derelictech.macromachine.util;

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

    public void addUnit(Unit u, int x, int y) {
        units[x][y] = u;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
}
