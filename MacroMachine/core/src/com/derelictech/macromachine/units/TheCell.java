package com.derelictech.macromachine.units;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.CellGrid;

/**
 * Created by Tim on 4/12/2016.
 */
public class TheCell extends Group {
    private Sprite sprite;
    private CellGrid cellGrid;
    private float cellWallPad = 3;

    public TheCell() {
        sprite = new Sprite(Assets.inst.getRegion("grid_layout"));
        this.setSize(5, 5);
        sprite.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        cellGrid = new CellGrid(5, 5);
        this.addActor(cellGrid);

        UnitFactory.inst.createWireUnit(cellGrid, 1, 1);
        UnitFactory.inst.createWireUnit(cellGrid, 2, 1);
//        UnitFactory.inst.createWireUnit(cellGrid, 3, 1);
//        UnitFactory.inst.createWireUnit(cellGrid, 1, 2);
//        UnitFactory.inst.createWireUnit(cellGrid, 3, 2);
//        UnitFactory.inst.createWireUnit(cellGrid, 1, 3);
//        UnitFactory.inst.createWireUnit(cellGrid, 2, 3);
//        UnitFactory.inst.createWireUnit(cellGrid, 3, 3);
        System.out.println(cellGrid.toString());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);

        applyTransform(batch, computeTransform());
        drawChildren(batch, parentAlpha);
        resetTransform(batch);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x, y);
    }
}
