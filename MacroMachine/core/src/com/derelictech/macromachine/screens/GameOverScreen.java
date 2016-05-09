package com.derelictech.macromachine.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.Const;

/**
 * Created by Tim on 5/7/2016.
 */
public class GameOverScreen extends AbstractGameScreen {
    private Stage stage;
    /**
     * Constructor for this screen that sets its {@link AbstractGameScreen#game}
     * Used to set the next screen to display
     *
     * @param inputGame The game this screen in
     */
    public GameOverScreen(Game inputGame, int powerLevel) {
        super(inputGame);
        Gdx.app.log("GAMEOVERSCREEN", "You Lost.");
        Assets.inst.playMusic(false);

        stage = new Stage(new ExtendViewport(Const.HUD_VIEWPORT_W, Const.HUD_VIEWPORT_H, Const.HUD_VIEWPORT_W, Const.HUD_VIEWPORT_H));



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
