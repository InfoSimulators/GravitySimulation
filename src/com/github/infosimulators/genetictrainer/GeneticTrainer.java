package com.github.infosimulators.genetictrainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.infosimulators.Simulation;
import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventCategory;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;

public class GeneticTrainer {

	private int numPlanets;
	private int paramsPerPlanet;
	private int genomesPerGeneration;

	private ArrayList<float[]> learningHistory;
	private float learningGraphHeight;
	private String[] learningCurveNames;

	private boolean isRunningSimulations;
	private int generationCounter;

	private Random random = new Random();
	private float chanceOfMutation = .03f;
	private float chanceOfCompleteMutation = .04f;
	private boolean keepBest = false;

	private float[][] genomes;
	private SimuIdEvalEventsListPair[] evalEvents;
	private ArrayList<Simulation> simulations;

	/**
	 * Creates a new instance of trainer.
	 * 
	 * @param numPlanets
	 *            Number of planets
	 * @param paramsPerPlanet
	 *            Number of parameters for each planet
	 * @param genomesPerGeneration
	 *            Number of genomes per generation
	 */
	public GeneticTrainer(int numPlanets, int paramsPerPlanet, int genomesPerGeneration) {
		this.numPlanets = numPlanets;
		this.paramsPerPlanet = paramsPerPlanet;
		this.genomesPerGeneration = genomesPerGeneration;

		generationCounter = 0;
		isRunningSimulations = false;

		learningHistory = new ArrayList<float[]>();
		learningGraphHeight = 100;
		learningCurveNames = new String[] { "Top", "Avg", "Low" };
	}

	/**
	 * Generates a totally random generation of genomes.
	 */
	public void generateFirstGeneration() {
		genomes = new float[genomesPerGeneration][numPlanets * paramsPerPlanet];

		for (int i = 0; i < genomesPerGeneration; i++)
			genomes[i] = generateRandomGenome();

		generationCounter = 1;
		EventRegistry.fire(new Event(EventType.TRAINER_GENERATED_FIRST_GEN));
		EventRegistry.fire(new Event(EventType.TRAINER_GEN_GENERATED, new String[] { "1" }));
	}

	/**
	 * Generates a single random genome, uses random variable of this class.
	 * 
	 * @return The genome as float[]
	 */
	private float[] generateRandomGenome() {
		float[] genome = new float[numPlanets * paramsPerPlanet];

		for (int i = 0; i < genome.length; i++)
			genome[i] = random.nextFloat() * Float.MAX_VALUE;

		return genome;
	}

	/**
	 * The maximum height the learning graph can reach.
	 */
	public float getLearningGraphHeight() {
		return learningGraphHeight;
	}

	/**
	 * Sets the maximum value a learning graph can have. Default is 100. Does
	 * not have to be changed.
	 * 
	 * @param max
	 *            The maximum value to reach in learning process.
	 */
	public void configureLearningGraphHeight(float max) {
		learningGraphHeight = max;
	}

	/**
	 * Adds points to learning graphs at the end.
	 * 
	 * @param points
	 *            The y values of the points. x is auto-set to the next int
	 *            value after the last points on the graphs.
	 */
	public void addLearningGraphPoints(float[] points, float maxValue, boolean byCostFunction) {
		for (int i = 0; i < points.length; i++)
			points[i] = ((byCostFunction ? (maxValue - points[i]) : points[i]) / maxValue) * getLearningGraphHeight();
		learningHistory.add(points);
	}

	/**
	 * @return The values for all learning curves to display.
	 */
	public ArrayList<float[]> getLearningGraphs() {
		return learningHistory;
	}

	/**
	 * @return Names for the learning curves that can be displayed.
	 */
	public String[] getLearningCurveNames() {
		return learningCurveNames;
	}

	/**
	 * Sets names for the learning curves that can help evaluating the learning
	 * process manually.
	 * 
	 * @param names
	 *            The names for the different graphs.
	 */
	public void setLearningCurveNames(String[] names) {
		learningCurveNames = names;
	}

