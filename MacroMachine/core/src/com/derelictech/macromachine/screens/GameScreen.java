package com.derelictech.macromachine.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.*;
import com.derelictech.macromachine.util.Const;
import com.derelictech.macromachine.util.Level;

/**
 * Main Game Screen
 * @author Tim Slippy, voxelv
 * @author Nate Groggett, Ghost4dot2
 */
public class GameScreen extends AbstractGameScreen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    private Camera hud_cam;
    private Viewport hud_view;
    private Stage hud;

    private InputMultiplexer multiplexer;

    private Level level;

    /**
     * Constructor for {@link GameScreen}
     * @param game The game to set. Used to switch screens.
     */
    public GameScreen(Game game) {
        super(game);
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Const.VIEWPORT_W, Const.VIEWPORT_H, Const.VIEWPORT_W, Const.VIEWPORT_H, camera);
//        viewport = new FillViewport(camera);
        stage = new Stage(viewport);
        camera.update();

        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector3 mouseRaw = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                Vector3 prevWorldMouse = new Vector3(camera.unproject(mouseRaw));
                System.out.print("TouchLoc: " + prevWorldMouse.toString());
                if(event.getRelatedActor() != null) System.out.println(" Touched: " + event.getRelatedActor().toString());
                else System.out.println();
                return true;
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                Vector3 mouseRaw = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                System.out.println("Mouse At: " + mouseRaw.toString());
                System.out.println("Params: x: " + x + " y: " + y);
                Vector3 prevWorldMouse = new Vector3(viewport.unproject(mouseRaw)); // Get mouse location before zoom

                if(amount > 0) { // Scrolling Out
                    viewport.setWorldSize(viewport.getWorldWidth() + Math.abs(amount), viewport.getWorldHeight() + Math.abs(amount)); // Change viewport

                    if(viewport.getWorldWidth() > Const.VIEWPORT_W || viewport.getWorldHeight() > Const.VIEWPORT_H) {
                        viewport.setWorldSize(Const.VIEWPORT_W, Const.VIEWPORT_H); // CLAMP
                    }
                }
                else if(amount < 0) { // Scrolling In
                    viewport.setWorldSize(viewport.getWorldWidth() - Math.abs(amount), viewport.getWorldHeight() - Math.abs(amount)); // Change viewport
                    if(viewport.getWorldWidth() < 1 || viewport.getWorldHeight() < 1) {
                        viewport.setWorldSize(1, 1); // CLAMP
                    }
                }
                viewport.apply(true); // Update viewport, don't center camera.

                mouseRaw.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                Vector3 newWorldMouse = new Vector3(viewport.unproject(mouseRaw)); // Get new mouse location
                prevWorldMouse.sub(newWorldMouse); // Math

                camera.translate(prevWorldMouse); // Move camera to correct location so that mouse is over the exact spot
                // it was over before zooming (unless clamping disallows as per below)

                if(camera.position.x - camera.viewportWidth/2 < 0) {
                    camera.position.x = camera.viewportWidth/2;
                }
                if(camera.position.y - camera.viewportHeight/2 < 0) {
                    camera.position.y = camera.viewportHeight/2;
                }
                if(camera.position.x + camera.viewportWidth/2 > Const.VIEWPORT_W) {
                    camera.position.x = Const.VIEWPORT_W - camera.viewportWidth/2;
                }
                if(camera.position.y + camera.viewportHeight/2 > Const.VIEWPORT_H) {
                    camera.position.y = Const.VIEWPORT_H - camera.viewportHeight/2;
                }

                return true;
            }
        });

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

        level = new Level(this, 0);
        stage.addActor(level);
        System.out.println("Stagesetup");
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
        camera.update(false);

        hud_view.update(width, height, true);
        hud_cam.update();

    }

    @Override
    public void dispose() {
        stage.dispose();
        hud.dispose();
    }
}
