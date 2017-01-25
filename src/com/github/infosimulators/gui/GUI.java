package com.github.infosimulators.gui;

import processing.core.PApplet;

/**
 * GUI class. For interaction with GUI.
 * 
 * Single-instance class!
 * Use {@code GUI.getInstance()} instead of constructor!
 */
public class GUI extends PApplet {

	/**
	 * Static instance of GUI for public access.
	 */
	private static GUI instance;
	
	/**
	 * Number of tabs
	 */
	private int tabs;
	
	/**
	 * Constructor, to be called automatically.
	 * Do not call manually. Use {@code GUI.getInstance()} instead.
	 */
	public GUI() {
		GUI.instance = this;
		tabs = 1;
	}
	
	/**
	 * Configures this class as main processing class.
	 * Use instead of constructor.
	 * 
	 * @return Single-instance of this class.
	 */
	public static GUI getInstance() {
		if (instance == null)
			main("com.github.infosimulators.gui.GUI");
		return instance;
	}
	
	/**
	 * Called once on startup
	 */
	public void settings() {
		size(500, 400); // has to be here instead setup()
	}
	
	/**
	 * Called once after startup
	 */
	public void setup() {}
	
	/**
	 * Called each frame
	 */
	public void draw() {
		background(0);
		noStroke();
		for (int i = 0; i < tabs; i++) {
			rect(width / (float) tabs * i, 0, width / (float) tabs, 20);
		}
	}
	
	/**
	 * Example configuration method.
	 * Sets the number of tabs to display / choose from.
	 * @param tabs Number of tabs
	 */
	public void setTabs(int tabs) {
		this.tabs = tabs;
	}
	
	/**
	 * Number of tabs.
	 * @return tabs
	 */
	public int getTabs() {
		return tabs;
	}

}
