package com.derelictech.macromachine.tiles.units;

import com.derelictech.macromachine.e_net.EConsumerUnit;

/**
 * Created by Tim on 5/9/2016.
 */
public class MineCollector extends EConsumerUnit {
    /**
     * Constructor for AbstractEUnit
     *
     * @param unit_name Name of texture file to apply to the unit
     * @param cell
     */
    public MineCollector(String unit_name, Cell cell) {
        super(unit_name, cell);
    }

    @Override
    public String TAG() {
        return "MCL";
    }
}
