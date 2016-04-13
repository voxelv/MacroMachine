package com.derelictech.macromachine.e_net;

import com.derelictech.macromachine.util.GridDirection;

/**
 * A unit that can interact with an ENetwork
 * @author Tim Slippy, voxelv
 */
public interface EUnit {
    /**
     * Returns the network of this EUnit based on the {@link GridDirection} the request comes from
     * @param side Specifies which side's network is returned
     * @return The network of the side.
     */
    ENetwork getNetwork(GridDirection side);

    /**
     * Sets the network of an EUnit
     * @param eNetwork The network to set
     * @param fromSide The side the request is coming from
     */
    void setNetwork(ENetwork eNetwork, GridDirection fromSide);
}
