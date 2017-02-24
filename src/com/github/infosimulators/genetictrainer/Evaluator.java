package com.github.infosimulators.genetictrainer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.infosimulators.events.Event;

public abstract class Evaluator {
	
	/**
	 * Evaluates the given data.
	 * 
	 * @param data
	 *            Data's key has the index of the item as first element.
	 * @return The sorted cost/fitness determined by this evaluator.
	 */
	public float[] eval(Map<float[], List<Event>> data) {
		float[] results = new float[data.size()];

		Iterator<Entry<float[], List<Event>>> it = data.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<float[], List<Event>> pair = it.next();
			results[(int) pair.getKey()[0]] = eval(pair.getValue());
			it.remove();
		}

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
