package com.derelictech.macromachine.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Tim on 4/12/2016.
 */
public class PaddedGrid<T> extends Grid<T> {

    private Sprite sprite;
    protected float edgeWidth;
    protected final float padding;

    public PaddedGrid(int cols, int rows, float padding, float edgeWidth, String gridFileName) {
        super(cols, rows);
        sprite = new Sprite(Assets.inst.getRegion(gridFileName));
        this.padding = padding;
        this.edgeWidth = edgeWidth;
        this.setSize((2 * edgeWidth) + (cols - 1)*padding + (cols),
                (2 * edgeWidth) + (rows - 1)*padding + (rows));
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
