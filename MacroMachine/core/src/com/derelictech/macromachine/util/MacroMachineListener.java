package com.derelictech.macromachine.util;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.derelictech.macromachine.tiles.Tile;

/**
 * Created by Tim on 5/7/2016.
 */
public class MacroMachineListener implements EventListener {
    @Override
    public boolean handle(Event e) {
        if (!(e instanceof MacroMachineEvent)) return false;
        MacroMachineEvent event = ((MacroMachineEvent) e);

        switch(event.getType()) {
            case cellDeath:
                return cellDeath(event);
            case cellTakeDamage:
                return cellTakeDamage(event, event.getDamageAmount());
            case drilledMaterial:
                return drilledMaterial(event, event.getTile());
        }

        return false;
    }

    public boolean cellDeath(MacroMachineEvent event) {
        return false;
    }

    public boolean cellTakeDamage(MacroMachineEvent event, long amount) {
        return false;
    }

    public boolean drilledMaterial(MacroMachineEvent event, Tile tile) {
        return false;
    }
}
