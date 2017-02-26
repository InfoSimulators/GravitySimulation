package com.github.infosimulators;

import java.util.List;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.genetictrainer.Evaluator;
import com.github.infosimulators.genetictrainer.GeneticTrainer;

/**
 * Main class, handling program in general.
 */
public class Main {

	private static GeneticTrainer trainer;
	private static Evaluator evaluator;
	private static List<Event> events;

	/**
	 * Main method, called on startup.
	 * 
	 * @param args
	 *            Arguments passed.
	 */
	public static void main(String[] args) {
		// creates and runs processing sketch
		// GUI gui = GUI.getInstance();

		int numPlanets = 3;
		int paramsPerPlanet = 6;
		int genomesPerGeneration = 50;
		trainer = new GeneticTrainer(numPlanets, paramsPerPlanet, genomesPerGeneration);
		evaluator = new ExampleEvaluator();

		// TODO replace this loop with gui-functionality
		for (int i = 0; i < 3; i++)
			mainLoop();
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

	public static void handleEvents() {
		for (Event e : events)
			handleEvent(e);
	}

	public static void handleEvent(Event event) {
		System.out.println("----------------------------------");
		System.out.println("EVENT OCCURED");
		System.out.println("Type: " + event.getType().toString());
		for (String arg : event.getArgs())
			System.out.println("> " + arg);
		event.setHandled();
	}

}
