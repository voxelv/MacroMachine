package com.derelictech.macromachine.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Tim on 4/12/2016.
 */
public class PaddedGrid<T> extends Grid<T> {

    private Sprite sprite;
    protected float edgePad;
    protected float padding;

    /**
     * Constructor for PaddedGrid
     * @param cols The number of columns this PaddedGrid will have
     * @param rows The number of rows this PaddedGrid will have
     * @param padding The padding in world-length between elements in the PaddedGrid
     * @param edgePad The edge width this PaddedGrid will have
     */
    public PaddedGrid(int cols, int rows, float padding, float edgePad, String gridFileName) {
        super(cols, rows);
        sprite = new Sprite(Assets.inst.getRegion(gridFileName));
        this.padding = padding;
        this.edgePad = edgePad;
        this.setSize((2 * edgePad) + (cols - 1)*padding + (cols),
                (2 * edgePad) + (rows - 1)*padding + (rows));
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
