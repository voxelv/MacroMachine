package com.derelictech.macromachine.units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.derelictech.macromachine.e_net.AbstractEUnit;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.GridDirection;

/**
 * Created by Tim on 4/5/2016.
 */
public class Wire extends AbstractEUnit {

    private Array<TextureRegion> textures;

    public Wire() {
        super("units/wire");
        setSize(1, 1); // A wire occupies a 1x1 space
        textures = Assets.inst.getWireTextures();
        sprite.setRegion(textures.get(15));
    }

    public void updateFrame(GridDirection dir) {
    }
}
