package com.derelictech.macromachine.units;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.derelictech.macromachine.util.CellGrid;
import com.derelictech.macromachine.util.Const;

/**
 * Created by Tim on 4/12/2016.
 */
public class TheCell extends Group {
    private CellGrid cellGrid;

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
        cellGrid.removeUnitAt(2, 2);
        cellGrid.addUnitAt(new ControlUnit(), 2, 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        cellGrid.draw(batch, parentAlpha);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        cellGrid.setPosition(x, y);
    }
}
