package com.derelictech.macromachine.tiles.units;

/**
 * Created by Tim on 5/8/2016.
 */
public class ProximityDetector extends Unit {
    public ProximityDetector( Cell cell) {
        super("units/proximity_detector", cell);
    }

    @Override
    public String TAG() {
        return "PRX";
    }
}
