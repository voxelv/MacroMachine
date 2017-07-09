package com.derelictech.macromachine.tiles.units;

import com.derelictech.macromachine.e_net.EStorageUnit;

/**
 * Created by Tim on 5/7/2016.
 */
public class EBattery extends EStorageUnit {

    /**
     * Constructor for AbstractEUnit
     *
     * @param cell the cell this is added to
     */
    public EBattery(Cell cell) {
        super("units/e_battery", cell);
        energyStorageCapacity = 100;
    }

    @Override
    public String TAG() {
        return "BAT";
    }
}
