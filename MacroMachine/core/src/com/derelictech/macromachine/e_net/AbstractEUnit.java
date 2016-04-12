package com.derelictech.macromachine.e_net;

import com.badlogic.gdx.utils.Array;
import com.derelictech.macromachine.units.Unit;
import com.derelictech.macromachine.units.Wire;
import com.derelictech.macromachine.util.GridDirection;

/**
 * Created by Tim on 4/5/2016.
 */
public abstract class AbstractEUnit extends Unit implements EUnit{

    protected ENetwork rNet, uNet, lNet, dNet;
    protected Array<ENetwork> networks = new Array<ENetwork>(true, 4);

    public AbstractEUnit(String unit_name) {
        super(unit_name);
        ENetwork selfNet = new ENetwork();
        rNet = selfNet;
        uNet = selfNet;
        lNet = selfNet;
        dNet = selfNet;
        networks.add(rNet);
        networks.add(uNet);
        networks.add(lNet);
        networks.add(dNet);
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
        GridDirection thisSide = fromSide.invert();      // This side interaction is opposite the fromSide
        if(net != null && getNetwork(thisSide) != net) { // If not already in net
            if(thisSide != null) {
                switch (thisSide) { // Set the side network
                    case RIGHT:
                        rNet = net;
                        networks.set(0, net);
                        break;
                    case UP:
                        uNet = net;
                        networks.set(1, net);
                        break;
                    case LEFT:
                        lNet = net;
                        networks.set(2, net);
                        break;
                    case DOWN:
                        dNet = net;
                        networks.set(3, net);
                        break;
                }
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
