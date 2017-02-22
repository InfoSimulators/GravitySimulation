package com.github.infosimulators.gui.GElement;

import processing.core.PApplet;

/**
 * Abstract class for GElements to take variables and methods from.
 */
public abstract class GElement {

	protected float x, y, xSize, ySize;
	protected PApplet p;

	public GElement(float x, float y, float xSize, float ySize) {
		this.x = x;
		this.y = y;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public abstract void update(PApplet p);

}
