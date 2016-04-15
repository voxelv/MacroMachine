package com.derelictech.macromachine.tiles.units;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.derelictech.macromachine.items.Item;
import com.derelictech.macromachine.tiles.Tile;
import com.derelictech.macromachine.util.Assets;

/**
 * Created by Tim on 4/5/2016.
 */
public abstract class Unit extends Tile implements Item {

    public Unit(String unit_name) {
        super();
        sprite.setRegion(Assets.inst.getRegion(unit_name));
    }
}
