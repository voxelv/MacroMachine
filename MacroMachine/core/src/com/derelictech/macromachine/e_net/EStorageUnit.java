package com.derelictech.macromachine.e_net;

import com.derelictech.macromachine.tiles.units.Cell;

/**
 * Created by Tim on 5/7/2016.
 */
public class EStorageUnit extends AbstractEUnit implements EStorage {

    protected long energyStored = 0;
    protected long energyStorageCapacity = 1;

    /**
     * Constructor for AbstractEUnit
     *
     * @param unit_name Name of texture file to apply to the unit
     * @param cell
     */
    public EStorageUnit(String unit_name, Cell cell) {
        super(unit_name, cell);
    }

    @Override
    public boolean isFull() {
        return energyStored >= energyStorageCapacity;
    }

    @Override
    public long amountStored() {
        return energyStored;
    }

    @Override
    public long store(long amount) {
        long diff = 0;

        if(amount > energyStorageCapacity - energyStored) {
            diff = energyStored + amount - energyStorageCapacity; // Skim the top
            energyStored = energyStorageCapacity; // Set the amount stored
        }
        else {
            energyStored += amount;
            diff = 0;
        }

        return diff;
    }

    @Override
    public long extract(long amount) {
        long result = 0;
        if(amount > energyStored) {
            result = energyStored;
            energyStored = 0;
        }
        else {
            result = amount;
            energyStored -= amount;
        }

        return result;
    }

    @Override
    public long getCapacity() {
        return energyStorageCapacity;
    }

    @Override
    public String TAG() {
        return "ESU";
    }
}
