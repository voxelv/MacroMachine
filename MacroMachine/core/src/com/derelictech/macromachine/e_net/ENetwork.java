package com.derelictech.macromachine.e_net;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Tim on 4/5/2016.
 */
public class ENetwork implements Disposable{

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

    public ENetwork merge(ENetwork net) {
        // TODO: Add all elements in 'net' to this object
        // TODO: Destroy the old 'net'
        return this;
    }

    public ENetwork split(EUnit unit) {
        // TODO: Figure out how to split a network when an EUnit is removed
        // TODO: probably a traversal to find neighbors and reconstruct
        // TODO: possibly four entirely new ENetworks.
        return this;
    }


    @Override
    public void dispose() {
        // TODO: Figure out how to dispose of this correctly
    }
}
