package com.github.infosimulators.gui.GElement;

import processing.core.PApplet;

public class Switch extends GElement{

	private boolean on;
	
	public Switch(boolean on, float x, float y, float xSize, float ySize) {
		super(x, y, xSize, ySize);
		this.on = on;
	}

	@Override
	public void update(PApplet p) {
		if (on){
			p.noStroke();
			p.fill(255);
		}else{
			p.stroke(255);
			p.noFill();
		}
		p.rect(x, y, xSize/2, ySize);
		if (!on){
			p.noStroke();
			p.fill(255);
		}else{
			p.stroke(255);
			p.noFill();
		}
		p.rect(x + xSize/2, y, xSize/2, ySize);
	}	
}
