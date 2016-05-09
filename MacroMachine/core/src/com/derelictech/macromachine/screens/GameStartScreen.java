package com.derelictech.macromachine.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.derelictech.macromachine.util.Assets;
import com.derelictech.macromachine.util.Const;

/**
 * Created by Tim on 5/7/2016.
 */
public class GameStartScreen extends AbstractGameScreen {
    private Skin skin;
    private Stage stage;
    /**
     * Constructor for this screen that sets its {@link AbstractGameScreen#game}
     * Used to set the next screen to display
     *
     * @param inputGame The game this screen in
     */
    public GameStartScreen(Game inputGame) {
        super(inputGame);

        stage = new Stage(new ExtendViewport(Const.HUD_VIEWPORT_W, Const.HUD_VIEWPORT_H, Const.HUD_VIEWPORT_W, Const.HUD_VIEWPORT_H));
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Label welcome = new Label("Welcome to MacroMachine", skin, "default");
        welcome.setFontScale(4);
        welcome.setPosition(10, Gdx.graphics.getHeight() + 75);
        stage.addActor(welcome);

        Label info = new Label(
                "Gather Resources, \nPress Space to Activate drills, \nMove with Arrow Keys when no obstacle, \nRightclick to remove an object from the Cell and" +
                        "\n  place it in the inventory, \nSelect Unit to build, click build and then click location, \nWhen level is empty, click Level Up, \n" +
                        "Observe help messages", skin, "default");
        info.setFontScale(2);
        info.setPosition(10, Gdx.graphics.getHeight() - 200);
        stage.addActor(info);



        TextButton quitButton = new TextButton("Quit", skin, "default");
        quitButton.setWidth(100);
        quitButton.setHeight(50);
        quitButton.setPosition(Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight() - 350);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.dispose();
                Assets.inst.dispose();
                System.exit(0);
            }
        });
        stage.addActor(quitButton);
        TextButton playButton = new TextButton("Play", skin, "default");
        playButton.setWidth(100);
        playButton.setHeight(50);
        playButton.setPosition(Gdx.graphics.getWidth()/2 + 100, Gdx.graphics.getHeight() - 350);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
            }
        });
        stage.addActor(playButton);

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
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
