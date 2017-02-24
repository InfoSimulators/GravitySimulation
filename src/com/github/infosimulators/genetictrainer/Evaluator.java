package com.github.infosimulators.genetictrainer;

import java.util.List;

import com.github.infosimulators.Simulation;

/**
 * Evaluator class
 */
public abstract class Evaluator {

	private int numParams;
	private boolean isCostFunction;
	private List<Simulation> simulations;
	private List<EvaluationEvent> events;
	private boolean isRunningEvaluation;

	/**
	 * Creates an evaluator that evaluates based on a cost function.
	 * 
	 * @param format
	 *            The data format
	 */
	public Evaluator(int numParams) {
		this(numParams, true);
	}

	/**
	 * Creates an evaluator.
	 * 
	 * @param numParams
	 *            The number of parameters that will be passed for each genome.
	 * @param isCostFunction
	 *            Whether this evaluator works based on a cost function or not.
	 *            True for cost function, false for fitness function.
	 */
	public Evaluator(int numParams, boolean isCostFunction) {
		this.numParams = numParams;
		this.isCostFunction = isCostFunction;

		isRunningEvaluation = false;
	}

	/**
	 * Evaluates all given genomes with this evaluators evaluation-function.
	 * 
	 * @param genomes
	 *            The genomes to evaluate.
	 * @return The results of the evaluation-function for each genome.
	 */
	public void startEvaluateAll(float[][] genomes) {
		// TODO create all simulations necessary
		isRunningEvaluation = true;
	}

	/**
	 * Calculates next simulation step for all simulations and adds events to
	 * event-list.
	 * 
	 * @throws Exception
	 *             If no evaluation is currently running.
	 */
	public void updateEvaluation() throws Exception {
		if (!isRunningEvaluation)
			throw new Exception("No evaluation is running.");
		// TODO updateEvaluation
	}

	/**
	 * Evaluation function. This function has to be a cost- or fitness-function
	 * based on what the object was configured as.
	 * 
	 * @param parameters
	 *            The parameters to evaluate
	 * @return The cost/fitness for this genome
	 */
	public abstract float evaluate(float[] parameters);

	/**
	 * @return The number of parameters that will be passed for each genome to
	 *         evaluate.
	 */
	public int getNumParams() {
		return numParams;
	}

	/**
	 * @return A list of all currently running simulations to evaluate the
	 *         different genomes.
	 */
	public List<Simulation> getSimulations() {
		return simulations;
	}

	/**
	 * @return A list of all events that were thrown since the last time this
	 *         method was called.
	 */
	public List<EvaluationEvent> getEvents() {
		return events;
	}

	/**
	 * A cost function is a function that returns a higher value the worse the
	 * genome that is evaluated is. The ideal value is 0.
	 * 
	 * @return Whether or not this evaluator evaluates based on a cost function.
	 */
	public boolean isCostFunction() {
		return isCostFunction;
	}

	/**
	 * A fitness function is a function that returns a higher value the better
	 * the evaluated genome performed. The worst value is 0.
	 * 
	 * @return Whether or not this evaluator evaluates based on a fitness
	 *         function.
	 */
	public boolean isFitnessFunction() {
		return !isCostFunction;
	}

	/**
	 * @return Whether an evaluation is currently running.
	 */
	public boolean isRunningEvaluation() {
		return isRunningEvaluation;
	}

}
