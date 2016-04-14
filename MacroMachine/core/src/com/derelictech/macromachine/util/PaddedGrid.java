package com.derelictech.macromachine.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Tim on 4/12/2016.
 */
public class PaddedGrid<T> extends Grid<T> {

    private Sprite sprite;
    protected float edgeWidth;
    protected final float padding;

    /**
     * Constructor for PaddedGrid
     * @param cols The number of columns this PaddedGrid will have
     * @param rows The number of rows this PaddedGrid will have
     * @param padding The padding in world-length between elements in the PaddedGrid
     * @param edgeWidth The edge width this PaddedGrid will have
     */
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
    public void draw(Batch batch, float parentAlpha) {
        applyTransform(batch, computeTransform());
        sprite.draw(batch, parentAlpha);
        resetTransform(batch);
        super.draw(batch, parentAlpha);
    }
}
