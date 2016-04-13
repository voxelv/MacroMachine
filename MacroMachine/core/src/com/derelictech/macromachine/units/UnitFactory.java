package com.derelictech.macromachine.units;

import com.derelictech.macromachine.util.CellGrid;

/**
 * Created by Tim on 4/11/2016.
 */
public class UnitFactory {

    public static UnitFactory inst = new UnitFactory();

    public Unit createWireUnit(CellGrid grid, int x, int y) {
        Wire u = new Wire();
        u.setGrid(grid);
        grid.addUnitAt(u, x, y);
        u.setConnections();
        return u;
    }
}
