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
import com.github.infosimulators.gui.gelements.Panel;
import com.github.infosimulators.gui.gelements.RectButton;
import com.github.infosimulators.gui.gelements.Text;
import com.github.infosimulators.gui.gelements.TextField;

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
		mainMenu();

		//int numPlanets = 3;
		//int paramsPerPlanet = 6;
		//int genomesPerGeneration = 50;
		//trainer = new GeneticTrainer(numPlanets, paramsPerPlanet, genomesPerGeneration);
		//evaluator = new ExampleEvaluator();

		// TODO replace this loop with gui-functionality
		//for (int i = 0; i < 3; i++)
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
	private static void mainMenu() {
		State mainMenuState = new State(0, 120, 255);
		
		mainMenuState.addElement(new Text("GravitySimulationText", "Gravity Simulation", 50, PApplet.CENTER, 0, 60, 600, 60));
		
		mainMenuState.addElement(new RectButton("ClassicModeButton", "Classic Mode", 190, 170, 220, 40));
		mainMenuState.addElement(new RectButton("RandomModeButton", "Random Mode", 190, 250, 220, 40));
		mainMenuState.addElement(new RectButton("AutoModeButton", "Training Mode", 190, 330, 220, 40));
		mainMenuState.addElement(new RectButton("LoadStartButton", "Load Start", 190, 410, 220, 40));
		
		mainMenuState.addListener(new Listener("GravitySimulationText - hovered", new Runnable(){

			@Override
			public void run() {
				gui.stroke(0, 255, 255);
				gui.fill(0, 200, 200);
				gui.textSize(16);
				gui.textAlign(PApplet.CENTER, PApplet.TOP);
				gui.text("A customizable and optimizable simulation of Gravity.", gui.width/2, 120);
				gui.text("Hover over the buttons to learn more!", gui.width/2, 133);
			}
			
		}));
		
		mainMenuState.addListener(new Listener("ClassicModeButton - hovered", new Runnable(){

			@Override
			public void run() {
				gui.stroke(0, 255, 255);
				gui.fill(0, 200, 200);
				gui.textSize(16);
				gui.textAlign(PApplet.CENTER, PApplet.TOP);
				gui.text("Pick everything yourself!", gui.width/2, 215);
				gui.text("You decide on the asteroids attributes.", gui.width/2, 234);
			}
			
		}));
		
		mainMenuState.addListener(new Listener("RandomModeButton - hovered", new Runnable(){

			@Override
			public void run() {
				gui.stroke(0, 255, 255);
				gui.fill(0, 200, 200);
				gui.textSize(16);
				gui.textAlign(PApplet.CENTER, PApplet.TOP);
				gui.text("Let RNG do your work!", gui.width/2, 295);
				gui.text("The asteroids attributes are chosen randomly.", gui.width/2, 314);
			}
			
		}));
		
		mainMenuState.addListener(new Listener("AutoModeButton - hovered", new Runnable(){

			@Override
			public void run() {
				gui.stroke(0, 255, 255);
				gui.fill(0, 200, 200);
				gui.textSize(16);
				gui.textAlign(PApplet.CENTER, PApplet.TOP);
				gui.text("Let evolution do your work!", gui.width/2, 375);
				gui.text("A genetic algorithm will learn about interesting starting situations.", gui.width/2, 394);
			}
			
		}));
		
		mainMenuState.addListener(new Listener("LoadStartButton - hovered", new Runnable(){

			@Override
			public void run() {
				gui.stroke(0, 255, 255);
				gui.fill(0, 200, 200);
				gui.textSize(16);
				gui.textAlign(PApplet.CENTER, PApplet.TOP);
				gui.text("Your work was already done!", gui.width/2, 455);
				gui.text("Load a previously saved starting situation.", gui.width/2, 474);
			}
			
		}));
		
		
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
