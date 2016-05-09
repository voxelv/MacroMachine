package com.derelictech.macromachine.tiles.materials;

import com.derelictech.macromachine.tiles.Material;

/**
 * Created by Tim on 4/19/2016.
 */
public class RadicalMaterial extends Material {
    private long damageAmount = 100;
    public RadicalMaterial() {
        super("materials/radical");
    }

    @Override
    public String TAG() {
        return "RMT";
    }

    public long getDamageAmount() {
        return damageAmount;
    }
}
