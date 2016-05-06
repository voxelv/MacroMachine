package com.derelictech.macromachine.e_net;

import com.derelictech.macromachine.tiles.units.Cell;

/**
 * Created by Tim on 5/5/2016.
 */
public class EProducerUnit extends AbstractEUnit implements EProducer {

    private long produceAmount = 0;

    /**
     * Constructor for AbstractEUnit
     *
     * @param unit_name Name of texture file to apply to the unit
     * @param cell
     */
    public EProducerUnit(String unit_name, Cell cell) {
        super(unit_name, cell);
    }

    public long getProduceAmount() {
        return produceAmount;
    }

    public void setProduceAmount(long produceAmount) {
        this.produceAmount = produceAmount;
    }

    @Override
    public String TAG() {
        return "EPU";
    }

    @Override
    public long produce() {
        return produceAmount;
    }
}
