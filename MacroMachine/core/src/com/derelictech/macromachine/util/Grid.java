package com.derelictech.macromachine.util;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.StringBuilder;

/**
 * Created by Tim on 4/12/2016.
 */
public class Grid<T> extends Group {

    private T[] items;

    protected int cols, rows;

    /**
     * Constructor for Grid
     * @param cols The number of columns this Grid will have
     * @param rows The number of rows this Grid will have
     */
    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;

        items = (T[])new Object[cols * rows];
    }

    /**
     * Adds an item to this Grid
     * @param newItem The item to add
     * @param x The x coordinate to add the item at
     * @param y The y coordinate to add the item at
     * @return Returns true if the item was added. Otherwise returns false.
     */
    protected boolean add(T newItem, int x, int y) {
        if(x > cols - 1 || x < 0) return false; // Unit not placed
        if(y > rows - 1 || y < 0) return false; // Unit not placed

        items[((cols*y) + x)] =  newItem;
        return true; // Item was placed
    }

    /**
     * Removes an item from this Grid
     * @param x The x coordinate to remove the item from
     * @param y The y coordinate to remove the item from
     * @return Returns the item removed. Returns null if it was already empty.
     */
    protected T deleteItemAt(int x, int y) {
        T t = getItemAt(x, y);
        items[((cols * y) + x)] = null;
        return t;
    }

    /**
     * Gets the item at the coordinates
     * @param x The x coordinate to get the item from
     * @param y The y coordinate to get the item from
     * @return Returns the item. Returns null no item was there.
     */
    public T getItemAt(int x, int y) {
        return items[cols*y + x];
    }

    /**
     * Prints the Grid, upside-down because it's easier...
     * @return Returns the string to print
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < (cols); i++) {
            for(int j = 0; j < rows; j++) {
                sb.append(items[(i*cols) + j]);
                sb.append(", ");
                if(j == rows - 1) sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Gets the number of columns this grid has
     * @return Returns the number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Gets the number of rows this grid has
     * @return Returns the number of rows
     */
    public int getRows() {
        return rows;
    }
}
