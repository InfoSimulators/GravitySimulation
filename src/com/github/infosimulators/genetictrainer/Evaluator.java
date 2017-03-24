package com.github.infosimulators.genetictrainer;

import java.util.ArrayList;
import java.util.List;

import com.github.infosimulators.events.Event;

public abstract class Evaluator {

	/**
	 * Evaluates the given data.
	 * 
	 * @param map
	 *            The ArrayList of Lists of events that were fired in each
	 *            genome's simulation
	 * @return The sorted cost/fitness determined by this evaluator.
	 */
	public float[] eval(ArrayList<List<Event>> pairs) {
		float[] results = new float[pairs.size()];

		for (int i = 0; i < results.length; i++)
			results[i] = eval(pairs.get(i));

		return results;
	}

	/**
	 * Calculates the cost/fitness for one specific genome by all events that
	 * affected the simulation it caused.
	 * 
	 * @param events
	 *            All events that appeared in this genome's simulation.
	 * @return The cost/fitness of this genome.
	 */
	protected abstract float eval(List<Event> events);

	/**
	 * @return Whether this evaluator is based on a cost function, meaning 0 is
	 *         ideal and the higher the outcome the worse.
	 */
	public abstract boolean isCostFunction();

	/**
	 * @return Whether this evaluator is based on a fitness function, meaning 0
	 *         is the worst outcome, but the higher the outcome the better.
	 */
	public boolean isFitnessFunction() {
		return !isCostFunction();
	}

}
