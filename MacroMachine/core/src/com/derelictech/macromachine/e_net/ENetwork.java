package com.derelictech.macromachine.e_net;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Tim on 4/5/2016.
 */
public class ENetwork {

    private Array<EUnit> eUnits;
    private Array<EProducer> eProducers;
    private Array<EConsumer> eConsumers;
    private Array<EStorage> eStorages;

    public ENetwork() {
        eUnits = new Array<EUnit>();
        eProducers = new Array<EProducer>();
        eConsumers = new Array<EConsumer>();
        eStorages = new Array<EStorage>();
    }
}
