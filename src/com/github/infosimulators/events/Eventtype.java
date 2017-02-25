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
	TRAINER_GENERATED_FIRST_GEN,

	/**
	 * Evaluation-Event
	 *
	 * Determines that a planet has left in a simulation of an evaluation.
	 *
	 * args = [int simulationID, int planetID]
	 */
	EVAL_PLANET_LEFT,
	
	/**
	 * COLLISION-Event
	 *
	 * Fired when two planets collide
	 *
	 * args = [int simulationID, int planetID1, int planetID2]
	 */
	COLLISION_DETECT,
	
	/**
	 * UNITE-Event
	 *
	 * Fired when two planets would unite into one
	 *
	 * args = [int simulationID, int planetID1, int planetID2]
	 */
	UNITE_DETECT
	
}
