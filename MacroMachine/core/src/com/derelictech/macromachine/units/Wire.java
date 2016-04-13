package com.derelictech.macromachine.units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.derelictech.macromachine.e_net.AbstractEUnit;
import com.derelictech.macromachine.e_net.EUnit;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.GridDirection;

/**
 * A Wire is part of a network ind is the method that EUnits can connect to each other.
 * @author Tim Slippy, voxelv
 */
public class Wire extends AbstractEUnit {

    private Array<TextureRegion> wireTextures;
    private int frame = 0;

    /**
     * Constructor for Wire
     * Sets the size... see {@link Unit}
     * Gets the Wire Textures from the TextureAtlas in {@link Assets}
     */
    public Wire() {
        super("units/wire");

        setSize(1, 1); // A wire occupies a 1x1 space
        wireTextures = Assets.inst.getWireTextures();
    }

    /**
     * Overrides the super by copying functionality and adds setting neighboring wires connections
     */
    @Override
    public void setConnections() {
        Unit unit;
        frame = 0;
        for(GridDirection dir : GridDirection.values()) {

            unit = getNeighbor(dir);

            if(unit instanceof EUnit) {
                addConnection(dir);
            }

            if(unit instanceof Wire) {
                ((Wire) unit).addConnection(dir.invert());
            }
        }
    }

    /**
     * Overrides the super by copying functionality and adds unsetting neighboring wires connections
     */
    @Override
    public void unsetConnections() {
        Unit unit;
        frame = 0;
        for(GridDirection dir : GridDirection.values()) {

            unit = getNeighbor(dir);

            if(unit instanceof EUnit) {
                addConnection(dir);
            }

            if(unit instanceof Wire) {
                ((Wire) unit).remConnection(dir.invert());
            }
        }
    }

    /**
     * Uses some binary trickery to determine which frame to display
     * @param dir which direction to compute for
     */
    public void addConnection(GridDirection dir) {
        switch(dir) {
            case RIGHT:
                frame += 1;
                break;
            case UP:
                frame += 2;
                break;
            case LEFT:
                frame += 4;
                break;
            case DOWN:
                frame += 8;
                break;
            default:
                break;
        }
        sprite.setRegion(wireTextures.get(frame));
    }

    /**
     * Uses some binary trickery to determine which frame to display
     * @param dir which direction to compute for
     */
    public void remConnection(GridDirection dir) {
        switch(dir) {
            case RIGHT:
                frame -= 1;
                break;
            case UP:
                frame -= 2;
                break;
            case LEFT:
                frame -= 4;
                break;
            case DOWN:
                frame -= 8;
                break;
            default:
                break;
        }
        sprite.setRegion(wireTextures.get(frame));
    }
}
