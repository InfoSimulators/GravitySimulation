package com.github.infosimulators.genetictrainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;

public class SimplestEvaluator extends Evaluator {

	@Override
	protected float eval(List<Event> events) {
		if (events == null)
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
			case SIMU_PLANET_END:
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

	public static void main(String[] args) {
		GeneticTrainer trainer = new GeneticTrainer(3, 6, 100);
		Evaluator evaluator = new SimplestEvaluator();

		trainer.generateFirstGeneration();
		trainer.generateFirstGeneration();

		boolean stop = false;

		while (!stop) {
			trainer.startSimulations();
			System.out.println("Started Simulations");

			boolean stepsDone = false;
			int steps = 0;
			
			while (!stepsDone) {
				System.out.println("Step...");
				trainer.step();

				List<Event> events = EventRegistry.getEventsOfType(EventType.TRAINER_SIMUS_END);
				if (events.size() > 0) {
					stepsDone = true;
					System.out.println("All simus ended.");
				}
				for (Event event : events)
					event.setHandled();
				
				if (++steps > 0) {
					System.out.println("Too many steps");
					stepsDone = true;
				}
			}

			float[] results = evaluator.eval(trainer.getEvalEvents());
			System.out.println("Got results");

			printResults(results);

			try {
				trainer.generateNextGeneration(results, evaluator.isCostFunction());
				System.out.println("Generated next gen");
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Done with one generation.");

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				String s = br.readLine();
				if (s == "e")
					stop = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
		System.out.println("min: " + min);
		System.out.println("avg: " + avg);
		System.out.println("max: " + max);
	}

}
