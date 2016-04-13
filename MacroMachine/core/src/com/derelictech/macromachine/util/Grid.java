package com.derelictech.macromachine.util;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.StringBuilder;

/**
 * Created by Tim on 4/12/2016.
 */
public class Grid<T> extends Group {

    private T[] items;

    protected int cols, rows;

    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;

        items = (T[])new Object[cols * rows];
        for(int i = 0; i < (cols * rows - 1); i++) {
            items[i] = null;
        }
    }

    protected boolean add(T newItem, int x, int y) {
        if(x > cols - 1 || x < 0) return false; // Unit not placed
        if(y > rows - 1 || y < 0) return false; // Unit not placed

        items[((cols*y) + x)] =  newItem;
        return true; // Item was placed
    }

    protected T deleteItemAt(int x, int y) {
        T t = getItemAt(x, y);
        items[((cols * y) + x)] = null;
        return t;
    }

    public T getItemAt(int x, int y) {
        return items[cols*y + x];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < (cols); i++) {
            for(int j = 0; j < rows; j++) {
                sb.append(items[(i*cols) + j] + ", ");
                if(j == rows - 1) sb.append("\n");
            }
        }
        return sb.toString();
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
}
