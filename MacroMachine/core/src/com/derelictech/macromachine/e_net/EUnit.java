package com.derelictech.macromachine.e_net;

import com.derelictech.macromachine.util.GridDirection;

/**
 * A unit that can interact with an ENetwork
 * @author Tim Slippy, voxelv
 */
public interface EUnit {
    /**
     * Returns the network of this EUnit
     * @return The network.
     */
    ENetwork getNetwork();

    /**
     * Sets the network of an EUnit
     * @param eNetwork The network to set
     */
    void setNetwork(ENetwork eNetwork);
}
