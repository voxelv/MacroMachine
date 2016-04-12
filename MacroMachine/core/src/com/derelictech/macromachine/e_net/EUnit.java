package com.derelictech.macromachine.e_net;

import com.derelictech.macromachine.units.Unit;
import com.derelictech.macromachine.util.GridDirection;

/**
 * Created by Tim on 4/5/2016.
 */
public interface EUnit {
    ENetwork getNetwork();
    void setNetwork(ENetwork eNetwork);
    Unit getNeighbor(GridDirection dir);
}
