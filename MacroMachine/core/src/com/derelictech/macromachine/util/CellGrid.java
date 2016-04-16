package com.derelictech.macromachine.util;
import com.derelictech.macromachine.tiles.units.Cell;
import com.derelictech.macromachine.tiles.units.Unit;

/**
 * A {@link PaddedGrid} specifically designed for {@link Cell}
 * @author Tim Slippy, voxelv
 *
 * @deprecated
 */
public class CellGrid extends PaddedGrid<Unit> {

    /**
     * Constructor for CellGrid
     * Sets the texture to be grid_layout.png
     * See {@link PaddedGrid}
     */
    public CellGrid(int cols, int rows, float padding, float edgeWidth) {
        super(cols, rows, padding, edgeWidth);
    }

    /**
     * Gets the Unit at coordinates x, y
     * @param x The x coordinate in the Grid to get
     * @param y The y coordinate in the Grid to get
     * @return Returns the unit at x, y in the Grid
     * Returns null if not in the grid or nothing is at that location in the Grid
     */
    public Unit getUnitAt(int x, int y) {
        return getItemAt(x, y);
    }

    /**
     * Adds a Unit at the coordinates and calls the Unit's pre- and post- AdditionToGrid methods.
     * @param u The Unit to addItemAt
     * @param x The x coordinate to addItemAt the Unit at
     * @param y The y coordinate to addItemAt the Unit at
     * @return Returns true if the Unit was able to be placed.
     * TODO: Check if the space is occupied. Perhaps implement in superclass
     */
    public boolean addUnitAt(Unit u, int x, int y) {
        u.preAdditionToGrid(this, x, y);

        u.setPosition(edgePad + x + x* inPad, edgePad + y + y* inPad);        // Set Position
        boolean b = super.addItemAt(u, x, y);                                         // Add to the grid
        this.addActor(u);                                                       // Add to children

        u.postAdditionToGrid(this, x, y);
        return b;
    }

    /**
     * Removes a Unit from the coordinates and calls the Unit's pre- and post- RemovalFromGrid methods.
     * @param x The x coordinate to remove the Unit from
     * @param y The y coordinate to remove the Unit from
     * @return Returns the Unit removed.
     * TODO: Check if the space is null. Perhaps implement in superclass
     */
    public Unit removeUnitAt(int x, int y) {
        getUnitAt(x, y).preRemovalFromGrid(this);

        Unit unit = deleteItemAt(x, y); // Delete from grid
        this.removeActor(unit);         // Remove from children

        unit.postRemovalFromGrid(this);
        return unit;
    }
}
