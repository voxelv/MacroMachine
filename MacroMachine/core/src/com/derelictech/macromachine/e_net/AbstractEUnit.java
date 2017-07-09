package com.derelictech.macromachine.e_net;

import com.derelictech.macromachine.tiles.Tile;
import com.derelictech.macromachine.tiles.units.Cell;
import com.derelictech.macromachine.tiles.units.Unit;
import com.derelictech.macromachine.tiles.units.Wire;
import com.derelictech.macromachine.util.GridDirection;
import com.derelictech.macromachine.util.TileGrid;

/**
 * Contains network information for an EUnit, Sets connections for wires next to this EUnit.
 *
 * @author Tim Slippy, voxelv
 */
public abstract class AbstractEUnit extends Unit implements EUnit{

    protected ENetwork eNet;
    private boolean disabled;

    /**
     * Constructor for AbstractEUnit
     * @param unit_name Name of texture file to apply to the unit
     */
    public AbstractEUnit(String unit_name, Cell cell) {
        super(unit_name, cell);
        eNet = new ENetwork();

        eNet.add(this);
    }

    /**
     * Sets the connections of wires adjacent to this EUnit
     */
    public void setConnections() {
        Tile unit;
        if(this instanceof Wire) ((Wire) this).resetFrame();
        for(GridDirection dir : GridDirection.values()) {

            unit = getNeighbor(dir);

            if(unit instanceof AbstractEUnit && ((Unit) unit).isInSameCell(this.getCell())) {
                setNetwork(((AbstractEUnit) unit).getNetwork().merge(this.eNet));
                if(this instanceof Wire) ((Wire) this).addConnection(dir);
            }

            if(unit instanceof Wire && ((Unit) unit).isInSameCell(this.getCell())) {
                ((Wire) unit).addConnection(dir.invert());
            }
        }
    }

    /**
     * Removes the connections of wires adjacent to this EUnit
     */
    public void unsetConnections() {
        eNet.split(this);

        if(this instanceof Wire) ((Wire) this).resetFrame();

        Tile unit;
        for(GridDirection dir : GridDirection.values()) {

            unit = getNeighbor(dir);

            if(unit instanceof AbstractEUnit && ((Unit) unit).isInSameCell(this.getCell())) {
                if(this instanceof Wire) ((Wire) this).addConnection(dir);
            }

            if(unit instanceof Wire && ((Unit) unit).isInSameCell(this.getCell())) {
                ((Wire) unit).remConnection(dir.invert());
            }
        }
    }

    @Override
    public ENetwork getNetwork() {
        return eNet;
    }

    @Override
    public void postAdditionToGrid(TileGrid grid, int x, int y) {
        super.postAdditionToGrid(grid, x, y);
        setConnections();
    }

    @Override
    public void preRemovalFromGrid(TileGrid grid) {
        super.preRemovalFromGrid(grid);
        this.disable();
        unsetConnections();
    }

    private void disable() {
        this.disabled = true;
    }

    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public void postRemovalFromGrid(TileGrid grid) {
        super.postRemovalFromGrid(grid);
    }

    @Override
    public void reCalcNet(ENetwork net) {
        if(net != null && eNet != net) { // If not already in net
            this.eNet = net;
            net.add(this); // Add this to the network

            for (GridDirection dir : GridDirection.values()) {          // Loop through all neighbors
                Tile unit = getNeighbor(dir);                                   // Get neighbor
                if (unit == null) continue;                                // Nothing's there, continue
                if (unit instanceof AbstractEUnit && !((AbstractEUnit) unit).isDisabled()) {
                    ((AbstractEUnit) unit).reCalcNet(net);      // If a neighbor is an AbstractEUnit, set its network
                }
            }
        } // End if not already in net
    }

    @Override
    public void setNetwork(ENetwork eNetwork) {
        this.eNet = eNetwork;
    }
}
