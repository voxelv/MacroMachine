package com.derelictech.macromachine.util;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.derelictech.macromachine.screens.GameScreen;
import com.derelictech.macromachine.tiles.units.Cell;

/**
 * Contains all the information for a Power Level
 * TODO: WIP
 */
public class Level extends Group{

    private GameScreen gameScreen;
    private int powerLevel;
    private TileGrid gameGrid;
    private Cell cell;

    public Level(GameScreen gameScreen, int power) {
        this.gameScreen = gameScreen;
        this.powerLevel = power;
        this.init();
    }

    private void init() {
        gameGrid = new TileGrid(25, 25, 5/Const.TEXTURE_RESOLUTION, 3/Const.TEXTURE_RESOLUTION, true);
        gameScreen.setViewportWorldSize(gameGrid.getWidth(), gameGrid.getHeight());
        addActor(gameGrid);

        cell = new Cell(gameGrid, 5, 5);
        cell.setPosition(10, 10);
        addActor(cell);
    }
}
