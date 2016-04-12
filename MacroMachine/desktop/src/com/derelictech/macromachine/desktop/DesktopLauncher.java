package com.derelictech.macromachine.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.derelictech.macromachine.MacroMachine;

public class DesktopLauncher {

	private static boolean repackTextures = true;

	public static void main (String[] arg) {

		if(repackTextures) {
			TexturePacker.process(".", "packs", "pack");
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 640;
		new LwjglApplication(new MacroMachine(), config);
	}
}
