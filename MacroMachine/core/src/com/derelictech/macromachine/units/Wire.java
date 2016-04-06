package com.derelictech.macromachine.units;

import com.derelictech.macromachine.e_net.AbstractEUnit;
import com.derelictech.macromachine.e_net.EUnit;

/**
 * Created by Tim on 4/5/2016.
 */
public class Wire extends AbstractEUnit {
    public Wire() {
        super("units/wire");
        sprite.setSize(1, 1); // A wire occupies a 1x1 space
    }
}
