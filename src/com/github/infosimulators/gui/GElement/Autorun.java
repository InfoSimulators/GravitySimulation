package com.github.infosimulators.gui.GElement;

/*
 * Special Button automatically running its method if displayed on screen.
 */
public class Autorun extends Button {

	public Autorun(Runnable r, float x, float y, float xSize, float ySize) {
		super(r, null, x, y, xSize, ySize);
	}

	public void update() {
		r.run();
	}

}
