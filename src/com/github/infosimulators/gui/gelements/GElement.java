package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.gui.GUI;

import processing.core.PApplet;

public abstract class GElement {

	protected float x, y, xSize, ySize;
	
	protected int color1, color2, color3;
	
	protected GElement(float x, float y, float xSize, float ySize, int color1, int color2, int color3){
		
		this.x = x;
		this.y = y;
		this.xSize = xSize;
		this.ySize = ySize;
		
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
	}
	
	protected GElement(float x, float y, float xSize, float ySize){
		
		this.x = x;
		this.y = y;
		this.xSize = xSize;
		this.ySize = ySize;
		
		this.color1 = GUI.getInstance().getGUIColor1();
		this.color2 = GUI.getInstance().getGUIColor2();
		this.color3 = GUI.getInstance().getGUIColor3();
	}
	
	public abstract void update(PApplet p);

	/**
	 * @return the color1
	 */
	public int getColor1() {
		return color1;
	}

	/**
	 * @param color1 the color1 to set
	 */
	public void setColor1(int color1) {
		this.color1 = color1;
	}

	/**
	 * @return the color2
	 */
	public int getColor2() {
		return color2;
	}

	/**
	 * @param color2 the color2 to set
	 */
	public void setColor2(int color2) {
		this.color2 = color2;
	}

	/**
	 * @return the color3
	 */
	public int getColor3() {
		return color3;
	}

	/**
	 * @param color3 the color3 to set
	 */
	public void setColor3(int color3) {
		this.color3 = color3;
	}
	
	
}
