package com.derelictech.macromachine.fx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.derelictech.macromachine.util.Const;

/**
 * Created by Tim on 5/8/2016.
 */
public class RadicalExplosion extends Actor implements Disposable{
    private ParticleEffect radicalExplode;


    public RadicalExplosion() {
        radicalExplode = new ParticleEffect();
        radicalExplode.load(Gdx.files.internal("effects/radical.fx"), Gdx.files.internal(""));
        radicalExplode.getEmitters().first().setPosition(10, 10);
        radicalExplode.scaleEffect(0.5f/ Const.TEXTURE_RESOLUTION);
        radicalExplode.start();
    }

    @Override
    public void act(float delta) {
        radicalExplode.update(delta);
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        radicalExplode.draw(batch);
        if(radicalExplode.isComplete()) radicalExplode.dispose();
    }

    public boolean isComplete() {
        radicalExplode.reset();
        return radicalExplode.isComplete();
    }

    public void reset() {
        radicalExplode.reset();
    }

    public RadicalExplosion explodeAt(float x, float y) {
        radicalExplode.getEmitters().first().setPosition(x, y);
        radicalExplode.start();
        return this;
    }

    @Override
    public void dispose() {
        radicalExplode.dispose();
    }
}
