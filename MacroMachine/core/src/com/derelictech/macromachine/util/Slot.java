package com.derelictech.macromachine.util;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.derelictech.macromachine.tiles.Tile;

/**
 * A Slot contains a {@link Tile}
 */
public class Slot extends Actor{
    private int gridX, gridY;
    private float width, height;

    private Tile tile;

    public Slot(final int gridX, final int gridY, float width, float height) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.width = width;
        this.height = height;

        setBounds(0, 0, 1, 1);
        setTouchable(Touchable.enabled);

        addListener(new ClickListener() {
            int counter = 0;
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("["+ counter++ +"] Touched Slot: x"+gridX+" y"+gridY+" Tile"+ ((tile == null) ? " NULL" : " bounds: " + tile.getX() + " " + tile.getY() + " " + tile.getWidth() + " " + tile.getHeight()
                        + " Contains: " + tile.toString() +" | hash: "+ tile.hashCode()));
                return true;
            }
        });
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile removeTile() {
        Tile t = this.tile;
        this.tile = null;
        return t;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if(tile != null) {
            tile.setPosition(x, y);
        }
    }
}
