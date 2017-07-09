package com.derelictech.macromachine.util;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.derelictech.macromachine.tiles.Tile;

/**
 * Created by Tim on 5/7/2016.
 */
public class MacroMachineEvent extends Event {
    private Type type;

    private long amount = 0;
    private Tile tile;

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
        cellDeath,
        cellTakeDamage,
        drilledMaterial
    }

    public long getDamageAmount() {
        return amount;
    }

    public MacroMachineEvent setDamageAmount(long amount) {
        this.amount = amount;
        return this;
    }

    public Tile getTile() {
        return tile;
    }

    public MacroMachineEvent setTile(Tile tile) {
        this.tile = tile;
        return this;
    }

}
