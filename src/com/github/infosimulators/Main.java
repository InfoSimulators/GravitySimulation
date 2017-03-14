package com.github.infosimulators;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.Eventtype;
import com.github.infosimulators.genetictrainer.Evaluator;
import com.github.infosimulators.genetictrainer.GeneticTrainer;
import com.github.infosimulators.genetictrainer.ParameterTypes;
import com.github.infosimulators.gui.GUI;
import com.github.infosimulators.gui.Listener;
import com.github.infosimulators.gui.State;
import com.github.infosimulators.gui.gelements.NumberField;
import com.github.infosimulators.gui.gelements.RectButton;

import processing.core.PApplet;


/**
 * Main class, handling program in general.
 */
public class Main {

	public float x;
	public float y;
	
	private static GUI gui;

	/**
	 * Main method, called on startup.
	 * 
	 * @param args
	 *            Arguments passed.
	 */
	public static void main(String[] args) {
		// creates and runs processing sketch
		gui = GUI.getInstance();
		
		functionalityTest();
		

		ParameterTypes[] format = new ParameterTypes[] { ParameterTypes.BOOLEAN, ParameterTypes.INT,
				ParameterTypes.DOUBLE };
		Evaluator evaluator = new Evaluator(format) {
		};
		GeneticTrainer trainer = new GeneticTrainer(evaluator, 50);

		for (int i = 0; i < 10; i++) {
			trainer.evaluateCurrent();
			trainer.generateNextGeneration();
		}

	}
	
	
	/**
	 * Test code to show the basic functionality of the GUI.
	 */
	private static void functionalityTest(){
		State mainMenuState = new State(0, 120, 255);
		mainMenuState.addElement(new RectButton("TestButton", 40, 40, 100, 100));
		mainMenuState.addListener(new Listener("TestButton", new Runnable(){

			@Override
			public void run() {
				for (Event event : EventRegistry.getEventsOfType(Eventtype.GUI_NUMBERFIELD_VALUE)){
					if (event.getArgs()[0] == "TestCounter"){
						System.out.println(event.getArgs()[1]);
					}
				}
				
			}
			
		}));
		mainMenuState.addListener(new Listener("TestButton - hovered", new Runnable(){

			@Override
			public void run() {
				//System.out.println("TestButton hovered");
				gui.textSize(12);
				gui.fill(gui.getGUIColor3());
				gui.textAlign(PApplet.LEFT);
				gui.text("Info: This is a test, so lets test you tester", gui.mouseX, gui.mouseY);
			}
			
		}));
		mainMenuState.addElement(new NumberField("TestCounter", 6, 40, 150, 110, 30));
		
		gui.setState(mainMenuState);
	}

	/**
	 * @return the gui
	 */
	public static GUI getGUI() {
		return gui;
	}

	/**
	 * @param gui the gui to set
	 */
	public static void setGUI(GUI gui) {
		Main.gui = gui;
	}
}