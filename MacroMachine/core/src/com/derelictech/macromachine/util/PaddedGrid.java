package com.derelictech.macromachine.util;

/**
 * Created by Tim on 4/12/2016.
 * @deprecated
 */
public class PaddedGrid<T> extends Grid<T> {
    protected float edgePad;
    protected float inPad;

    /**
     * Constructor for PaddedGrid
     * @param cols The number of columns this will have
     * @param rows The number of rows this will have
     * @param inPad The inPad in world-length between elements in this
     * @param edgePad The edge width this will have
     */
    public PaddedGrid(int cols, int rows, float edgePad, float inPad) {
        super(cols, rows);
        this.edgePad = edgePad;
        this.inPad = inPad;
    }
}
