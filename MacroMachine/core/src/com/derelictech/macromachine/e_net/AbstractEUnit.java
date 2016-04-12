package com.derelictech.macromachine.e_net;

import com.derelictech.macromachine.units.Unit;
import com.derelictech.macromachine.units.Wire;
import com.derelictech.macromachine.util.GridDirection;

/**
 * Created by Tim on 4/5/2016.
 */
public abstract class AbstractEUnit extends Unit implements EUnit{

    protected ENetwork rNet, uNet, lNet, dNet;

    public AbstractEUnit(String unit_name) {
        super(unit_name);
    }

    @Override
    public ENetwork getNetwork(GridDirection side) {
        switch(side) {
            case RIGHT:
                return rNet;
            case UP:
                return uNet;
            case LEFT:
                return lNet;
            case DOWN:
                return dNet;
        }
        return null;
    }

    @Override
    public void setNetwork(ENetwork net, GridDirection fromSide) {
        Unit u;
        if(getNetwork(fromSide.inv()) != net) { // If not already in net
            switch(fromSide.inv()) { // Set the side network
                case RIGHT: rNet = net;
                case UP:    uNet = net;
                case LEFT:  lNet = net;
                case DOWN:  dNet = net;
            }
            net.add(this); // Add this to the network

            // If this is a Wire, traverse it
            if (this instanceof Wire) {
                for (GridDirection dir : GridDirection.values()) {          // Loop through all neighbors
                    u = getNeighbor(dir);                                   // Get neighbor
                    if (u == null) continue;                                // Nothing's there, continue
                    if (u instanceof Wire) ((Wire) u).setNetwork(net, dir); // If a neighbor is a Wire, set its network too
                    else if (u instanceof AbstractEUnit)
                        ((AbstractEUnit) u).setNetwork(net, dir);           // If a neighbor is an AbstractEUnit, set its network
                }
            } // End if Wire
        } // End if not already in net
    }

}
