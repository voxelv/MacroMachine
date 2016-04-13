package com.derelictech.macromachine.units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.derelictech.macromachine.e_net.AbstractEUnit;
import com.derelictech.macromachine.e_net.EUnit;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.Grid;
import com.derelictech.macromachine.util.GridDirection;

/**
 * Created by Tim on 4/5/2016.
 */
public class Wire extends AbstractEUnit {

    private Array<TextureRegion> textures;
    private int frame = 0;

    public Wire() {
        super("units/wire");

        setSize(1, 1); // A wire occupies a 1x1 space
        textures = Assets.inst.getWireTextures();
    }

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

    private void addConnection(GridDirection dir) {
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
        sprite.setRegion(textures.get(frame));
    }

    private void remConnection(GridDirection dir) {
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
        sprite.setRegion(textures.get(frame));
    }

    @Override
    public void preAdditionToGrid(Grid grid, int x, int y) {
        this.setGrid(grid);
        this.setGridPos(x, y);
    }

    @Override
    public void postAdditionToGrid(Grid grid, int x, int y) {
        setConnections();
    }

    @Override
    public void preRemovalFromGrid(Grid grid) {
        unsetConnections();
    }

    @Override
    public void postRemovalFromGrid(Grid grid) {

    }
}
