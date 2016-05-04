package com.derelictech.macromachine.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.*;
import com.derelictech.macromachine.util.Const;
import com.derelictech.macromachine.util.GridDirection;
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
    private Skin skin;


    private InputMultiplexer multiplexer;

    private Level level;

    /**
     * Constructor for {@link GameScreen}
     * @param game The game to set. Used to switch screens.
     */
    public GameScreen(Game game) {
        super(game);
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new ExtendViewport(Const.VIEWPORT_W, Const.VIEWPORT_H, Const.VIEWPORT_W, Const.VIEWPORT_H, camera);
        stage = new Stage(viewport);
        camera.update();

//        hud_cam = new OrthographicCamera();
//        hud_view = new ExtendViewport(Const.HUI_VIEWPORT_W,Const.HUI_VIEWPORT_H, Const.HUI_VIEWPORT_W, Const.HUI_VIEWPORT_H, hud_cam);
        hud = new Stage();
//        skin = new Skin();
//        skin.add("buttonPressed", new Texture("ui_object/pressed_button.png"));
//        skin.add("button", new Texture("button.png"));
////        hud_cam.update();
//        Table table = new Table();
//        table.setFillParent(true);
//        hud.addActor(table);
//        Button button = new Button(skin);
//        table.add(button);
//        table.setDebug(true);







        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector3 mouseRaw = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                float gutterX = viewport.getWorldWidth();
                float gutterY = viewport.getWorldHeight();
                int width = viewport.getScreenWidth();
                int height = viewport.getScreenHeight();
                Vector3 prevWorldMouse = new Vector3(camera.unproject(mouseRaw));
                System.out.print("TouchLoc: " + prevWorldMouse.toString());
                System.out.print("\nmouseRaw: " + mouseRaw.toString());
                System.out.print("\nGutter: " + Float.toString(gutterX) + " " + Float.toString(gutterY));
                System.out.print("\nScreen Width/height: " + Integer.toString(width) + " " + Integer.toString(height));
                if(event.getRelatedActor() != null) System.out.println(" Touched: " + event.getRelatedActor().toString());
                else System.out.println();
                return true;
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                Vector3 mouseRaw = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
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

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch(keycode) {
                    case Input.Keys.RIGHT:
                        System.out.println("Move cell returns: " + level.moveCell(GridDirection.RIGHT));
                        break;
                    case Input.Keys.UP:
                        System.out.println("Move cell returns: " + level.moveCell(GridDirection.UP));
                        break;
                    case Input.Keys.LEFT:
                        System.out.println("Move cell returns: " + level.moveCell(GridDirection.LEFT));
                        break;
                    case Input.Keys.DOWN:
                        System.out.println("Move cell returns: " + level.moveCell(GridDirection.DOWN));
                        break;
                    case Input.Keys.U:
                        System.out.println("GO UP ONE LEVEL");
                        break;
                    case Input.Keys.D:
                        System.out.println("GO DN ONE LEVEL");
                        break;
                    case Input.Keys.R:
                        level.upLevel();
                        break;

                    default:
                        break;
                }
                return true;
            }
        });


        hud_cam = new OrthographicCamera();
        hud_view = new FitViewport(Const.HUI_VIEWPORT_W, Const.HUI_VIEWPORT_H, hud_cam);
        hud = new Stage(hud_view);
//        hud.addActor(world_back);


        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(hud);
        Gdx.input.setInputProcessor(multiplexer);

        level = new Level(this, 0);
        stage.addActor(level);
    }

    /**
     * Sets the background color and renders the stage
     * @param delta Amount of time between calls
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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


        hud.getViewport().update(width, height, true);
//        hud_view.update(width, height, true);
//        hud_cam.update();

    }

    @Override
    public void dispose() {
        stage.dispose();
        hud.dispose();
    }
}
