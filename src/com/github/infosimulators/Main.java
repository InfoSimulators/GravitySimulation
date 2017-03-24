package com.github.infosimulators;

import java.util.Random;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;
import com.github.infosimulators.gui.GUI;
import com.github.infosimulators.gui.Listener;
import com.github.infosimulators.gui.State;
import com.github.infosimulators.gui.gelements.RectButton;
import com.github.infosimulators.gui.gelements.SimulationPanel;
import com.github.infosimulators.gui.gelements.SimulationSetupPanel;
import com.github.infosimulators.gui.gelements.Text;

import processing.core.PApplet;

/**
 * Main class, handling program in general.
 */
public class Main {

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

		// int numPlanets = 3;
		// int paramsPerPlanet = 6;
		// int genomesPerGeneration = 50;
		// trainer = new GeneticTrainer(numPlanets, paramsPerPlanet,
		// genomesPerGeneration);
		// evaluator = new SimplestEvaluator();

		// TODO replace this loop with gui-functionality
		for (int i = 0; i < 3; i++) {
		}
		// mainLoop();
	}

	public static Simulation workingExample() {
		Simulation x = new Simulation(
				new float[][] { { 50000f, (float) Math.PI, 2e3f, 0f, 0f, 200f }, { 0f, 0f, 2e5f, 0f, 0f, 1000f } });
		return x;
	}

	/**
	 * Setup of the main menu.
	 */
	private static void mainMenu() {
		State mainMenuState = new State(0, 120, 255);

		mainMenuState.addElement(
				new Text("GravitySimulationText", "Gravity Simulation", 50, PApplet.CENTER, 0, 60, 600, 60));

		mainMenuState.addElement(new Text("GravitySimulationInfoText1",
				"A customizable and optimizable simulation of Gravity.", 16, PApplet.CENTER, 0, 120, gui.width, 120));
		mainMenuState.addElement(new Text("GravitySimulationInfoText2", "Hover over the buttons to learn more!", 16,
				PApplet.CENTER, 0, 133, gui.width, 133));

		mainMenuState.addElement(new RectButton("ClassicModeButton", "Classic Mode", 190, 170, 220, 40));
		mainMenuState.addElement(new RectButton("RandomModeButton", "Random Mode", 190, 250, 220, 40));
		mainMenuState.addElement(new RectButton("AutoModeButton", "Training Mode", 190, 330, 220, 40));
		mainMenuState.addElement(new RectButton("LoadStartButton", "Load Start", 190, 410, 220, 40));

		mainMenuState.addListener(new Listener("ClassicModeButton - hovered", new Runnable() {

			@Override
			public void run() {
				gui.stroke(0, 255, 255);
				gui.fill(0, 200, 200);
				gui.textSize(16);
				gui.textAlign(PApplet.CENTER, PApplet.TOP);
				gui.text("Pick everything yourself!", gui.width / 2, 215);
				gui.text("You decide on the asteroids attributes.", gui.width / 2, 234);
			}

		}));

		mainMenuState.addListener(new Listener("RandomModeButton - hovered", new Runnable() {

			@Override
			public void run() {
				gui.stroke(0, 255, 255);
				gui.fill(0, 200, 200);
				gui.textSize(16);
				gui.textAlign(PApplet.CENTER, PApplet.TOP);
				gui.text("Let RNG do your work!", gui.width / 2, 295);
				gui.text("The asteroids attributes are chosen randomly.", gui.width / 2, 314);
			}

		}));

		mainMenuState.addListener(new Listener("AutoModeButton - hovered", new Runnable() {

			@Override
			public void run() {
				gui.stroke(0, 255, 255);
				gui.fill(0, 200, 200);
				gui.textSize(16);
				gui.textAlign(PApplet.CENTER, PApplet.TOP);
				gui.text("Let evolution do your work!", gui.width / 2, 375);
				gui.text("A genetic algorithm will learn about interesting starting situations.", gui.width / 2, 394);
			}

		}));

		mainMenuState.addListener(new Listener("LoadStartButton - hovered", new Runnable() {

			@Override
			public void run() {
				gui.stroke(0, 255, 255);
				gui.fill(0, 200, 200);
				gui.textSize(16);
				gui.textAlign(PApplet.CENTER, PApplet.TOP);
				gui.text("Your work was already done!", gui.width / 2, 455);
				gui.text("Load a previously saved starting situation.", gui.width / 2, 474);
			}

		}));

		mainMenuState.addListener(new Listener("ClassicModeButton", new Runnable() {

			@Override
			public void run() {
				classicMode();
			}

		}));

		mainMenuState.addListener(new Listener("RandomModeButton", new Runnable() {

			@Override
			public void run() {
				randomMode();
			}

		}));

		mainMenuState.addListener(new Listener("AutoModeButton", new Runnable() {

			@Override
			public void run() {
				autoMode();
			}

		}));

		gui.setState(mainMenuState);
	}

	private static void classicMode() {
		State classicModeState = new State(0, 120, 255);

		classicModeState.addElement(new SimulationSetupPanel("SetupPanel", 0, 60, gui.width, gui.height - 60));

		classicModeState.addElement(new RectButton("MainMenuButton", "Back", gui.width - 90, 10, 80, 40));

		classicModeState.addElement(new RectButton("FinishButton", "Finish", 10, 10, 100, 40));

		classicModeState.addListener(new Listener("MainMenuButton", new Runnable() {

			@Override
			public void run() {
				mainMenu();
			}

		}));

		classicModeState.addListener(new Listener("FinishButton", new Runnable() {

			@Override
			public void run() {
				EventRegistry.fire(new Event(EventType.GUI_SIMULATION_START));
			}

		}));

		gui.setState(classicModeState);
	}

	private static void randomMode() {
		State randomModeState = new State(0, 120, 255);

		Random ran = new Random();
		float[][] asteroids = new float[ran.nextInt(9) + 1][6];
		for (int i = 0; i < asteroids.length; i++) {
			asteroids[i][0] = ran.nextInt(1000);
			asteroids[i][1] = (float) (ran.nextInt(360) / (2 * Math.PI));
			asteroids[i][2] = (float) (Math.random() * 2e5f);
			asteroids[i][3] = ran.nextInt(15);
			asteroids[i][4] = (float) (ran.nextInt(360) / (2 * Math.PI));
			asteroids[i][5] = (float) (Math.random() * 40f);
		}

		randomModeState.addElement(
				new SimulationPanel("RandomSimulationPanel", new Simulation(asteroids), 0, 60, gui.width, gui.height));

		randomModeState.addElement(new RectButton("MainMenuButton", "Back", gui.width - 90, 10, 80, 40));

		randomModeState.addListener(new Listener("MainMenuButton", new Runnable() {

			@Override
			public void run() {
				mainMenu();
			}

		}));

		gui.setState(randomModeState);
	}

	private static void autoMode() {
		State autoModeState = new State(0, 120, 255);

		gui.setState(autoModeState);
	}

	/**
	 * @return the gui
	 */
	public static GUI getGUI() {
		return gui;
	}
}
