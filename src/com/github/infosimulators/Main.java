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

	public static Evaluator evaluator;
	public static GeneticTrainer trainer;

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

		evaluator = new ExampleEvaluator(numPlanets);
		trainer = new GeneticTrainer(evaluator, 50);

		for (int i = 0; i < 20; i++)
			mainLoop();

		System.out.println("Best in Gen.  : " + trainer.getBestResult());
		System.out.println("Avg. in Gen.  : " + trainer.getAvgResult());
		System.out.println("Worst in Gen. : " + trainer.getWorstResult());
	}

	public static void mainLoop() {
		if (evaluator.isRunningEvaluation()) {
			try {
				trainer.updateEvaluation();
				List<EvaluationEvent> events = trainer.getEvents();
				handleEvents(events);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// not running evaluation
			
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

	public static void handleEvents(List<EvaluationEvent> events) {
		for (EvaluationEvent e : events)
			handleEvent(e);
	}

	public static void handleEvent(EvaluationEvent event) {
		System.out.println("Event occured:");
		System.out.println("Type: " + event.getType().toString());
		System.out.println("Message: '" + event.getMessage() + "'");
	}

}
