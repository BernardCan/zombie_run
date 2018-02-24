package com.game.zombierunell.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.zombierunell.ZombieRun;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = ZombieRun.WIDTH;
		config.height = ZombieRun.HEIGHT;
		config.title = ZombieRun.TITLE;
		new LwjglApplication(new ZombieRun(), config);
	}
}
