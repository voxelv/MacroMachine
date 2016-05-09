package com.derelictech.macromachine.tiles.units;

import com.derelictech.macromachine.util.TileGrid;

/**
 * Created by Tim on 5/8/2016.
 */
public class HullUpgrade extends Unit {
    private long additionalHP = 100;
    public HullUpgrade(Cell cell) {
        super("units/hull_upgrade", cell);
    }

    @Override
    public String TAG() {
        return "HUL";
    }

    @Override
    public void postAdditionToGrid(TileGrid grid, int x, int y) {
        super.postAdditionToGrid(grid, x, y);
        cell.upgradeMaxHP(additionalHP);
    }

    @Override
    public void preRemovalFromGrid(TileGrid grid) {
        super.preRemovalFromGrid(grid);
        cell.downgradeMaxHP(additionalHP);
    }
}
