package com.derelictech.macromachine.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.derelictech.macromachine.e_net.AbstractEUnit;
import com.derelictech.macromachine.e_net.EUnit;
import com.derelictech.macromachine.util.Assets;
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

    public void updateConnections() {
        Unit unit;
        frame = 0;
        for(GridDirection dir : GridDirection.values()) {
            System.out.println("Scanning: " + dir.toString());

            unit = getNeighbor(dir);
            if(unit == null) {
                System.out.println("unit: is null");
            }

            if(unit instanceof EUnit) {
                System.out.println("Found EUnit: " + unit.getGridX() + ", " + unit.getGridY());
                addConnection(dir);
            }
        }
    }

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
        sprite.setRegion(textures.get(frame));
    }

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
        sprite.setRegion(textures.get(frame));
    }
}
