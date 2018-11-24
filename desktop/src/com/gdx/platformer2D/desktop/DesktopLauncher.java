package com.gdx.platformer2D.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdx.platformer2D.Platformer2D;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 300;
        config.height = 300;
		new LwjglApplication(new Platformer2D(), config);
	}
}
