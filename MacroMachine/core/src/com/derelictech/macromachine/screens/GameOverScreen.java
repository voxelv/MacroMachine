package com.derelictech.macromachine.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Tim on 5/7/2016.
 */
public class GameOverScreen extends AbstractGameScreen {
    /**
     * Constructor for this screen that sets its {@link AbstractGameScreen#game}
     * Used to set the next screen to display
     *
     * @param inputGame The game this screen in
     */
    public GameOverScreen(Game inputGame, int powerLevel) {
        super(inputGame);
        Gdx.app.log("GAMEOVERSCREEN", "You Lost.");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
