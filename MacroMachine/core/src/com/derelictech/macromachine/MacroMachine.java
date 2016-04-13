package com.derelictech.macromachine;

import com.badlogic.gdx.Game;
import com.derelictech.macromachine.screens.GameScreen;
import com.derelictech.macromachine.util.Assets;

/**
 * MacroMachine Game Class
 * Basically delegates stuff to screens.
 * @author Tim Slippy, voxelv
 */
public class MacroMachine extends Game {

	@Override
	public void create () {
		Assets.inst.init();
		setScreen(new GameScreen(this));
	}
}
