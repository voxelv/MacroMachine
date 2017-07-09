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
public class GameOverScreen extends AbstractGameScreen {
    private Skin skin;
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
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Label gameOver = new Label("Game Over.", skin, "default");
        gameOver.setFontScale(3);
        gameOver.setPosition(Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight() - 30);
        stage.addActor(gameOver);

        Label levelAchieved = new Label("You Made it to Level: " + Integer.toString(powerLevel), skin, "default");
        levelAchieved.setFontScale(3);
        levelAchieved.setPosition(Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight() - 90);
        stage.addActor(levelAchieved);



        TextButton quitButton = new TextButton("Quit", skin, "default");
        quitButton.setWidth(100);
        quitButton.setHeight(50);
        quitButton.setPosition(Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight() - 160);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.dispose();
                Assets.inst.dispose();
                System.exit(0);
            }
        });
        stage.addActor(quitButton);
        TextButton rePlayButton = new TextButton("Play Again", skin, "default");
        rePlayButton.setWidth(100);
        rePlayButton.setHeight(50);
        rePlayButton.setPosition(Gdx.graphics.getWidth()/2 + 100, Gdx.graphics.getHeight() - 160);
        rePlayButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
            }
        });
        stage.addActor(rePlayButton);

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
