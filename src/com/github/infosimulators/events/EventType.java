package com.github.infosimulators.events;

public enum EventType {

	/**
	 * GeneticTrainer-Event: Fires when simulations start for a generation.
	 *
	 * args = [int generationNum]
	 */
	TRAINER_SIMU_START,
	
	/**
	 * GeneticTrainer-Event: Fires when one simulation finishes.
	 * 
	 * args = [int simulationID]
	 */
	TRAINER_SIMU_END,
	
	/**
	 * GeneticTrainer-Event: Fires when all simulations finished.
	 * 
	 * args = []
	 */
	TRAINER_SIMUS_END,

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
	GUI_NUMBERFIELD_VALUE_CHANGE,

	/**
	 * GUI-Event
	 *
	 * Sets the value of a number field.
	 *
	 * args = [String numberFieldID, int value]
	 */
	GUI_NUMBERFIELD_VALUE_SET,

	/**
	 * GUI-Event
	 *
	 * Determines the value of a text field.
	 *
	 * args = [String textFieldID, int value]
	 */
	GUI_TEXTFIELD_VALUE_CHANGE,

	/**
	 * GUI-Event
	 *
	 * Determines the value of a slider.
	 *
	 * args = [String sliderID, float value]
	 */
	GUI_SLIDER_VALUE_CHANGE,

	/**
	 * GUI-Event
	 *
	 * Sets the value of a slider.
	 *
	 * args = [String sliderID, float value]
	 */
	GUI_SLIDER_VALUE_SET,

	/**
	 * GUI-Event
	 *
	 * Calls for setup of SimulationPanel from SimulationSetupPanel
	 *
	 * args = []
	 */
	GUI_SIMULATION_START,

	/**
	 * GUI-Event
	 *
	 * Calls for the reset of a GElement
	 *
	 * args = [String ID + " - reset"]
	 */
	GUI_ELEMENT_RESET,

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
	 * args = [int simulationID, int planetID,, int runNumber]
	 */
	SIMU_PLANET_LEFT,

	/**
	 * Simulation-Event: Fired when two planets would unite into one
	 *
	 * args = [int simulationID, int planetID1, int planetID2, int runNumber]
	 */
	SIMU_PLANET_UNITE,
	/**
	 * Simulation-Event: Fired when two planets would unite into one
	 *
	 * args = [int simulationID, int planetID1, int planetID2, int runNumber]
	 */
	SIMU_PLANET_COLLISION,

	/**
	 * Simulation-Event: Fired when the Simulation ends.
	 *
	 * args = [int simulationID, int runNumber]
	 */
	SIMU_END

}
