package com.derelictech.macromachine.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.derelictech.macromachine.util.Const;

/**
 * Created by Tim on 4/5/2016.
 */
public class GameScreen extends AbstractGameScreen {

    private Camera camera;
    private static Viewport viewport;
    private Stage stage;

    public GameScreen(Game game) {
        super(game);
        camera = new OrthographicCamera();
        viewport = new FitViewport(Const.VIEWPORT_W, Const.VIEWPORT_H, camera);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
