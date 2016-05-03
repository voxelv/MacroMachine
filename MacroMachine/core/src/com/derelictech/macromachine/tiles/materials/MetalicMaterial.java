package com.derelictech.macromachine.tiles.materials;

import com.derelictech.macromachine.tiles.Material;

/**
 * Created by Tim on 4/19/2016.
 */
public class MetalicMaterial extends Material {
    public MetalicMaterial() {
        super("materials/metalic");
    }

    @Override
    public String TAG() {
        return "MMT";
    }
}
