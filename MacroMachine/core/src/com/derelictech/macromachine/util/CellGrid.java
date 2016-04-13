package com.derelictech.macromachine.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.derelictech.macromachine.units.Unit;

/**
 * Created by Tim on 4/11/2016.
 */
public class CellGrid extends Grid<Unit> {

    private Sprite sprite;
    private float cellWallWidth;
    private final float padding;

    public CellGrid(int cols, int rows) {
        this(cols, rows, 0);
    }

    public CellGrid(int cols, int rows, float padding) {
        super(cols, rows);
        sprite = new Sprite(Assets.inst.getRegion("grid_layout"));
        this.padding = padding;
        cellWallWidth = this.padding*2;
        this.setSize((2 * cellWallWidth) + (cols - 1)*padding + (cols),
                (2 * cellWallWidth) + (rows - 1)*padding + (rows));
    }

    public Unit getUnitAt(int x, int y) {
        return getItemAt(x, y);
    }

    public Unit removeUnitAt(int x, int y) {
        return deleteItemAt(x, y);
    }

    @Override
    public boolean add(Unit u, int x, int y) {
        u.setGridPos(x, y);
        u.setPosition(cellWallWidth + x + x*padding, cellWallWidth + y + y*padding);
        boolean b = super.add(u, x, y);
        this.addActor(u);
        return b;
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        sprite.setSize(width, height);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.sprite.draw(batch, parentAlpha);
        applyTransform(batch, computeTransform());
        drawChildren(batch, parentAlpha);
        resetTransform(batch);
    }
}
