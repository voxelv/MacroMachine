package com.derelictech.macromachine.e_net;

/**
 * Interface for an Energy Consumer
 * @author Tim Slippy, voxelv
 */
public interface EConsumer extends EUnit {
    /**
     * Consumes energy.
     * @param amount The amount of energy to consume
     * @return Returns the amount not consumed if any. Returns 0 (zero) if all of amount was consumed.
     */
    long consume(long amount);

    /**
     * Tells whether this Consumer has all the energy it needs
     * @return Returns whether this Consumer is full
     */
    boolean isFull();
}
