package com.github.infosimulators;

import java.util.Random;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;
import com.github.infosimulators.gui.GUI;
import com.github.infosimulators.gui.Listener;
import com.github.infosimulators.gui.State;
import com.github.infosimulators.gui.gelements.CheckBox;
import com.github.infosimulators.gui.gelements.GeneticPanel;
import com.github.infosimulators.gui.gelements.NumberField;
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
	}

	public static Simulation workingExample() {
		Simulation x = new Simulation(new float[][] { { 0f, (float) Math.PI, 10, 0f, 0f, 1f ,3f}, { 10f, (float) Math.PI, 10, 0f, 0f, 1f ,3f}});
		Simulation.test(x, 5);
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
				autoModeSetup();
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
				Main.getGUI().getState()
						.addElement(new CheckBox("ChangeDotsCheckBox", false, 125, 10, gui.width - 230, 40));
				Main.getGUI().getState().addListener(new Listener("ChangeDotsCheckBox", new Runnable() {

					@Override
					public void run() {
						EventRegistry
								.fire(new Event(EventType.GUI_SIMULATION_SETDOTS, new String[] { "SimulationPanel" }));
					}

				}));
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
			asteroids[i][5] = (float) (Math.random() * 80f);
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

	private static void autoModeSetup() {
		State autoModeSetupState = new State(0, 120, 255);

		String s = "This is the Training Mode! A genetic Algorithm will develop interesting and fun to watch starting situation, but first of all you can specify what kind of situation you want to be developed.";
		autoModeSetupState.addElement(new Text("AutoModeInfo", s, 18, PApplet.CENTER, 10, 70, gui.width - 30, 100));

		autoModeSetupState.addElement(
				new Text("ConfigInfo", "(The default options work just fine, if you are not sure about anything.)", 14,
						PApplet.CENTER, 10, 140, gui.width - 30, 100));

		autoModeSetupState
				.addElement(new Text("PlanetNumberText", "Number of planets:", 16, PApplet.LEFT, 20, 200, 150, 20));

		autoModeSetupState.addElement(new NumberField("PlanetNumberField", 3, 230, 188, 100, 35));

		EventRegistry.fire(new Event(EventType.GUI_NUMBERFIELD_VALUE_SET, new String[] { "PlanetNumberField", "10" }));

		autoModeSetupState
				.addElement(new Text("GenomeNumberText", "Number of genomes:", 16, PApplet.LEFT, 20, 250, 150, 20));

		autoModeSetupState.addElement(new NumberField("GenomeNumberField", 5, 230, 238, 100, 35));

		EventRegistry
				.fire(new Event(EventType.GUI_NUMBERFIELD_VALUE_SET, new String[] { "GenomeNumberField", "1000" }));

		autoModeSetupState
				.addElement(new Text("ShowSimulationText", "Show Simulation: ", 16, PApplet.LEFT, 20, 300, 150, 20));

		autoModeSetupState.addElement(new CheckBox("ShowSimulationCheckBox", true, 265, 290, 30, 30));

		autoModeSetupState.addElement(new RectButton("DoneSetupButton", "Done!", 400, 500, 180, 40));

		autoModeSetupState.addListener(new Listener("DoneSetupButton", new Runnable() {

			@Override
			public void run() {
				int planets = 10;
				int genomes = 1000;

				for (Event event : EventRegistry.getEventsOfType(EventType.GUI_NUMBERFIELD_VALUE_CHANGE)) {
					if (event.getArgs()[0] == "PlanetNumberField") {
						planets = Integer.parseInt(event.getArgs()[1]);
						event.setHandled();
					} else if (event.getArgs()[0] == "GenomeNumberField") {
						genomes = Integer.parseInt(event.getArgs()[1]);
						event.setHandled();
					}
				}

				boolean showSimulations = true;

				for (Event event : EventRegistry.getEventsOfType(EventType.GUI_CHECKBOX_VALUE_CHANGE)) {
					if (event.getArgs()[0] == "ShowSimulationCheckBox") {
						showSimulations = Boolean.parseBoolean(event.getArgs()[1]);
					}
				}

				Main.getGUI().setState(new State());
				Main.getGUI().getState().addElement(new GeneticPanel("GeneticPanel", planets, genomes, showSimulations,
						0, 0, gui.width, gui.height));
			}

		}));

		autoModeSetupState.addElement(new RectButton("MainMenuButton", "Back", gui.width - 90, 10, 80, 40));

		autoModeSetupState.addListener(new Listener("MainMenuButton", new Runnable() {

			@Override
			public void run() {
				mainMenu();
			}

		}));

		gui.setState(autoModeSetupState);
	}

	/**
	 * @return the gui
	 */
	public static GUI getGUI() {
		return gui;
	}
}
