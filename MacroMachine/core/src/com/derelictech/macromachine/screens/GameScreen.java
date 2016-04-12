package com.derelictech.macromachine.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.derelictech.macromachine.units.TheCell;
import com.derelictech.macromachine.units.Wire;
import com.derelictech.macromachine.util.CellGrid;
import com.derelictech.macromachine.util.Const;

/**
 * Created by Tim on 4/5/2016.
 */
public class GameScreen extends AbstractGameScreen {

    private Camera camera;
    private Viewport viewport;
    private Stage stage;

    private Camera hui_cam;
    private Viewport hui_view;

    private InputProcessor hui_input;

    public GameScreen(Game game) {
        super(game);
        camera = new OrthographicCamera();
        viewport = new FitViewport(Const.VIEWPORT_W, Const.VIEWPORT_H, camera);
        stage = new Stage(viewport);
        camera.update();

        hui_cam = new OrthographicCamera();
        hui_view = new FitViewport(Const.HUI_VIEWPORT_W, Const.HUI_VIEWPORT_H, hui_cam);

        //hui_input = new //input processor based for the hui

        Gdx.input.setInputProcessor(stage);

        Wire testWire = new Wire();
        stage.addActor(testWire);
        TheCell cell = new TheCell();
        cell.setPosition(10, 10);
        stage.addActor(cell);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }
}
