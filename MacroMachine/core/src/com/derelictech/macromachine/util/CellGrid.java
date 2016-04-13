package com.derelictech.macromachine.util;
import com.derelictech.macromachine.units.Unit;

/**
 * Created by Tim on 4/11/2016.
 */
public class CellGrid extends PaddedGrid<Unit> {

    public CellGrid(int cols, int rows, float padding, float edgeWidth) {
        super(cols, rows, padding, edgeWidth, "grid_layout");
    }

    public Unit getUnitAt(int x, int y) {
        return getItemAt(x, y);
    }

    public boolean addUnitAt(Unit u, int x, int y) {
        u.preAdditionToGrid(this, x, y);

        u.setPosition(edgeWidth + x + x*padding, edgeWidth + y + y*padding);    // Set Position
        boolean b = super.add(u, x, y);                                         // Add to the grid
        this.addActor(u);                                                       // Add to children

        u.postAdditionToGrid(this, x, y);
        return b;
    }

    public Unit removeUnitAt(int x, int y) {
        getUnitAt(x, y).preRemovalFromGrid(this);

        Unit unit = deleteItemAt(x, y); // Delete from grid
        this.removeActor(unit);         // Remove from children

        unit.postRemovalFromGrid(this);
        return unit;
    }
}
