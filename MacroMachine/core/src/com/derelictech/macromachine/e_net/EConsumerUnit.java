package com.derelictech.macromachine.e_net;

import com.derelictech.macromachine.tiles.units.Cell;

/**
 * Created by Tim on 5/5/2016.
 */
public class EConsumerUnit extends AbstractEUnit implements EConsumer {

    private long consumeAmount = 5; // Amount of energy needed to perform an operation
    private long consumeBuffer = 0; // Amount of energy contained to apply to operation
    private boolean willConsume;

    /**
     * Constructor for AbstractEUnit
     *
     * @param unit_name Name of texture file to apply to the unit
     * @param cell
     */
    public EConsumerUnit(String unit_name, Cell cell) {
        super(unit_name, cell);
    }

    @Override
    public String TAG() {
        return "ECU";
    }

    @Override
    public boolean willConsume() {
        if(consumeAmount == 0) {
            willConsume = false;
        }
        else if(isFull()) {
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
        return false;
    }
}
