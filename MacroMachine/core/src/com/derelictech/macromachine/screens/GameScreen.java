package com.derelictech.macromachine.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.derelictech.macromachine.util.Const;
import com.derelictech.macromachine.util.Level;

/**
 * Main Game Screen
 * @author Tim Slippy, voxelv
 * @author Nate Groggett, Ghost4dot2
 */
public class GameScreen extends AbstractGameScreen {

    private Camera camera;
    private Viewport viewport;
    private Stage stage;

    private Camera hud_cam;
    private Viewport hud_view;
    private Stage hud;

    private InputMultiplexer multiplexer;


    /**
     * Constructor for {@link GameScreen}
     * @param game The game to set. Used to switch screens.
     */
    public GameScreen(Game game) {
        super(game);
        camera = new OrthographicCamera();
        viewport = new FitViewport(Const.VIEWPORT_W, Const.VIEWPORT_H, camera);
        stage = new Stage(viewport);
        camera.update();

        hud_cam = new OrthographicCamera();
        hud_view = new FitViewport(Const.HUI_VIEWPORT_W, Const.HUI_VIEWPORT_H, hud_cam);
        hud = new Stage(hud_view);

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(hud);
        Gdx.input.setInputProcessor(multiplexer);
        
//        Cell cell = new Cell();
//        cell.setTouchable(Touchable.enabled);
//        cell.setPosition(15, 15);
//        stage.addActor(cell);


//        Cell spinner = new Cell();
//        spinner.setTouchable(Touchable.enabled);
//        spinner.setPosition(0, 0);
//        hud.addActor(spinner);

        Level level = new Level(this, 0);
        stage.addActor(level);


    }

    public void setViewportWorldSize(float width, float height) {
        viewport.setWorldSize(width, height);
    }

    /**
     * Sets the background color and renders the stage
     * @param delta Amount of time between calls
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        hud.act(delta);

        stage.draw();
        hud.draw();
    }

    /**
     * Called when the window is resized. Updates the viewport with the new information. Updates the camera.
     * @param width New width of the window
     * @param height New height of the window
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();

        hud_view.update(width, height, true);
        hud_cam.update();

    }

    @Override
    public void dispose() {
        stage.dispose();
        hud.dispose();
    }
}
