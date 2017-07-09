package com.derelictech.macromachine.tiles.materials;

import com.derelictech.macromachine.fx.RadicalExplosion;
import com.derelictech.macromachine.tiles.Material;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.TileGrid;

/**
 * Created by Tim on 4/19/2016.
 */
public class RadicalMaterial extends Material {
    private long damageAmount = 100;
    public RadicalMaterial() {
        super("materials/basic");
        rotate90();
        rotate90();
    }

    @Override
    public String TAG() {
        return "RMT";
    }

    public long getDamageAmount() {
        return damageAmount;
    }

    @Override
    public void preRemovalFromGrid(TileGrid grid) {
        super.preRemovalFromGrid(grid);
        grid.addActor((new RadicalExplosion()).explodeAt(getX() + 0.5f, getY() + 0.5f));
        Assets.inst.radical_explosion.play();
    }
}
