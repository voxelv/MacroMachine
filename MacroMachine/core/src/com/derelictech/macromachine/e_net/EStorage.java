package com.derelictech.macromachine.e_net;

/**
 * Interface for an Energy Storage
 * @author Tim Slippy, voxelv
 */
public interface EStorage extends EUnit {
    /**
     * Store the energy
     * @param amount Amount of energy to store
     * @return Returns the amount not stored due to being full. Returns 0 (zero) if all of it was stored
     */
    long store(long amount);

    /**
     * Extract the energy
     * @param amount Amount of energy to extract
     * @return Returns the amount of energy able to be extracted. Returns 0 (zero) if it was empty
     */
    long extract(long amount);

    /**
     * Get the max capacity of the storage
     * @return Returns the max capacity of this EStorage
     */
    long getCapacity();
}
