package com.github.infosimulators.events;

public enum Eventtype {
	
	/**
	 * GeneticTrainer-Event: 
	 * Fires when simulations start for a generation.
	 * 
	 * args = [int generationNum]
	 */
	TRAINER_SIMU_START, 
	
	/**
	 * GeneticTrainer-Event:
	 * Fires when a generation has been generated.
	 * 
	 * args = [int generationNum]
	 */
	TRAINER_GEN_GENERATED, 
	
	/**
	 * GeneticTrainer-Event:
	 * Fires when the first generation has been generated.
	 * 
	 * args = []
	 */
	TRAINER_GENERATED_FIRST_GEN

}
