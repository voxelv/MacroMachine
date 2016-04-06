package com.derelictech.macromachine.e_net;

import com.derelictech.macromachine.units.Unit;

/**
 * Created by Tim on 4/5/2016.
 */
public abstract class AbstractEUnit extends Unit implements EUnit{

    protected ENetwork network;

    public AbstractEUnit(String unit_name) {
        super(unit_name);
    }

    @Override
    public ENetwork getNetwork() {
        return network;
    }

    @Override
    public void setNetwork(ENetwork net) {
        this.network = net;
    }
}
