package com.derelictech.macromachine.units;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.derelictech.macromachine.items.Item;
import com.derelictech.macromachine.util.Assets;

/**
 * Created by Tim on 4/5/2016.
 */
public class Unit extends Group implements Item {
    protected Sprite sprite;

    public Unit(String unit_name) {
        sprite = new Sprite(Assets.inst.getRegion(unit_name));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }
}
