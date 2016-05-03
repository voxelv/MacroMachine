package com.derelictech.macromachine.tiles.materials;

import com.derelictech.macromachine.tiles.Material;

/**
 * Created by Tim on 4/19/2016.
 */
public class BasicMaterial extends Material {
    public BasicMaterial() {
        super("materials/basic");
    }

    @Override
    public String TAG() {
        return "BMT";
    }

}
