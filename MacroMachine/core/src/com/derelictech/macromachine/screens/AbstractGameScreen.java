package com.derelictech.macromachine.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;

/**
 * A screen for the {@link Game}.
 */
public class AbstractGameScreen extends ScreenAdapter {

    private Game game;

    /**
     * Constructor for this screen that sets its {@link AbstractGameScreen#game}
     * Used to set the next screen to display
     * @param game The game this screen in
     */
    public AbstractGameScreen(Game game) {
        this.game = game;
    }
}
