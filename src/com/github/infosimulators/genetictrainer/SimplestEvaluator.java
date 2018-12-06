package com.github.infosimulators.genetictrainer;

import java.util.List;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;

public class SimplestEvaluator extends Evaluator {

	@Override
	protected float eval(List<Event> events) {
		if (events.isEmpty())
			return .5f;

		float p = 0;

		for (Event event : events) {
			switch (event.getType()) {
			case SIMU_PLANET_LEFT:
				p += 5;
				break;
			case SIMU_PLANET_COLLISION:
				p += 1;
				break;
			case SIMU_PLANET_UNITE:
				p += 3;
				break;
			case SIMU_END:
				p += 20;
				break;
			}
		}

		return p;
	}

	@Override
	public boolean isCostFunction() {
		return true;
	}
	
	@Override
	public float maxValue() {
		return 50;
	}

	public static void main(String[] args) {
		int generations = 1000;
		int maxSteps = 100;
		boolean doPrint = true;

		GeneticTrainer trainer = new GeneticTrainer(5, 6, 1000);
		Evaluator evaluator = new SimplestEvaluator();

		trainer.generateFirstGeneration();

		for (int i = 0; i < generations; i++) {
			if (doPrint)
				System.out.println((i + 1) + ": ----------");
			trainer.startSimulations();

			boolean stepsDone = false;
			int steps = 0;

			while (!stepsDone) {
				trainer.step();

				List<Event> events = EventRegistry.getEventsOfType(EventType.TRAINER_SIMUS_END);
				if (events.size() > 0) {
					stepsDone = true;
				}
				for (Event event : events)
					event.setHandled();

				if (++steps > maxSteps) {
					if (doPrint)
						System.out.println("Too many steps");
					stepsDone = true;
				}
				
				// for RAM clearing
				handleAllEvents();
			}

			float[] results = evaluator.eval(trainer.getEvalEvents());

			if (doPrint)
				printResults(results);

			trainer.clearSimulations();
			trainer.generateNextGeneration(results, evaluator.isCostFunction());
		}
	}

	private static void handleAllEvents() {
		for (Event event : EventRegistry.getEvents())
			event.setHandled();
	}

	private static void printResults(float[] results) {
		float min = Float.MAX_VALUE;
		float max = 0;
		float avg = 0;
		for (float f : results) {
			avg += f;
			min = f < min ? f : min;
			max = f > max ? f : max;
		}
		avg = avg / results.length;
		System.out.println("len: " + results.length);
		System.out.println("min: " + min);
		System.out.println("avg: " + avg);
		System.out.println("max: " + max);
	}

}