	/**
	 * Generates the next generation based on the results the current one got.
	 * 
	 * @param results
	 *            The fitness/cost values this generation got.
	 * @param byCostFunction
	 *            Whether the evaluator that created the results used a cost
	 *            function. False for fitness function.
	 */
	public void generateNextGeneration(float[] results, boolean byCostFunction) {
		if (results.length != genomesPerGeneration)
			System.err.println("Expected " + genomesPerGeneration + " results, received " + results.length
					+ " in generateNextGeneration().");

		// always two indices of parent-genomes
		int[][] parentIndices = new int[genomesPerGeneration][2];

		// find parent indices for each genome
		for (int i = 0; i < genomesPerGeneration; i++)
			parentIndices[i] = getRandomParents(results, byCostFunction);

		// init array for new genomes
		float[][] newGenomes = new float[genomesPerGeneration][numPlanets * paramsPerPlanet];

		// keep best genome if activated
		if (keepBest)
			newGenomes[0] = genomes[getIndexOfBest(results, byCostFunction)];
		else
			newGenomes[0] = generateGenomeFromParents(genomes[parentIndices[0][0]], genomes[parentIndices[0][1]]);

		// generate the rest of genomes
		for (int i = 1; i < genomesPerGeneration; i++)
			newGenomes[i] = generateGenomeFromParents(genomes[parentIndices[i][0]], genomes[parentIndices[i][1]]);

		genomes = newGenomes.clone();
		EventRegistry.fire(new Event(EventType.TRAINER_GEN_GENERATED, new String[] { "" + generationCounter++ }));
	}

	/**
	 * Returns the index of the best value in the given array. If cost function,
	 * the lower the better, if not cost function (fitness function), the higher
	 * the better.
	 * 
	 * @param array
	 *            The array to find the index in.
	 * @return The index of the best result.
	 */
	private int getIndexOfBest(float[] array, boolean byCostFunction) {
		int tempIndex = 0;
		float tempValue = array[0];
		for (int i = 1; i < array.length; i++) {
			if (byCostFunction && array[0] > tempValue || !byCostFunction && array[0] < tempValue) {
				tempIndex = i;
				tempValue = array[0];
			}
		}
		return tempIndex;
	}

	/**
	 * Generates a new genomes based off the given parents and mutates it.
	 * 
	 * @param parent1
	 *            The first parent
	 * @param parent2
	 *            The second parent
	 * @return The newly generated genome
	 */
	private float[] generateGenomeFromParents(float[] parent1, float[] parent2) {
		int crossovers = random.nextInt(parent1.length / 4);
		int point = random.nextInt(parent1.length);

		float[] genome = crossover(parent1, parent2, point);

		for (int i = 1; i < crossovers; i++) {
			point = random.nextInt(genome.length);
			genome = crossover(genome, i % 2 == 1 ? parent1 : parent2, point);
		}

		mutate(genome);
		return genome;
	}

	/**
	 * Combines two genomes by taking the first part of the first genome and the
	 * second part of the second genome, split at the given point.
	 * 
	 * @param genome1
	 *            The beginning genome
	 * @param genome2
	 *            The end genome
	 * @param point
	 *            The point to cross over at
	 * @return The new genome that is being created
	 */
	private static float[] crossover(float[] genome1, float[] genome2, int point) {
		float[] genome = new float[genome1.length];

		for (int i = 0; i < genome.length; i++)
			genome[i] = i < point ? genome1[i] : genome2[i];

		return genome;
	}

	/**
	 * Mutates a genome based on the trainer's chances for muation.
	 * 
	 * @param genome
	 *            The genome to mutate
	 * @return The mutated genome
	 */
	private float[] mutate(float[] genome) {
		for (int i = 0; i < genome.length; i++) {
			if (random.nextFloat() < chanceOfMutation) {
				/*
				 * Option 1: switch value to completely random
				 * 
				 * Option 2: add/sub from value
				 */

				if (random.nextFloat() < chanceOfCompleteMutation) {
					genome[i] = getRandomValue();
				} else {
					genome[i] = genome[i] + random.nextFloat() - .5f;
					if (genome[i] < 0)
						genome[i] = 0;
				}
			}
		}

		return genome;
	}

	/**
	 * Returns a 2-long array of random ints in range [0;results.length[ based
	 * on the results each index of genomes got in the last generation.
	 * 
	 * @param results
	 * @param byCostFunction
	 * @return Two weighted-random ints as indices.
	 */
	private int[] getRandomParents(float[] results, boolean byCostFunction) {
		return new int[] { getRandomValuedIndex(results, byCostFunction),
				getRandomValuedIndex(results, byCostFunction) };
	}

