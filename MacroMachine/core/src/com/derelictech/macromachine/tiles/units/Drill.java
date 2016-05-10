package com.derelictech.macromachine.tiles.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import com.derelictech.macromachine.e_net.EConsumerUnit;
import com.derelictech.macromachine.tiles.Tile;
import com.derelictech.macromachine.tiles.materials.RadicalMaterial;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.MacroMachineEvent;

/**
 * Created by Tim on 5/7/2016.
 */
public class Drill extends EConsumerUnit {
    private Array<TextureRegion> drillAnimationFrames;
    private Animation drillAnimation;
    private static float miningTime = 0.5f; // Time in seconds
    private boolean drilling = false;

    private class DrillAction extends Action {
        float time = 0;

        @Override
        public boolean act(float delta) {

            setTextureRegion(drillAnimation.getKeyFrame(time));
            setRotation(rotation);
            time += delta;

            if(time >= miningTime) {
                time = 0;
                drilling = false;
                Tile acquiredTile = acquireMinedTile();
                fire((new MacroMachineEvent(MacroMachineEvent.Type.drilledMaterial)).setTile(acquiredTile));

                setTextureRegion(initialRegion);

                removeAction(this);

                cell.stopMining();
            }

            return false;
        }
    }
    private DrillAction drillAction = new DrillAction();

    /**
     * Constructor for AbstractEUnit
     *
     * @param cell
     */
    public Drill(Cell cell) {
        super("units/drill", cell);
        setSize(1.5f, 1.0f);

        consumeAmount = 20;

        drillAnimationFrames = Assets.inst.getFrameSequence("units/drill_extend");
        drillAnimation = new Animation(0.15f, drillAnimationFrames);
        drillAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Radical Explosion
    }

    @Override
    public String TAG() {
        return "DRL";
    }

    private Tile acquireMinedTile() {
        Tile tile = getNeighbor(rotation);
        if(tile == null) return null; // Nothing there
        if(cell.containsUnitAt(tile.getGridX(), tile.getGridY())) {
            return null; // Can't mine units in this cell.
        }
        if(tile instanceof RadicalMaterial) {
            fire((new MacroMachineEvent(MacroMachineEvent.Type.cellTakeDamage)).setDamageAmount(((RadicalMaterial) tile).getDamageAmount()));
        }
        return grid.removeTileAt(tile.getGridX(), tile.getGridY());
    }

    @Override
    public void doConsumeAction() {
        Gdx.app.log("DRILL", consumeBuffer + "/" + consumeAmount);
        if(isFull() && !drilling) {
            drilling = true;
            super.doConsumeAction();
            this.addAction(drillAction);
            Assets.inst.playDrillSound();
        }
    }
}
