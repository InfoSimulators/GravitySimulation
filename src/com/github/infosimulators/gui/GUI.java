package com.github.infosimulators.gui;

import processing.core.PApplet;

/**
 * GUI class. For interaction with GUI.
 * 
 * Single-instance class! Use {@code GUI.getInstance()} instead of constructor!
 */
public class GUI extends PApplet {

	/**
	 * Static instance of GUI for public access.
	 */
	private static GUI instance;


	/**
	 * Constructor, to be called automatically. Do not call manually. Use
	 * {@code GUI.getInstance()} instead.
	 */
	public GUI() {
		
	}

	/**
	 * Called once on startup
	 */
	public void settings() {
		size(600, 600);
	}

	/**
	 * Called once after startup
	 */
	public void setup() {
		
	}

	/**
	 * Called each frame
	 */
	public void draw() {
		
	}

	/**
	 * Configures this class as main processing class. Use instead of
	 * constructor.
	 * 
	 * @return Single-instance of this class.
	 */
	public static GUI getInstance() {
		if (instance == null)
			main("com.github.infosimulators.gui.GUI");
		return instance;
	}
}
