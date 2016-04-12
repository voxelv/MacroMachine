package com.derelictech.macromachine.e_net;

import com.derelictech.macromachine.units.Unit;
import com.derelictech.macromachine.util.GridDirection;

/**
 * Created by Tim on 4/5/2016.
 */
public abstract class AbstractEUnit extends Unit implements EUnit{

    protected ENetwork network;

    public AbstractEUnit(String unit_name) {
        super(unit_name);
    }

    @Override
    public ENetwork getNetwork() {
        return network;
    }

    @Override
    public void setNetwork(ENetwork net) {
        this.network = net;
    }

    @Override
    public Unit getNeighbor(GridDirection dir) {
        Unit unit = null;
        switch(dir) {
            case RIGHT:
                if(gridX == cellGrid.getRows() - 1) break;      // Stop at edge of grid
                unit = cellGrid.getUnitAt(gridX + 1, gridY);    // Get the unit
                if(unit == null) break;                         // If nothing is there, break
                break;

            case UP:
                if(gridY == getCellGrid().getCols() - 1) break; // Stop at edge of grid
                unit = cellGrid.getUnitAt(gridX, gridY + 1);    // Get the unit
                if(unit == null) break;                         // If nothing is there, break
                break;

            case LEFT:
                if(gridX == 0) break;                           // Stop at edge of grid
                unit = cellGrid.getUnitAt(gridX - 1, gridY);    // Get the unit
                if(unit == null) break;                         // If nothing is there, break
                break;

            case DOWN:
                if(gridY == 0) break;                           // Stop at edge of grid
                unit = cellGrid.getUnitAt(gridX, gridY - 1);    // Get the unit
                if(unit == null) break;                         // If nothing is there, break
                break;

            default:
                break;
        }
        return unit;
    }
}
