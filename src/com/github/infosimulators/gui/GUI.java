package com.github.infosimulators.gui;

import com.github.infosimulators.gui.states.*;

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
	private State mainMenuState;

	/**
	 * GUI Colors
	 */
	private int[] GUIColor1, GUIColor2, GUIColor3;
	
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
		colorSetup();
		
		textFont(loadFont("Helvetica-Bold-96.vlw"), 96);
		
		if(mainMenuState == null){
			System.out.println("Creation");
			if(instance == null){
				System.out.println("instance null - creation");
			}
			mainMenuState = new MainMenuState(instance);
		}
		State.setState(mainMenuState);
	}

	/**
	 * Called each frame
	 */
	public void draw() {
		State.getState().update();
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
	 * Setting values for the GUI colors
	 */
	private void colorSetup(){
		//Base color
		GUIColor1 = new int[3];
		GUIColor1[0] = 50;
		GUIColor1[1] = 50;
		GUIColor1[2] = 150;
		
		//2nd color
		GUIColor2 = new int[3];
		GUIColor2[0] = 50;
		GUIColor2[1] = 50;
		GUIColor2[2] = 50;
		
		//Accent color
		GUIColor3 = new int[3];
		GUIColor3[0] = 255;
		GUIColor3[1] = 255;
		GUIColor3[2] = 255;
	}
	
	/**
	 * Getters and setters
	 */
	
	public int[] getGUIColor1(){
		return GUIColor1;
	}
	
	public int[] getGUIColor2(){
		return GUIColor2;
	}
	
	public int[] getGUIColor3(){
		return GUIColor3;
	}
}
