package com.github.infosimulators;

import java.util.List;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventCategory;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;
import com.github.infosimulators.genetictrainer.Evaluator;
import com.github.infosimulators.genetictrainer.GeneticTrainer;
import com.github.infosimulators.gui.*;
import com.github.infosimulators.gui.gelements.RectButton;
import com.github.infosimulators.gui.gelements.SimulationPanel;
import com.github.infosimulators.gui.gelements.SimulationSetupPanel;
import com.github.infosimulators.gui.gelements.Text;

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
			// trainer.getSimulations()

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

	public static void handleEvents() {
		for (Event e : events)
			handleEvent(e);
	}
	public static Simulation workingExample(){
		Simulation x  = new Simulation(new float[][]{{50000f,(float) Math.PI,2e3f,0f,0f,200f},{0f,0f,2e5f,0f,0f,1000f}});
		return x;
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
	* Setup of the main menu.
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

		mainMenuState.addListener(new Listener("ClassicModeButton", new Runnable(){

			@Override
			public void run() {
				classicMode();
			}

		}));

		mainMenuState.addListener(new Listener("RandomModeButton", new Runnable(){

			@Override
			public void run() {
				randomMode();
			}

		}));

		mainMenuState.addListener(new Listener("AutoModeButton", new Runnable(){

			@Override
			public void run() {
				autoMode();
			}

		}));

		gui.setState(mainMenuState);
	}

	private static void classicMode(){
		State classicModeState = new State(0, 120, 255);
		
		classicModeState.addElement(new SimulationSetupPanel("SetupPanel", 0, 60, gui.width, gui.height - 60));
		
		classicModeState.addElement(new RectButton("MainMenuButton", "Back", gui.width - 90, 10, 80, 40));
		
		classicModeState.addElement(new RectButton("FinishButton", "Finish", 10, 10, 100, 40));
		
		classicModeState.addListener(new Listener("MainMenuButton", new Runnable(){

			@Override
			public void run() {
				EventRegistry.fire(new Event(EventType.GUI_ELEMENT_RESET, new String[]{"SetupPanel"}));
				mainMenu();
			}
			
		}));
		
		classicModeState.addListener(new Listener("FinishButton", new Runnable(){

			@Override
			public void run() {
				EventRegistry.fire(new Event(EventType.GUI_SIMULATION_START));
				
			}
			
		}));

		gui.setState(classicModeState);
	}

	private static void randomMode(){
		State randomModeState = new State(0, 120, 255);
		
		randomModeState.addElement(new SimulationPanel("RandomSimulationPanel", new Simulation(new float[][]{{50000f,(float) Math.PI,2e3f,0f,0f,200f},{0f,0f,2e5f,0f,0f,1000f},{1f,200f,2e7f,0f,0f,1000f}}), 0, 60, gui.width, gui.height));
		
		randomModeState.addElement(new RectButton("MainMenuButton", "Back", gui.width - 90, 10, 80, 40));
		
		randomModeState.addListener(new Listener("MainMenuButton", new Runnable(){

			@Override
			public void run() {
				mainMenu();
			}
			
		}));
		
		gui.setState(randomModeState);
	}

	private static void autoMode(){
		State autoModeState = new State(0, 120, 255);

		gui.setState(autoModeState);
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
