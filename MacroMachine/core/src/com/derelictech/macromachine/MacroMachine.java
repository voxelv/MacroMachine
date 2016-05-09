package com.derelictech.macromachine;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.derelictech.macromachine.screens.GameStartScreen;
import com.derelictech.macromachine.util.Assets;

/**
 * MacroMachine Game Class
 * Basically delegates stuff to screens.
 * @author Tim Slippy, voxelv
 */
public class MacroMachine extends Game {

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.inst.init();
		Assets.inst.playMusic(true);
		setScreen(new GameStartScreen(this));
	}
}
