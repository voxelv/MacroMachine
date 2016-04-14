package com.derelictech.macromachine.tiles.units;

import com.derelictech.macromachine.util.CellGrid;

/**
 * Creates Units for the game
 * TODO: Currently unused
 * @author Tim Slippy, voxelv
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
