package com.derelictech.macromachine.units;

import com.derelictech.macromachine.e_net.AbstractEUnit;
import com.derelictech.macromachine.e_net.EConsumer;
import com.derelictech.macromachine.e_net.EProducer;
import com.derelictech.macromachine.e_net.EStorage;

/**
 * Created by Tim on 4/12/2016.
 */
public class ControlUnit extends AbstractEUnit implements EConsumer, EProducer, EStorage{
    public ControlUnit() {
        super("units/control_unit");
        setSize(1, 1); // A Control Unit occupies a 1x1 space
    }
}
