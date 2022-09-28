package com.glaikunt.framework.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.glaikunt.framework.DynamicDisplay;

/**
 * Conversion to lwjgl3
 *  - default libgdx backend version
 * 	- discontinuation of lwjgl3
 * 	- better support for JREs, macOS, Linux and muti-window environments
 * https://libgdx.com/news/2021/07/devlog-7-lwjgl3
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Libgdx Framework");
		config.useVsync(true);
		config.setForegroundFPS(60);
		config.setWindowedMode(1280, 960);
		config.setResizable(false);
//		new LwjglApplication(new Display2D(), config);
//		new LwjglApplication(new Display3D(), config);
		new Lwjgl3Application(new DynamicDisplay(), config);
	}
}
