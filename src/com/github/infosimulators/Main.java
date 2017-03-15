package com.github.infosimulators;

import java.util.List;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventCategory;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;
import com.github.infosimulators.genetictrainer.Evaluator;
import com.github.infosimulators.genetictrainer.GeneticTrainer;
import com.github.infosimulators.gui.*;
import com.github.infosimulators.gui.gelements.NumberField;
import com.github.infosimulators.gui.gelements.RectButton;

import processing.core.PApplet;

/**
 * Main class, handling program in general.
 */
public class Main {

	private static GeneticTrainer trainer;
	private static Evaluator evaluator;
	private static List<Event> events;
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

		int numPlanets = 3;
		int paramsPerPlanet = 6;
		int genomesPerGeneration = 50;
		trainer = new GeneticTrainer(numPlanets, paramsPerPlanet, genomesPerGeneration);
		evaluator = new ExampleEvaluator();

		// TODO replace this loop with gui-functionality
		for (int i = 0; i < 3; i++){}
			//mainLoop();
			
	}

	public static void mainLoop() {
		if (trainer.isRunningSimulations()) {
			try {
				trainer.step();
			} catch (Exception e) {
				e.printStackTrace();
			}

			/*
			 * Rendering simulations goes here
			 */
			// trainer.getEvaluator().getSimulations()

		} else {
			// not running simulation

			/*
			 * Rendering GUI interface goes here.
			 *
			 * Add option to call methods as shown here (start an evaluation)
			 */

			if (trainer.getGeneration() == 0) {
				trainer.generateFirstGeneration();
			} else {

				float[] results = evaluator.eval(trainer.getEvalEvents());

				try {
					trainer.generateNextGeneration(results, evaluator.isCostFunction());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}


			trainer.startSimulations();

		}

		// output events
		events = EventRegistry.getEvents();
		handleEvents();
	}
	/**
	* Test code to show the basic functionality of the GUI.
	*/
	private static void functionalityTest() {
		State mainMenuState = new State(0, 120, 255);
		mainMenuState.addElement(new RectButton("TestButton", 40, 40, 100, 100));
		mainMenuState.addListener(new Listener("TestButton", new Runnable() {

			@Override
			public void run() {
				for (Event event : EventRegistry.getEventsOfType(EventType.GUI_NUMBERFIELD_VALUE)) {
					if (event.getArgs()[0] == "TestCounter") {
						System.out.println(event.getArgs()[1]);
					}
				}

			}

		}));
		mainMenuState.addListener(new Listener("TestButton - hovered", new Runnable() {

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
	public static void handleEvents() {
		for (Event e : events)
			handleEvent(e);
	}

	public static void handleEvent(Event event) {
		System.out.println("----------------------------------");
		System.out.println("EVENT OCCURED");
		System.out.println("Type: " + event.getType().toString());
		if (event.getCategories() != null && !event.getCategories().isEmpty()) {
			System.out.println("Categories:");
			for (EventCategory cat : event.getCategories())
				System.out.println("> " + cat.toString());
		}
		if (event.getArgs() != null && event.getArgs().length != 0) {
			System.out.println("Args:");
			for (String arg : event.getArgs())
				System.out.println("> " + arg);
		}
		event.setHandled();
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
