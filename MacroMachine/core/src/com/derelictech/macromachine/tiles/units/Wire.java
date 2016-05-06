package com.derelictech.macromachine.tiles.units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.derelictech.macromachine.e_net.AbstractEUnit;
import com.derelictech.macromachine.e_net.ENetwork;
import com.derelictech.macromachine.e_net.EUnit;
import com.derelictech.macromachine.tiles.Tile;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.GridDirection;

/**
 * A Wire is part of a network ind is the method that EUnits can connect to each other.
 * @author Tim Slippy, voxelv
 */
public class Wire extends AbstractEUnit {

    private Array<TextureRegion> wireTextures;
    private int frame = 0;
    private ENetwork wireNet;

    /**
     * Constructor for Wire
     * Sets the size... see {@link Unit}
     * Gets the Wire Textures from the TextureAtlas in {@link Assets}
     */
    public Wire(Cell cell) {
        super("units/wire", cell);

        setSize(1, 1); // A wire occupies a 1x1 space
        wireTextures = Assets.inst.getWireTextures();
        wireNet = eNet;
    }

    public void resetFrame() {
        frame = 0;
    }

    @Override
    public String TAG() {
        return "WIR";
    }

//    /**
//     * Overrides the super by copying functionality and adds setting this Wire's connections
//     */
//    @Override
//    public void setConnections() {
//        Tile unit;
//        frame = 0;
//        for(GridDirection dir : GridDirection.values()) {
//
//            unit = getNeighbor(dir);
//
//            if(unit instanceof AbstractEUnit && ((Unit) unit).isInSameCell(this.getCell())) {
//                addConnection(dir);
//            }
//
//            if(unit instanceof Wire && ((Unit) unit).isInSameCell(this.getCell())) {
//                ((Wire) unit).addConnection(dir.invert());
//            }
//        }
//    }
//
//    /**
//     * Overrides the super by copying functionality and adds unsetting this Wire's connections
//     */
//    @Override
//    public void unsetConnections() {
//        Tile unit;
//        frame = 0;
//        for(GridDirection dir : GridDirection.values()) {
//
//            unit = getNeighbor(dir);
//
//            if(unit instanceof AbstractEUnit && ((Unit) unit).isInSameCell(this.getCell())) {
//                addConnection(dir);
//            }
//
//            if(unit instanceof Wire && ((Unit) unit).isInSameCell(this.getCell())) {
//                ((Wire) unit).remConnection(dir.invert());
//            }
//        }
//    }

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
