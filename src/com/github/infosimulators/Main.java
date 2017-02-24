package com.github.infosimulators;

import java.util.List;

import com.github.infosimulators.genetictrainer.EvaluationEvent;
import com.github.infosimulators.genetictrainer.Evaluator;
import com.github.infosimulators.genetictrainer.ExampleEvaluator;
import com.github.infosimulators.genetictrainer.GeneticTrainer;

/**
 * Main class, handling program in general.
 */
public class Main {

	private static GeneticTrainer trainer;
	private static List<EvaluationEvent> events;

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

		Evaluator evaluator = new ExampleEvaluator(numPlanets);
		trainer = new GeneticTrainer(evaluator, 50);

		for (int i = 0; i < 20; i++)
			mainLoop();

		System.out.println("Best in Gen.  : " + trainer.getBestResult());
		System.out.println("Avg. in Gen.  : " + trainer.getAvgResult());
		System.out.println("Worst in Gen. : " + trainer.getWorstResult());
	}

	public static void mainLoop() {
		if (trainer.getEvaluator().isEvaluating()) {
			try {
				trainer.updateEvaluation();
				events = trainer.getEvents();
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
				try {
					trainer.generateNextGeneration();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			try {
				trainer.startEvaluation();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public static void handleEvents() {
		for (EvaluationEvent e : events)
			handleEvent(e);
	}

	public static void handleEvent(EvaluationEvent event) {
		System.out.println("Event occured:");
		System.out.println("Type: " + event.getType().toString());
		System.out.println("Message: '" + event.getMessage() + "'");
	}

}
