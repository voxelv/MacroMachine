package com.derelictech.macromachine.util;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

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
        }

        return false;
    }

    public boolean cellDeath(MacroMachineEvent event) {
        return false;
    }
}
