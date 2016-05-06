package com.derelictech.macromachine.e_net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.StringBuilder;
import com.derelictech.macromachine.tiles.Tile;
import com.derelictech.macromachine.util.GridDirection;

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
            if(!eUnits.contains(u, true)) {
                eUnits.add(u);
                if (u instanceof EProducer) eProducers.add((EProducer) u);
                if (u instanceof EConsumer) eConsumers.add((EConsumer) u);
                if (u instanceof EStorage) eStorage.add((EStorage) u);
            }
        }
    }

    public void remove(AbstractEUnit u) {
        if(u != null) {
            if(eUnits.contains(u, true)) {
                eUnits.removeValue(u, true);
                if (u instanceof EProducer) eProducers.removeValue((EProducer) u, true);
                if (u instanceof EConsumer) eConsumers.removeValue((EConsumer) u, true);
                if (u instanceof EStorage) eStorage.removeValue((EStorage) u, true);
            }
        }
    }

    private Array<AbstractEUnit> getUnits() {
        return eUnits;
    }

    public ENetwork merge(ENetwork net) {
        for(AbstractEUnit u : net.getUnits()) {
            this.add(u);
            u.setNetwork(this);
        }
        net.dispose();
        return this;
    }

    public ENetwork split(AbstractEUnit unit) {
        // TODO: Figure out how to split a network when an EUnit is removed
        // TODO: probably a traversal to find neighbors and reconstruct
        // TODO: possibly four entirely new ENetworks.

        remove(unit);
        for(GridDirection dir : GridDirection.values()) {
            ENetwork newNet = new ENetwork();
            Tile tile = unit.getNeighbor(dir);
            if(tile instanceof AbstractEUnit) {
                ((AbstractEUnit) tile).reCalcNet(newNet);
            }
        }
        return this;
    }

    public static void tickNetwork(ENetwork net) {
        net.tick();
    }

    private void tick() {
        Gdx.app.log("NET", "Want to tick.");
        if(eProducers.size + eConsumers.size + eStorage.size < 2) return; // Skip if only one in this net. Saves CPU.
        /**
         * PRODUCTION PHASE
         * Gets all available energy on the network.
         * - Gets all producers production amounts
         * - Gets all storage extraction amounts
         */
        long availableEnergy;
        availableEnergy = 0;
        for(EProducer ep : eProducers) {
            availableEnergy += ep.produce();
        }
        for(EStorage es : eStorage) {
            availableEnergy += es.extract(es.getCapacity());
        }

        Gdx.app.log("NET", "Available Energy: " + availableEnergy);

        /**
         * CONSUMPTION PHASE
         * Splits the available energy into equal parts for all consumers, then iterates until all consumers are full,
         * or available energy is 0 (zero)
         */
        boolean allConsumersFull = true;
        long numConsumersNotFull = 0;

        for(EConsumer ec : eConsumers) {
            if(ec.willConsume()) {
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
         * Stores extra energy into storage on the network using the same algorithm
         */
        boolean allStorageFull = true;
        long numStorageNotFull = 0;

        for(EStorage es : eStorage) {
            if(!es.isFull()) {
                allStorageFull = false;   // If one consumer is not full, then not all consumers are full
                numStorageNotFull++;      // Increment number of consumers not full
            }
        }

        while(!allStorageFull && availableEnergy > 0) {
            long portion = availableEnergy / numStorageNotFull;
            long remainder = availableEnergy % numStorageNotFull;

            availableEnergy = 0;
            allStorageFull = true;
            numStorageNotFull = 0;

            for(EStorage es : eStorage) {
                if(!es.isFull()) {
                    if(remainder > 0) {
                        availableEnergy += es.store(portion + 1);
                        remainder--;
                    }
                    else {
                        availableEnergy += es.store(portion);
                    }

                    if(!es.isFull()) {
                        allStorageFull = false;
                        numStorageNotFull++;
                    }
                } // END if es not full
            } // END for es in eStorage
        } // END while not all storage full or available energy > 0
    }

    @Override
    public void dispose() {
        // TODO: Figure out how to dispose of this correctly
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\nEUnits: \n");
        sb.append(eUnits.toString());
        sb.append("\nEProducers: \n");
        sb.append(eProducers.toString());
        sb.append("\nEConsumers: \n");
        sb.append(eConsumers.toString());
        sb.append("\nEStorage: \n");
        sb.append(eStorage.toString());
        return sb.toString();
    }
}
