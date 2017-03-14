package com.github.infosimulators.gui;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.Eventtype;

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
	private int GUIColor1, GUIColor2, GUIColor3;
	
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
		
		colorMode(RGB, 255, 255, 255);
		GUIColor1 = 0;//0x000000;
		GUIColor2 = 120;//0x00000f;
		GUIColor3 = 255;//0x0000ff;
		
		
		
		strokeCap(ROUND);
		strokeJoin(ROUND);
	
	}

	/**
	 * Called each frame
	 */
	public void draw() {
		currentState.update(instance);
		
		removeEvents();
	}

	public void keyReleased(){
		EventRegistry.fire(new Event(Eventtype.KEY_RELEASED, new String[]{Character.toString(key)}));
	}
	
	private void removeEvents(){
		for (Event event: EventRegistry.getEventsOfType(Eventtype.KEY_RELEASED)){
			event.setHandled();
		}
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
	 * @return the currentState
	 */
	public State getState() {
		return currentState;
	}

	/**
	 * @param State the State to set
	 */
	public void setState(State State) {
		this.currentState = State;
	}

	/**
	 * @return the GUIColor1
	 */
	public int getGUIColor1() {
		return GUIColor1;
	}

	/**
	 * @param gUIColor1 the color to set
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
	 * @param gUIColor2 the color to set
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
	 * @param gUIColor3 the color to set
	 */
	public void setGUIColor3(int gUIColor3) {
		GUIColor3 = gUIColor3;
	}
}
