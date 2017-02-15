package com.github.infosimulators.gui.GElement;

import processing.core.PApplet;

public class Slider extends GElement{
	
	private float state;
	private Runnable r;

	public Slider(float state, Runnable r, float x, float y, float xSize, float ySize) {
		super(x, y, xSize, ySize);
		this.state = state;
		this.r = r;
	}

	@Override
	public void update(PApplet p) {
		// TODO Auto-generated method stub
		
	}

}
