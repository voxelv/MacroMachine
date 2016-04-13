package com.derelictech.macromachine.units;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.CellGrid;
import com.derelictech.macromachine.util.Const;

/**
 * Created by Tim on 4/12/2016.
 */
public class TheCell extends Group {
    private CellGrid cellGrid;

    public TheCell() {
        cellGrid = new CellGrid(5, 5, (1/Const.TEXTURE_RESOLUTION));
        this.setSize(cellGrid.getWidth(), cellGrid.getHeight());
        this.addActor(cellGrid);

        for(int i = 0; i < cellGrid.getCols(); i++) {
            for(int j = 0; j < cellGrid.getRows(); j++) {
                UnitFactory.inst.createWireUnit(cellGrid, i, j);
            }
        }

        System.out.println(cellGrid.toString());
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
