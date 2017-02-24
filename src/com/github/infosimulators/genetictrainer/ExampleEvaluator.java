package com.github.infosimulators.genetictrainer;

/**
 * A very basic example of an evaluator.
 */
public class ExampleEvaluator extends Evaluator {

	/**
	 * The number of arguments there are for each planet:
	 * 
	 * <pre>
	 * - mass
	 * - volume
	 * - distance from center
	 * - angle theta from center
	 * - length of impulse
	 * - angle of impulse
	 * </pre>
	 */
	private static final int ARGS_PER_PLANET = 6;

	/**
	 * Create an evaluator to "train" with.
	 * 
	 * @param numPlanets
	 *            The number of planets to train for.
	 */
	public ExampleEvaluator(int numPlanets) {
		super(numPlanets * ARGS_PER_PLANET, false);
	}

	@Override
	public float evaluate(float[] parameters) {
		int numPlanets = parameters.length / 6;
		System.out.println("Going to evaluate system with " + numPlanets + " planets.");
		return 0;
	}

}
