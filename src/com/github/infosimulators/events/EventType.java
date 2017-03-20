package com.github.infosimulators.events;

public enum EventType {

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

	// ----------------------------------------------------------------------

	/**
	* GUI-Event
	*
	* Determines that a button was pressed.
	*
	* args = [String buttonID]
	*/
	GUI_BUTTON_PRESSED,

	/**
	 * GUI-Event
	 *
	 * Determines if a GElement is hovered.
	 *
	 * args = [String ID + " - hovered"]
	 */
	GUI_ELEMENT_HOVERED,

	/**
	 * GUI-Event
	 *
	 * Determines the value of a number field.
	 *
	 * args = [String numberFieldID, int value]
	 */
	GUI_NUMBERFIELD_VALUE,

	/**
	 * GUI-Event
	 *
	 * Determines the value of a text field.
	 *
	 * args = [String textFieldID, String value]
	 */
	GUI_TEXTFIELD_VALUE,

	// ----------------------------------------------------------------------

	/**
	 * Key-Event
	 *
	 * Determines that a key has been released
	 *
	 * args = [char key]
	 */
	KEY_RELEASED,

	// ----------------------------------------------------------------------

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
	SIMU_PLANET_OVERLAP,

	/**
	 * Simulation-Event: Fired when two planets would unite into one
	 *
	 * args = [int simulationID, int planetID1, int planetID2]
	 */
	SIMU_PLANET_UNITE,
	/**
	 * Simulation-Event: Fired when two planets would unite into one
	 *
	 * args = [int simulationID, int planetID1, int planetID2]
	 */
	SIMU_PLANET_COLLISION,

	/**
	 * Simulation-Event: Fired when the Simulation ends.
	 *
	 * args = [int simulationID]
	 */
	SIMU_PLANET_END

}
