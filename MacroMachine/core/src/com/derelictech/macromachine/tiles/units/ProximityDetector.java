package com.derelictech.macromachine.tiles.units;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.derelictech.macromachine.util.Assets;

/**
 * Created by Tim on 5/8/2016.
 */
public class ProximityDetector extends Unit {
    private Sprite dish;
    private float dish_speed = 100;
    public ProximityDetector( Cell cell) {
        super("units/proximity_detector", cell);
        dish = new Sprite(Assets.inst.getRegion("units/proximity_dish"));
        dish.setSize(1, 1);
        dish.setOrigin(0.5f, 0.5f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        dish.rotate(-dish_speed * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        applyTransform(batch, computeTransform());
        dish.draw(batch, parentAlpha);
        resetTransform(batch);
    }

    @Override
    public String TAG() {
        return "PRX";
    }
}
