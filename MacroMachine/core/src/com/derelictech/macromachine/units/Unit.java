package com.derelictech.macromachine.units;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.derelictech.macromachine.items.Item;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.CellGrid;

/**
 * Created by Tim on 4/5/2016.
 */
public abstract class Unit extends Group implements Item {
    protected Sprite sprite;
    protected CellGrid cellGrid;
    protected int gridX, gridY;

    public Unit(String unit_name) {
        sprite = new Sprite(Assets.inst.getRegion(unit_name));
    }

    public CellGrid getCellGrid() {
        return cellGrid;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        sprite.setSize(width, height);
    }
}
