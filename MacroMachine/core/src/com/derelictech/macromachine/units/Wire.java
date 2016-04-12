package com.derelictech.macromachine.units;

import com.derelictech.macromachine.e_net.AbstractEUnit;

/**
 * Created by Tim on 4/5/2016.
 */
public class Wire extends AbstractEUnit {
    public Wire() {
        super("units/wire");
        setSize(1, 1); // A wire occupies a 1x1 space
    }
}
