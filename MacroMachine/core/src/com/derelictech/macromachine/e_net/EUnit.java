package com.derelictech.macromachine.e_net;

/**
 * A unit that can interact with an ENetwork
 * @author Tim Slippy, voxelv
 */
public interface EUnit {

    /**
     * Traverse Net at this EUnit and merge all
     * @param eNetwork The network to set all merged EUnits to
     */
    void reCalcNet(ENetwork eNetwork);

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
