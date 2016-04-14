package com.derelictech.macromachine.util;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.derelictech.macromachine.tiles.units.TheCell;

/**
 * Contains all the information for a Power Level
 * TODO: WIP
 */
public class Level extends Group{

    private int powerLevel;
    private TileGrid gameGrid;
    private TheCell cell;

    public Level(int power) {
        this.powerLevel = power;
        this.init();
    }

    private void init() {
        gameGrid = new TileGrid(25, 25, true);
        addActor(gameGrid);

        cell = new TheCell();
        addActor(cell);
    }
}
