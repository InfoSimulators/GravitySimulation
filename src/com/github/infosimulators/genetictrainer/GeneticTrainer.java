package com.github.infosimulators.genetictrainer;

import java.util.Random;

/**
 * Trainer class for finding optimal parameters.
 */
public class GeneticTrainer {
	
	// TODO find different (?) solution
	private Random random = new Random();

	private Evaluator evaluator;
	private int genomesPerGeneration;

	private float[] results;
	private float[][] genomes;
	
	private int generationCounter;
	private boolean generationIsEvaluated;

	/**
	 * Creates a general genetic trainer that trains for the given evaluator.
	 * 
	 * @param evaluator
	 *            The evaluator to train for
	 * @param genomesPerGeneration
	 *            The number of genomes every generation should have
	 */
	public GeneticTrainer(Evaluator evaluator, int genomesPerGeneration) {
		setEvaluator(evaluator);
		this.genomesPerGeneration = genomesPerGeneration;
		
		generationCounter = 0;
		generationIsEvaluated = false;
	}
	
	public void generateFirstGeneration() {
		if (generationCounter > 0)
			System.err.println("Overwriting existing generations.");
		if (genomes != null)
			System.err.println("Genomes weren't empty when generating first generation.");
		
		genomes = new float[genomesPerGeneration][evaluator.getNumParams()];
		
		for (int i = 0; i < genomesPerGeneration; i++)
			genomes[i] = generateRandomGenome();
		
		generationCounter++;
	}
	
	public float[] generateRandomGenome() {
		float[] args = new float[evaluator.getNumParams()];
		
		for (int i = 0; i < evaluator.getNumParams(); i++)
			args[i] = random.nextFloat();
		
		return args;
	}
	
	public void generateNextGeneration() throws Exception {
		if (!generationIsEvaluated)
			throw new Exception("Evaluate generation before generating next.");

		// TODO finish gen.-generation function
		
		generationIsEvaluated = false;
		generationCounter++;
	}

	public void startEvaluation() throws Exception {
		// handle arguments
		if (genomes == null)
			throw new Exception("No genomes to evaluate");
		else if (genomes.length != genomesPerGeneration)
			throw new Exception("Wrong amount of genomes in generation.");
		else if (generationIsEvaluated)
			System.err.println("Generation has already been evaluated - doing it again.");

		results = new float[genomesPerGeneration];
		
		// TODO finish evaluation

		generationIsEvaluated = true;
	}
	
	public void updateEvaluation() throws Exception {
		// TODO update in trainer
		evaluator.updateEvaluation();
	}

	/**
	 * Sets the evaluator to use to evaluate the genomes.
	 * 
	 * @param evaluator
	 *            The evaluator to use.
	 */
	public void setEvaluator(Evaluator evaluator) {
		this.evaluator = evaluator;
	}

	/**
	 * @return The evaluator to use to evaluate all genomes with.
	 */
	public Evaluator getEvaluator() {
		return evaluator;
	}

	/**
	 * @return The number for genomes every generation should have.
	 */
	public int getGenomesPerGeneration() {
		return genomesPerGeneration;
	}

	/**
	 * @return List of all current results of the last evaluated generation of
	 *         genomes.
	 */
	public float[] getResults() {
		return results;
	}

	public float getBestResult() {
		// TODO Return best result
		return 0;
	}

	public float getAvgResult() {
		// TODO Return average result
		return 0;
	}

	public float getWorstResult() {
		// TODO Return worst result
		return 0;
	}
	
	public int getGeneration() {
		return generationCounter;
	}

}
