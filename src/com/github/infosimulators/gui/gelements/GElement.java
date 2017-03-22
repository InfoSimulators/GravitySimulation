package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.gui.GUI;

import processing.core.PApplet;

/**
 * Abstract class containing methods and variables used by all GElements.
 *
 */
public abstract class GElement {

	protected String ID;

	protected float x, y, xSize, ySize;

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void modifyX(float x) {
		this.x += x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void modifyY(float y) {
		this.y += y;
	}

	protected int color1, color2, color3;

	protected GElement(String ID, float x, float y, float xSize, float ySize, int color1, int color2, int color3) {

		this.ID = ID;

		this.x = x;
		this.y = y;
		this.xSize = xSize;
		this.ySize = ySize;

		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
	}

	protected GElement(String ID, float x, float y, float xSize, float ySize) {

		this.ID = ID;

		this.x = x;
		this.y = y;
		this.xSize = xSize;
		this.ySize = ySize;

		this.color1 = GUI.getInstance().getGUIColor1();
		this.color2 = GUI.getInstance().getGUIColor2();
		this.color3 = GUI.getInstance().getGUIColor3();
	}

	protected boolean hovered(PApplet p) {
		if (x <= p.mouseX && x + xSize >= p.mouseX && y <= p.mouseY && y + ySize >= p.mouseY) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}

	public abstract void update(PApplet p);

	/**
	 * @return the xSize
	 */
	public float getxSize() {
		return xSize;
	}

	/**
	 * @param xSize
	 *            the xSize to set
	 */
	public void setxSize(float xSize) {
		this.xSize = xSize;
	}

	/**
	 * @return the ySize
	 */
	public float getySize() {
		return ySize;
	}

	/**
	 * @param ySize
	 *            the ySize to set
	 */
	public void setySize(float ySize) {
		this.ySize = ySize;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the color1
	 */
	public int getColor1() {
		return color1;
	}

	/**
	 * @param color1
	 *            the color1 to set
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
	 * @param color2
	 *            the color2 to set
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
	 * @param color3
	 *            the color3 to set
	 */
	public void setColor3(int color3) {
		this.color3 = color3;
	}

}