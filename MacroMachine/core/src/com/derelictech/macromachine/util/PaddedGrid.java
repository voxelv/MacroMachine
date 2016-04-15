package com.derelictech.macromachine.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Tim on 4/12/2016.
 */
public class PaddedGrid<T> extends Grid<T> {

    private Sprite gridBackground;
    protected float edgePad;
    protected float inPad;

    /**
     * Constructor for PaddedGrid
     * @param cols The number of columns this will have
     * @param rows The number of rows this will have
     * @param inPad The inPad in world-length between elements in this
     * @param edgePad The edge width this will have
     * @param gridBackgroundFileName The filename of the background for this
     */
    public PaddedGrid(int cols, int rows, float edgePad, float inPad, String gridBackgroundFileName) {
        super(cols, rows);
        gridBackground = new Sprite(Assets.inst.getRegion(gridBackgroundFileName));
        this.edgePad = edgePad;
        this.inPad = inPad;
        this.setSize((2 * edgePad) + (cols - 1)* this.inPad + (cols),
                (2 * edgePad) + (rows - 1)* this.inPad + (rows));
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        gridBackground.setSize(width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        applyTransform(batch, computeTransform());
        gridBackground.draw(batch, parentAlpha);
        resetTransform(batch);
        super.draw(batch, parentAlpha);
    }
}
