package com.github.infosimulators.genetictrainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.Eventtype;

public class GeneticTrainer {

	private int numPlanets;
	private int paramsPerPlanet;
	private int genomesPerGeneration;

	private boolean isRunningSimulations;
	private int generationCounter;
	
	private Map<float[], List<Event>> evalData;

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
	}

	public void generateFirstGeneration() {
		// TODO generate first generation
	}

	public void generateNextGeneration(float[] results) {
		// TODO generate next generation
	}

	public boolean isRunningSimulations() {
		return isRunningSimulations;
	}

	public void step() {
		// TODO simulation step
		// TODO interpret events for evalData
	}

	public int getGeneration() {
		return generationCounter;
	}

	public void startSimulations() {
		isRunningSimulations = true;
		
		evalData = new HashMap<float[], List<Event>>();
		
		EventRegistry.fire(new Event(Eventtype.TRAIN_SIMU_START, 
				new String[] { "" + generationCounter }) {
		});
	}

	public Map<float[], List<Event>> getEvalData() {
		return evalData;
	}

}
