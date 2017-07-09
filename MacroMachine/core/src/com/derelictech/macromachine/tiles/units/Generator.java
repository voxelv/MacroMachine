package com.derelictech.macromachine.tiles.units;

import com.derelictech.macromachine.e_net.EProducerUnit;
import com.derelictech.macromachine.tiles.Tile;
import com.derelictech.macromachine.util.GridDirection;
import com.derelictech.macromachine.util.TileGrid;

/**
 * Created by Tim on 5/7/2016.
 */
public class Generator extends EProducerUnit {

    private long synergyBonus = 1; // Generators nearby boost this, and this boosts them

    /**
     * Constructor for AbstractEUnit
     *
     * @param cell
     */
    public Generator(Cell cell) {
        super("units/generator", cell);
    }

    private void calcProductionAmount() {
        produceAmount = 0;
        Tile tile;
        for(GridDirection dir : GridDirection.values()) {
            tile = getNeighbor(dir);
            if(tile instanceof Generator) {
                ((Generator) tile).increaseProductionAmount(synergyBonus);
                produceAmount += synergyBonus;
            }
        }

    }

    @Override
    public void postAdditionToGrid(TileGrid grid, int x, int y) {
        super.postAdditionToGrid(grid, x, y);
        calcProductionAmount();
    }

    private void increaseProductionAmount(long amount) {
        produceAmount += amount;
    }

    @Override
    public String TAG() {
        return "GEN";
    }
}