	/**
	 * Returns one random int based on likelihoods specified in results.
	 * 
	 * @param results
	 *            The results of the generation to generated weight-based
	 *            indices for.
	 * @param byCostFunction
	 *            Whether a cost function was used to generate the results.
	 *            False for fitness function.
	 * @return A weighted-random int as index.
	 */
	private int getRandomValuedIndex(float[] results, boolean byCostFunction) {
		if (byCostFunction) {
			for (int i = 0; i < results.length; i++) {
				if (results[i] == 0)
					results[i] = Float.MAX_VALUE;
				else
					results[i] = 1f / results[i];
			}
		}

		results = normalizeWeights(results);

		float r = random.nextFloat();
		float sum = 0;
		int index = 0;
		while (sum < r) {
			sum += results[index];
			index++;
		}
		index--;

		return index;
	}

	/**
	 * Normalizes weights, meaning calculating their percentage of a full count.
	 * After this, the ratio between all elements is the same, but they add up
	 * to 1.
	 * 
	 * If at least one weight is negative an error is shown, because that can
	 * not be handled.
	 * 
	 * @param weights
	 *            The weights to normalize.
	 * @return A normalized array of floats with a total sum of 1.0 and the same
	 *         ratio as the starting array.
	 */
	private static float[] normalizeWeights(float[] weights) {
		float total = 0;
		float[] normalized = new float[weights.length];

		for (int i = 0; i < weights.length; i++) {
			if (weights[i] < 0)
				System.err.println("Cannot handle negative weights.");

			total += weights[i];
		}

		if (total == 0) {
			/*
			 * All values are 0
			 * 
			 * This is a special case - all weights will be the same, but they
			 * will not stay 0, which would be the usual behavior of a weight
			 * equal to 0.
			 */
			for (int i = 0; i < weights.length; i++)
				normalized[i] = 1.0f / weights.length;
			return normalized;
		}

		for (int i = 0; i < weights.length; i++)
			normalized[i] = weights[i] / total;

		return normalized;
	}

	/**
	 * @return Whether simulations are running.
	 */
	public boolean isRunningSimulations() {
		return isRunningSimulations;
	}

	/**
	 * Sets the state for simulations to be running. Initializes Simulations.
	 */
	public void startSimulations() {
		simulations = new ArrayList<Simulation>(genomesPerGeneration);

		for (float[] genome : genomes) {
			float[][] simuParams = new float[genome.length / paramsPerPlanet][paramsPerPlanet];

			for (int i = 0; i < genome.length; i++) {
				simuParams[i / 6][i % 6] = genome[i];
			}

			simulations.add(new Simulation(simuParams));
		}

		// ----------------------------------------------------------------
		// reset evalEvents
		evalEvents = new SimuIdEvalEventsListPair[genomesPerGeneration];

		for (int i = 0; i < evalEvents.length; i++) {
			evalEvents[i] = new SimuIdEvalEventsListPair();
			evalEvents[i].simuID = simulations.get(i).getID();
			evalEvents[i].events = new ArrayList<Event>();
		}
		// ----------------------------------------------------------------

		isRunningSimulations = true;

		EventRegistry.fire(new Event(EventType.TRAINER_SIMU_START, new String[] { "" + generationCounter }) {
		});
	}

	/**
	 * Updates all running simulations and sorts and interprets some events.
	 */
	public void step() {
		for (Simulation simulation : simulations)
			simulation.update();

		List<Event> simuEvents = EventRegistry.getEventsOfCategory(EventCategory.SIMULATION);

		for (Event event : simuEvents) {
			if (event.getType() == EventType.SIMU_END) {
				long simuID = Long.parseLong(event.getArgs()[0]);
				simulations.remove(getSimulationById(simuID));

				List<EventCategory> categories = new ArrayList<EventCategory>(1);
				categories.add(EventCategory.GENETIC_TRAINER);
				EventRegistry
						.fire(new Event(EventType.TRAINER_SIMU_END, categories, new String[] { event.getArgs()[0] }));
			}
		}

		if (simulations.size() == 0) {
			List<EventCategory> categories = new ArrayList<EventCategory>(1);
			categories.add(EventCategory.GENETIC_TRAINER);
			EventRegistry.fire(new Event(EventType.TRAINER_SIMUS_END, categories));
		}

		for (Event event : simuEvents) {
			long simuID = Long.parseLong(event.getArgs()[0]);

			if (getSimulationById(simuID) != null) {
				int pairIndex = findIndexOfPairWithSimuId(evalEvents, simuID);
				evalEvents[pairIndex].events.add(event);
			}
		}
	}

