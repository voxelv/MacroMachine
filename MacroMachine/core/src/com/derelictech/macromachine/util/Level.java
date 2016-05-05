package com.derelictech.macromachine.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.derelictech.macromachine.screens.GameScreen;
import com.derelictech.macromachine.tiles.Material;
import com.derelictech.macromachine.tiles.materials.BasicMaterial;
import com.derelictech.macromachine.tiles.materials.MetalicMaterial;
import com.derelictech.macromachine.tiles.units.Cell;
import com.derelictech.macromachine.tiles.units.ControlUnit;
import com.derelictech.macromachine.tiles.units.Unit;
import com.derelictech.macromachine.tiles.units.Wire;

/**
 * Contains all the information for a Power Level
 * TODO: WIP
 */
public class Level extends Group {

    private GameScreen gameScreen;
    private int powerLevel;
    private TileGrid gameGrid;
    private Cell cell;
    private IntMap<Grid<Unit>> lowerCells;

    public Level(GameScreen gameScreen, int power) {
        this.gameScreen = gameScreen;
        this.powerLevel = power;
        this.lowerCells = new IntMap<Grid<Unit>>();
        this.init();
    }

    public void init() {
        gameGrid = new TileGrid(25, 25, 5 / Const.TEXTURE_RESOLUTION, 3 / Const.TEXTURE_RESOLUTION, false, "game_grid_edge5_pad3");
        addActor(gameGrid);

        cell = new Cell(gameGrid, 10, 10, 5, 5);
        addActor(cell);

        this.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.print("LEVEL GOT CLICK TOO. ");
                if (event.getRelatedActor() != null) System.out.println(" ITEM: " + event.getRelatedActor());
                else System.out.println();

                Slot s;
                if (event.getRelatedActor() instanceof Slot) {
                    s = ((Slot) event.getRelatedActor());

                    if (cell.containsUnitAt(s.getGridX(), s.getGridY())) {
                        switch (button) {
                            case Input.Buttons.LEFT:
                                cell.addUnitAt(new ControlUnit(cell), s.getGridX(), s.getGridY());
                                break;
                            case Input.Buttons.MIDDLE:
                                cell.addUnitAt(new Wire(cell), s.getGridX(), s.getGridY());
                                break;
                            case Input.Buttons.RIGHT:
                                cell.removeUnitAt(s.getGridX(), s.getGridY());
                                break;
                            default:
                                break;
                        }
                    }
                    else {
                        Material m;
                        switch(button) {
                            case Input.Buttons.LEFT:
                                m = new BasicMaterial();
                                if(gameGrid.addTileAt(m, s.getGridX(), s.getGridY())) {
                                    gameGrid.addActor(m);
                                }
                                break;
                            case Input.Buttons.MIDDLE:
                                m = new MetalicMaterial();
                                if(gameGrid.addTileAt(m, s.getGridX(), s.getGridY())) {
                                    gameGrid.addActor(m);
                                }
                                break;
                            case Input.Buttons.RIGHT:
                                gameGrid.removeTileAt(s.getGridX(), s.getGridY());
                                break;
                            default:
                                break;
                        }
                    }
                }

                return true;
            }
        });
    }

    public void upLevel() {
        if(!gameGrid.isEmpty()) return;
        if(!cellAtCenter()) return;

        cell.closeCell();
        lowerCells.put(powerLevel, cell.getUnits()); // Save the old cell contents into an IntMap

//        removeActor(gameGrid);
//        gameGrid = new TileGrid(25, 25, 5 / Const.TEXTURE_RESOLUTION, 3 / Const.TEXTURE_RESOLUTION, true, "game_grid_edge5_pad3");
//        addActor(gameGrid);
//
//        cell.addToGrid();

//        removeActor(cell);
//        cell = new Cell(gameGrid, 10, 10, 5, 5);
//        addActor(cell);

        System.gc(); // Collect that garbage

        powerLevel++;

        System.out.println(lowerCells.toString());
    }

    private boolean cellAtCenter() {
        return (cell.getGridX() == 10 && cell.getGridY() == 10);
    }

    public boolean moveCell(GridDirection dir) {
        return cell.move(dir);
    }
}
