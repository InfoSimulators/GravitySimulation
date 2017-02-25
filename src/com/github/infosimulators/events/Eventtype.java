package com.github.infosimulators.events;

public enum Eventtype {

	/**
	 * GeneticTrainer-Event: Fires when simulations start for a generation.
	 * 
	 * args = [int generationNum]
	 */
	TRAINER_SIMU_START,

	/**
	 * GeneticTrainer-Event: Fires when a generation has been generated.
	 * 
	 * args = [int generationNum]
	 */
	TRAINER_GEN_GENERATED,

	/**
	 * GeneticTrainer-Event: Fires when the first generation has been generated.
	 * 
	 * args = []
	 */
	TRAINER_GENERATED_FIRST_GEN,

	/*
	 * Simulation-Events below. Make sure the simulation's id comes always
	 * first.
	 */

	/**
	 * Simulation-Event: Fired when a planet has left the system in a
	 * simulation.
	 *
	 * args = [int simulationID, int planetID]
	 */
	SIMU_PLANET_LEFT,

	/**
	 * Simulation-Event: Fired when two planets collide in a simulation.
	 *
	 * args = [int simulationID, int planetID1, int planetID2]
	 */
	SIMU_PLANET_COLLISION,

	/**
	 * Simulation-Event: Fired when two planets would unite into one
	 *
	 * args = [int simulationID, int planetID1, int planetID2]
	 */
	SIMU_PLANET_UNITE

}
