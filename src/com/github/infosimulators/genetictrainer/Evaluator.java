package com.github.infosimulators.genetictrainer;

import java.util.List;
import java.util.Map;

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
	public float[] eval(Map<Long, List<Event>> map) {
		float[] results = new float[map.size()];

		for (int i = 0; i < results.length; i++)
			results[i] = eval(map.get(i));

		// TODO: test without / get rid of
		for (int i = 0; i < results.length; i++) {
			List<Event> events = map.get(i);
			if (null != events)
				for (Event event : events)
					event.setHandled();
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
