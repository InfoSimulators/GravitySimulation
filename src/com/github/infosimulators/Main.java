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

		for (int i = 0; i < 20; i++)
			mainLoop();
	}

	public static void mainLoop() {
		if (trainer.isRunningSimulations()) {
			try {
				trainer.step();

				// events that have not been handled by trainer
				events = EventRegistry.getEvents();

				handleEvents();
			} catch (Exception e) {
				e.printStackTrace();
			}

			/*
			 * Rendering simulations goes here
			 */
			// trainer.getEvaluator().getSimulations()

		} else {
			// not running evaluation

			/*
			 * Rendering GUI interface goes here.
			 * 
			 * Add option to call methods as shown here (start an evaluation)
			 */

			System.out.println("Next generation: " + (trainer.getGeneration() + 1));

			if (trainer.getGeneration() == 0) {
				trainer.generateFirstGeneration();
			} else {

				float[] results = evaluator.eval(trainer.getEvalData());
				
				try {
					trainer.generateNextGeneration(results);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			try {
				trainer.startSimulations();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public static void handleEvents() {
		for (Event e : events)
			handleEvent(e);
	}

	public static void handleEvent(Event event) {
		System.out.println("Event occured:");
		System.out.println("Type: " + event.getType().toString());
		System.out.println("Message: '" + event.getArgs()[0] + "'");
	}

}
