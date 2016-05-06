package com.derelictech.macromachine.e_net;

/**
 * Interface for an Energy Producer
 * @author Tim Slippy, voxelv
 */
public interface EProducer extends EUnit {
    /**
     * Produce energy
     * @return Returns the amount of energy produced
     */
    long produce();

    long getProduceAmount();
}
