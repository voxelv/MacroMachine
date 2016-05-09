package com.derelictech.macromachine.fx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Tim on 5/8/2016.
 */
public class RadicalExplosion extends Actor {
    private ParticleEffect radicalExplode = new ParticleEffect();


    public RadicalExplosion() {
        radicalExplode.load(Gdx.files.internal("effects/radical.fx"), Gdx.files.internal(""));
    }

    @Override
    public void act(float delta) {
        radicalExplode.update(delta);
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        radicalExplode.draw(batch, parentAlpha);
        if(radicalExplode.isComplete()) radicalExplode.dispose();
    }

    public boolean isComplete() {
        radicalExplode.reset();
        return radicalExplode.isComplete();
    }

    public void explodeAt(float x, float y) {
        radicalExplode.getEmitters().first().setPosition(x, y);
        radicalExplode.start();
    }
}
