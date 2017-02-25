package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.gui.GUI;

import processing.core.PApplet;

public abstract class GElement {

	protected float x, y, xSize, ySize;
	
	protected int[] color1, color2, color3;
	
	protected PApplet p;
	
	protected GElement(PApplet p, float x, float y, float xSize, float ySize){
		this.p = p;
		
		this.x = x;
		this.y = y;
		this.xSize = xSize;
		this.ySize = ySize;
		
		color1 = GUI.getInstance().getGUIColor1();
		color2 = GUI.getInstance().getGUIColor2();
		color3 = GUI.getInstance().getGUIColor3();
	}
	
	public abstract void update();
	
}
