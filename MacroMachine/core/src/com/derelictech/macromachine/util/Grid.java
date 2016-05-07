package com.derelictech.macromachine.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.StringBuilder;
import com.derelictech.macromachine.tiles.Tile;

/**
 * Created by Tim on 4/12/2016.
 */
public class Grid<T extends Actor> extends Group {

    private T[] items;

    protected int cols, rows;

    /**
     * Constructor for Grid
     * @param cols The number of columns this will have
     * @param rows The number of rows this will have
     */
    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;

        items = (T[])new Actor[cols * rows];
    }

    /**
     * Adds an item to this Grid
     * @param newItem The item to addItemAt
     * @param x The x coordinate to addItemAt the item at
     * @param y The y coordinate to addItemAt the item at
     * @return Returns true if the item was added. Otherwise returns false.
     */
    public boolean addItemAt(T newItem, int x, int y) {
        if(x > cols - 1 || x < 0) return false; // Unit not placed
        if(y > rows - 1 || y < 0) return false; // Unit not placed
        if(items[((cols * y) + x)] != null) return false; // Tile Occupied

        items[((cols*y) + x)] =  newItem;

        return true; // Item was placed
    }

    /**
     * Removes an item from this Grid
     * @param x The x coordinate to remove the item from
     * @param y The y coordinate to remove the item from
     * @return Returns the item removed. Returns null if it was already empty.
     */
    public T deleteItemAt(int x, int y) {
        T t = getItemAt(x, y);
        items[((cols * y) + x)] = null;
        removeActor(t);
        return t;
    }

    /**
     * Gets the item at the coordinates
     * @param x The x coordinate to get the item from
     * @param y The   y coordinate to get the item from
     * @return Returns the item. Returns null no item was there.
     */
    public T getItemAt(int x, int y) {
        return items[cols*y + x];
    }

    /**
     * Prints this
     * @return Returns the string to print
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for(int y = rows - 1; y >= 0; y--) {
            for(int x = 0; x < cols; x++) {
                T t = items[(y*cols) + x];
                if(t != null) {
                    if(t instanceof Tile && ((Tile) t).TAG() != null) {
                        sb.append(((Tile) t).TAG());
                    }
                    else if(t instanceof Tile && ((Tile) t).TAG() == null) {
                        sb.append("___");
                    }
                    else {
                        sb.append(t);
                    }
                }
                else sb.append("___");
                sb.append(" ");
            }
            sb.append("\n");
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
