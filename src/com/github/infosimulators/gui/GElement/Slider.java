package com.github.infosimulators.gui.GElement;

import processing.core.PApplet;

/*
 * GElement to hold a value between 0 and 100 and run its method that many times.
 */
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
		p.stroke(255);
		p.fill(255);
		
		//Draw the slider
		p.line(x + ySize/2, y + ySize/2, x + xSize - ySize/2, y + ySize/2);
		p.ellipse(PApplet.map(state, 0, 100, x + ySize/2, x + xSize - ySize/2), y + ySize/2, ySize, ySize);
		
		//Checks for mouse input for the slider
		if(p.mouseX > x && p.mouseY > y && p.mouseX < x+xSize && p.mouseY < y+ySize){
			if(p.mousePressed){
				state = 100*(p.mouseX-x)/xSize;
			}
		}
		
		//runs the method according to the state
		for(int i = 0; i < (int)state; i++){
			r.run();
		}
		
	}

}
