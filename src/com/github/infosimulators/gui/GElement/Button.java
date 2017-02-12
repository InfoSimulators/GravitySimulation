package com.github.infosimulators.gui.GElement;


import processing.core.PApplet;

public class Button extends GElement{
	
	private Runnable r;

	public Button(Runnable r, float x, float y, float xSize, float ySize) {
		super(x, y, xSize, ySize);
		this.r = r;
	}

	@Override
	public void update(PApplet p){
		if (p.mouseX > x && p.mouseY > y && p.mouseX < x+xSize && p.mouseY < y+ySize){
			p.fill(255, 0, 0);
			if (p.mousePressed){
				p.fill(200, 0, 0);
				r.run();
			}
		}else{
			p.fill(255);
		}
		p.stroke(200);
		p.rect(x, y, xSize, ySize);
	}
}