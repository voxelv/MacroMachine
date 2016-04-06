package com.derelictech.macromachine;

import com.badlogic.gdx.Game;
import screens.GameScreen;
import util.Assets;

public class MacroMachine extends Game {

	@Override
	public void create () {
		Assets.inst.init();
		setScreen(new GameScreen(this));
	}
}
