package com.derelictech.macromachine.tiles.units;

import com.badlogic.gdx.utils.StringBuilder;
import com.derelictech.macromachine.e_net.*;

/**
 * The Control Unit is the 'brains' of {@link Cell}
 * @author Tim Slippy, voxelv
 */
public class ControlUnit extends EConsumerProducerStorageUnit {
    /**
     * Constructor for Control Unit
     * Sets the Texture of this.
     * The way the size is handled is likely to change if/when Power Levels are implemented
     */
    public ControlUnit(Cell cell) {
        super("units/control_unit", cell);
    }

    public ControlUnit(Cell cell, long consumeAmount, long consumeBuffer, long produceAmount, long energyStored, long energyStorageCapacity) {
        super("units/control_unit", cell, consumeAmount, consumeBuffer, produceAmount, energyStored, energyStorageCapacity);
    }

    // Do after any constructor
    {
        setSize(1, 1); // A Control Unit occupies a 1x1 space
    }

    @Override
    public String TAG() {
        return "CPU";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TAG() + " ");
        sb.append(this.gridX +" "+ this.gridY);

        return sb.toString();
    }
}
