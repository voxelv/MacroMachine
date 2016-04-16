package com.derelictech.macromachine.e_net;

import com.badlogic.gdx.utils.Array;
import com.derelictech.macromachine.tiles.Tile;
import com.derelictech.macromachine.tiles.units.Unit;
import com.derelictech.macromachine.tiles.units.Wire;
import com.derelictech.macromachine.util.Grid;
import com.derelictech.macromachine.util.GridDirection;
import com.derelictech.macromachine.util.TileGrid;

/**
 * Contains network information for an EUnit, Sets connections for wires next to this EUnit.
 *
 * @author Tim Slippy, voxelv
 */
public abstract class AbstractEUnit extends Unit implements EUnit{

    protected ENetwork rNet, uNet, lNet, dNet;
    protected Array<ENetwork> networks = new Array<ENetwork>(true, 4);

    /**
     * Constructor for AbstractEUnit
     * @param unit_name Name of texture file to apply to the unit
     */
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

    /**
     * Sets the connections of wires adjacent to this EUnit
     */
    public void setConnections() {
        Tile unit;
        for(GridDirection dir : GridDirection.values()) {

            unit = getNeighbor(dir);

            if(unit instanceof Wire) {
                ((Wire) unit).addConnection(dir.invert());
            }
        }
    }

    /**
     * Removes the connections of wires adjacent to this EUnit
     */
    public void unsetConnections() {
        Tile unit;
        for(GridDirection dir : GridDirection.values()) {

            unit = getNeighbor(dir);

            if(unit instanceof Wire) {
                ((Wire) unit).remConnection(dir.invert());
            }
        }
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
    public void postAdditionToGrid(TileGrid grid, int x, int y) {
        setConnections();
    }

    @Override
    public void preRemovalFromGrid(TileGrid grid) {
        unsetConnections();
    }

    @Override
    public void setNetwork(ENetwork net, GridDirection fromSide) {
        Tile unit;
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
                    unit = getNeighbor(dir);                                   // Get neighbor
                    if (unit == null) continue;                                // Nothing's there, continue
                    if (unit instanceof Wire) ((Wire) unit).setNetwork(net, dir); // If a neighbor is a Wire, set its network too
                    else if (unit instanceof AbstractEUnit)
                        ((AbstractEUnit) unit).setNetwork(net, dir);           // If a neighbor is an AbstractEUnit, set its network
                }
            } // End if Wire
        } // End if not already in net
    }

}
