package com.github.infosimulators.genetictrainer;

public class LearningCurveHelper {

	/**
	 * Gives the default values for a learning curve.
	 * 
	 * @param points
	 *            All the results of the evaluator.
	 * @return The points to be shown on the graph.
	 */
	public static float[] standard(float[] results) {
		return maxAvgMin(results);
	}

	/**
	 * Gives the maximum, average and minimum of the results (in that order).
	 * 
	 * @param points
	 *            All the results of the evaluator.
	 * @return The points to be shown on the graph.
	 */
	public static float[] maxAvgMin(float[] results) {
		float[] maxAvgMin = new float[] { results[0], results[0], results[0] };

		for (int i = 1; i < results.length; i++) {
			if (results[i] > maxAvgMin[0])
				maxAvgMin[0] = results[i];

			maxAvgMin[1] += results[i];

			if (results[i] < maxAvgMin[2])
				maxAvgMin[2] = results[i];
		}

		return maxAvgMin;
	}

}
