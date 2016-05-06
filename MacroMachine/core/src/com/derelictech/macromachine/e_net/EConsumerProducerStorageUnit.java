package com.derelictech.macromachine.e_net;

import com.derelictech.macromachine.tiles.units.Cell;
import com.derelictech.macromachine.tiles.units.Unit;

/**
 * Abstract class for extention by a {@link Unit}
 */
public class EConsumerProducerStorageUnit extends AbstractEUnit implements EConsumer, EProducer, EStorage {
    private long consumeAmount = 3;
    private long consumeBuffer = 0;
    private long produceAmount = 2;

    private long energyStored = 5;
    private long energyStorageCapacity = 50;
    private boolean willConsume;

    /**
     * Constructor for AbstractEUnit
     *
     * @param unit_name Name of texture file to apply to the unit
     * @param cell
     */
    public EConsumerProducerStorageUnit(String unit_name, Cell cell) {
        super(unit_name, cell);
    }
    public EConsumerProducerStorageUnit(String unit_name, Cell cell, long consumeAmount, long consumeBuffer, long produceAmount, long energyStored, long energyStorageCapacity) {
        super(unit_name, cell);
        setStats(consumeAmount, consumeBuffer, produceAmount, energyStored, energyStorageCapacity);
    }

    @Override
    public String TAG() {
        return "CPS";
    }

    public void setStats(long consumeAmount, long consumeBuffer, long produceAmount, long energyStored, long energyStorageCapacity) {
        this.consumeAmount = consumeAmount;
        this.consumeAmount = consumeBuffer;
        this.produceAmount = produceAmount;

        this.energyStored = energyStored;
        this.energyStorageCapacity = energyStorageCapacity;
    }

    @Override
    public boolean willConsume() {
        if(consumeAmount == 0) {
            willConsume = false;
        }
        else if(consumeBuffer >= consumeAmount) {
            willConsume = false;
        }
        else {
            willConsume = true;
        }

        return willConsume;
    }

    @Override
    public long consume(long amount) {
        long diff = 0;

        if(amount > consumeAmount) {
            diff = amount - consumeAmount; // Skim the top
            consumeBuffer = consumeAmount;
        }
        else {
            consumeBuffer += amount;
            diff = 0;
        }

        return diff;
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
    public long produce() {
        return produceAmount;
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
}
