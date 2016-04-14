package com.derelictech.macromachine.units;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.derelictech.macromachine.util.CellGrid;
import com.derelictech.macromachine.util.Const;

/**
 * The main Gamepiece
 * @author Tim Slippy, voxelv
 */
public class TheCell extends Group {
    private CellGrid cellGrid;
    public boolean started  = false;
    public int degrees = 0;

    /**
     * Constructor for TheCell
     * Creates a {@link CellGrid}
     * Sets its size to the size of its {@link CellGrid}
     * Adds the {@link CellGrid} to its children
     *
     * TODO: TESTING FUNCTIONALITY TO BE REMOVED
     * Adds some Wires and the ControlUnit for testing purposes
     */
    public TheCell() {
        cellGrid = new CellGrid(5, 5, (1/Const.TEXTURE_RESOLUTION), (2/Const.TEXTURE_RESOLUTION));
        this.setSize(cellGrid.getWidth(), cellGrid.getHeight());
        this.addActor(cellGrid);

        for(int i = 0; i < cellGrid.getCols(); i++) {
            for(int j = 0; j < cellGrid.getRows(); j++) {
                cellGrid.addUnitAt(new Wire(), i, j);
            }
        }
        cellGrid.removeUnitAt(0, 1);
        cellGrid.removeUnitAt(1, 4);
        cellGrid.removeUnitAt(4, 3);
        cellGrid.removeUnitAt(3, 0);

        System.out.println(cellGrid.toString());

        addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(started)
                    started = false;
                else
                    started = true;
                return true;
            }
        });

        cellGrid.removeUnitAt(2, 2);
        cellGrid.addUnitAt(new ControlUnit(), 2, 2);
    }

    /**
     * Draws this with its children
     * @param batch The batch to draw to
     * @param parentAlpha The alpha level of the parent
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        applyTransform(batch, computeTransform());
        cellGrid.draw(batch, parentAlpha);
        resetTransform(batch);

        if(started)
        {
            cellGrid.setRotation(degrees);
            degrees +=5;
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        cellGrid.setPosition(x, y);
    }
}
