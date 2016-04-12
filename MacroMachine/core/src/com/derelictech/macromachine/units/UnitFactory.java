package com.derelictech.macromachine.units;

/**
 * Created by Tim on 4/11/2016.
 */
public class UnitFactory {

    public Unit createWireUnit() {
        Unit u = new Wire();
        return u;
    }
}