	/**
	 * @return A list of lists of events interpretable by the evaluator, sorted
	 *         by genome.
	 */
	public ArrayList<List<Event>> getEvalEvents() {
		ArrayList<List<Event>> events = new ArrayList<List<Event>>(evalEvents.length);

		for (int i = 0; i < evalEvents.length; i++)
			events.add(evalEvents[i].events);

		return events;
	}

	/**
	 * @return A random value for a parameter.
	 */
	private float getRandomValue() {
		return random.nextFloat();
	}

	/**
	 * @return A list of all running simulations.
	 */
	public ArrayList<Simulation> getSimulations() {
		return simulations;
	}

	/**
	 * Clear IDs of current simulations for RAM.
	 */
	public void clearSimulations() {
		for (Simulation simulation : simulations)
			simulation.clearID();
	}

	/**
	 * Returns the simulation of the given ID, if found in simulations.
	 * 
	 * @param simulationID
	 *            The ID of the simulation to be found.
	 * @return The simulation with the given ID or null if it wasn't found.
	 */
	private Simulation getSimulationById(long simulationID) {
		for (Simulation s : simulations)
			if (s.getID() == simulationID)
				return s;
		return null;
	}

	/**
	 * @return Current generation number
	 */
	public int getGeneration() {
		return generationCounter;
	}

	/**
	 * @return The chance that a genome mutates at all
	 */
	public float getChanceOfMutation() {
		return chanceOfMutation;
	}

	/**
	 * @param chanceOfMutation
	 *            The chance that a genome mutates at all
	 */
	public void setChanceOfMutation(float chanceOfMutation) {
		this.chanceOfMutation = chanceOfMutation;
	}

	/**
	 * @return The chance that, if the genome will mutate, it will have a
	 *         completely random new value at the mutated genome instead of a
	 *         slight change
	 */
	public float getChanceOfCompleteMutation() {
		return chanceOfCompleteMutation;
	}

	/**
	 * @param chanceOfCompleteMutation
	 *            The chance that, if the genome will mutate, it will have a
	 *            completely random new value at the mutated genome instead of a
	 *            slight change
	 */
	public void setChanceOfCompleteMutation(float chanceOfCompleteMutation) {
		this.chanceOfCompleteMutation = chanceOfCompleteMutation;
	}

	/**
	 * @return Whether the best performing genome will be kept for the next
	 *         generation.
	 */
	public boolean keepsBest() {
		return keepBest;
	}

	/**
	 * Enables keeping the best performing genome for the next generation.
	 */
	public void enableKeepBest() {
		setKeepBest(true);
	}

	/**
	 * Disables keeping the best performing genome for the next generation.
	 */
	public void disableKeepBest() {
		setKeepBest(false);
	}

	/**
	 * Toggles the value of whether the best performing genome should be kept
	 * for the next generation.
	 */
	public void toggleKeepBest() {
		setKeepBest(!keepsBest());
	}

	/**
	 * Sets whether the best performing genome will be kept for the next
	 * generation.
	 * 
	 * @param keepBest
	 *            True: the best performing genome will be kept in the next
	 *            generation; False: the best performing genome will not be kept
	 *            in the next generation, but it might by chance.
	 */
	public void setKeepBest(boolean keepBest) {
		this.keepBest = keepBest;
	}

	/**
	 * Helper class for putting pairs of simulation IDs and evaluation events
	 * into an array.
	 */
	public class SimuIdEvalEventsListPair {
		long simuID;
		List<Event> events;
	}

	/**
	 * Finds the index of the pair with the given simulation id in an array of
	 * pairs
	 * 
	 * @param pairs
	 *            The array of pairs to search in
	 * @param id
	 *            The simulation id to search for
	 * @return The index of the pair in the array, or -1 if it could not be
	 *         found.
	 */
	public static int findIndexOfPairWithSimuId(SimuIdEvalEventsListPair[] pairs, long id) {
		for (int i = 0; i < pairs.length; i++) {
			if (pairs[i].simuID == id)
				return i;
		}
		return -1;
	}

}
