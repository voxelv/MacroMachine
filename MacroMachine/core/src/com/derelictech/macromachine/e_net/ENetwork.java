package com.derelictech.macromachine.e_net;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Manages a network of EUnits, EProducers, EConsumers, and EStorage.
 *
 * DOES NOT manage sides of the units in the network, just worries about itself.
 *
 * @author Tim Slippy, voxelv
 */
public class ENetwork implements Disposable{

    private Array<AbstractEUnit> eUnits;
    private Array<EProducer> eProducers;
    private Array<EConsumer> eConsumers;
    private Array<EStorage> eStorage;

    public ENetwork() {
        eUnits = new Array<AbstractEUnit>();
        eProducers = new Array<EProducer>();
        eConsumers = new Array<EConsumer>();
        eStorage = new Array<EStorage>();
    }

    public void add(AbstractEUnit u) {
        if(u != null) {
            eUnits.add(u);
            if(u instanceof EProducer) eProducers.add((EProducer) u);
            if(u instanceof EConsumer) eConsumers.add((EConsumer) u);
            if(u instanceof EStorage) eStorage.add((EStorage) u);
        }
    }

    private Array<AbstractEUnit> getUnits() {
        return eUnits;
    }

    public ENetwork merge(ENetwork net) {
        // TODO: Add all elements in 'net' to this object
        // TODO: Destroy the old 'net'
        for(AbstractEUnit u : net.getUnits()) {
            this.add(u);
        }
        net.dispose();
        return this;
    }

    public ENetwork split(EUnit unit) {
        // TODO: Figure out how to split a network when an EUnit is removed
        // TODO: probably a traversal to find neighbors and reconstruct
        // TODO: possibly four entirely new ENetworks.
        return this;
    }

    public static void tickNetwork(ENetwork net) {
        net.tick();
    }

    private void tick() {
        /**
         * PRODUCTION PHASE
         * Gets all available energy on the network.
         * - Gets all producers production amounts
         * - Gets all storage extraction amounts
         */
        long availableEnergy = 0;
        for(EProducer ep : eProducers) {
            availableEnergy += ep.produce();
        }
        for(EStorage es : eStorage) {
            availableEnergy += es.extract(es.getCapacity());
        }

        /**
         * CONSUMPTION PHASE
         * Splits the available energy into equal parts for all consumers, then iterates until all consumers are full,
         * or available energy is 0 (zero)
         */
        boolean allConsumersFull = true;
        long numConsumersNotFull = 0;

        for(EConsumer ec : eConsumers) {
            if(!ec.isFull()) {
                allConsumersFull = false;   // If one consumer is not full, then not all consumers are full
                numConsumersNotFull++;      // Increment number of consumers not full
            }
        }

        while(!allConsumersFull && availableEnergy > 0) {
            long portion = availableEnergy / numConsumersNotFull;
            long remainder = availableEnergy % numConsumersNotFull;

            availableEnergy = 0;
            allConsumersFull = true;
            numConsumersNotFull = 0;

            for(EConsumer ec : eConsumers) {
                if(!ec.isFull()) {
                    if(remainder > 0) {
                        availableEnergy += ec.consume(portion + 1);
                        remainder--;
                    }
                    else {
                        availableEnergy += ec.consume(portion);
                    }

                    if(!ec.isFull()) {
                        allConsumersFull = false;
                        numConsumersNotFull++;
                    }
                } // END if ec not full
            } // END for ec in eConsumers
        } // END while not all consumers full or available energy > 0

        /**
         * STORAGE PHASE
         * TODO: NYI
         */
    }

    @Override
    public void dispose() {
        // TODO: Figure out how to dispose of this correctly
    }
}
