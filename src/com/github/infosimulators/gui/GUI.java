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
	 * States
	 */
	private State currentState;
	//private List<State> states;

	/**
	 * GUI Colors
	 */
	private int GUIColor1 = 0x000000, GUIColor2 = 0x00ffff, GUIColor3 = 0xffffff;
	
	/**
	 * Constructor, to be called automatically. Do not call manually. Use
	 * {@code GUI.getInstance()} instead.
	 */
	public GUI() {
		instance = this;
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
		
		textFont(loadFont("Helvetica-Bold-96.vlw"), 96);
		
		currentState = new State();
	}

	/**
	 * Called each frame
	 */
	public void draw() {
		currentState.update(instance);
	}

	/**
	 * Configures this class as main processing class. Use instead of
	 * constructor.
	 * 
	 * @return Single-instance of this class.
	 */
	public static GUI getInstance() {
		if (instance == null){
			main("com.github.infosimulators.gui.GUI");
		}
		return instance;
	}

	/**
	 * @return the gUIColor1
	 */
	public int getGUIColor1() {
		return GUIColor1;
	}

	/**
	 * @param gUIColor1 the gUIColor1 to set
	 */
	public void setGUIColor1(int gUIColor1) {
		GUIColor1 = gUIColor1;
	}

	/**
	 * @return the gUIColor2
	 */
	public int getGUIColor2() {
		return GUIColor2;
	}

	/**
	 * @param gUIColor2 the gUIColor2 to set
	 */
	public void setGUIColor2(int gUIColor2) {
		GUIColor2 = gUIColor2;
	}

	/**
	 * @return the gUIColor3
	 */
	public int getGUIColor3() {
		return GUIColor3;
	}

	/**
	 * @param gUIColor3 the gUIColor3 to set
	 */
	public void setGUIColor3(int gUIColor3) {
		GUIColor3 = gUIColor3;
	}
}
