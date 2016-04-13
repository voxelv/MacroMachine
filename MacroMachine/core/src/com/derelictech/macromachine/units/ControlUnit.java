package com.derelictech.macromachine.units;

import com.derelictech.macromachine.e_net.AbstractEUnit;
import com.derelictech.macromachine.e_net.EConsumer;
import com.derelictech.macromachine.e_net.EProducer;
import com.derelictech.macromachine.e_net.EStorage;

/**
 * The Control Unit is the 'brains' of {@link TheCell}
 * @author Tim Slippy, voxelv
 */
public class ControlUnit extends AbstractEUnit implements EConsumer, EProducer, EStorage{
    /**
     * Constructor for Control Unit
     * Sets the Texture of this.
     * The way the size is handled is likely to change if/when Power Levels are implemented
     */
    public ControlUnit() {
        super("units/control_unit");
        setSize(1, 1); // A Control Unit occupies a 1x1 space
    }

    @Override
    public long consume(long amount) {
        return 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public long produce() {
        return 0;
    }

    @Override
    public long store(long amount) {
        return 0;
    }

    @Override
    public long extract(long amount) {
        return 0;
    }

    @Override
    public long getCapacity() {
        return 0;
    }
}
