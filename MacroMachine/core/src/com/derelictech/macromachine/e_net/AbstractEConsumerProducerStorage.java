package com.derelictech.macromachine.e_net;

import com.derelictech.macromachine.tiles.units.Cell;
import com.derelictech.macromachine.tiles.units.Unit;

/**
 * Abstract class for extention by a {@link Unit}
 */
public class AbstractEConsumerProducerStorage extends AbstractEUnit implements EConsumer, EProducer, EStorage {
    private long consumeAmount = 0;
    private long produceAmount = 0;

    private long energyStored = 0;
    private long energyStorageCapacity = 0;
    /**
     * Constructor for AbstractEUnit
     *
     * @param unit_name Name of texture file to apply to the unit
     * @param cell
     */
    public AbstractEConsumerProducerStorage(String unit_name, Cell cell) {
        super(unit_name, cell);
    }

    @Override
    public String TAG() {
        return "CPS";
    }

    public void setStats(long consumeAmount, long produceAmount, long energyStored, long energyStorageCapacity) {
        this.consumeAmount = consumeAmount;
        this.produceAmount = produceAmount;

        this.energyStored = energyStored;
        this.energyStorageCapacity = energyStorageCapacity;
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
