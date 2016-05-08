package com.derelictech.macromachine.util;

import com.badlogic.gdx.scenes.scene2d.Event;

/**
 * Created by Tim on 5/7/2016.
 */
public class MacroMachineEvent extends Event {
    private Type type;

    public MacroMachineEvent(Type type) {
        setType(type);
    }

    /** The type of input event. */
    public Type getType () {
        return type;
    }

    public void setType (Type type) {
        this.type = type;
    }

    public static enum Type {
        cellDeath
    }


}
