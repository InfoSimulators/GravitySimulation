package com.github.infosimulators.events;

public enum Eventtype {

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
	 * Determines if a button is hovered.
	 *
	 * args = [String buttonID]
	 */
	GUI_BUTTON_HOVERED,
	
	/**
	 * GUI-Event
	 *
	 * Determines the value of a number field.
	 *
	 * args = [String numberFieldID, int value]
	 */
	GUI_NUMBERFIELD_VALUE,

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

	/**
	 * Evaluation-Event
	 *
	 * Determines that all currently running evaluation-simulations have
	 * finished executing.
	 *
	 * args = [int generationCount]
	 */
	EVAL_FINISHED_ALL,

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
		 * args = [int simulationID, int planetID1,int planetID2]
		 */
	COLLISION_DETECT,
	/**
	 * UNITE-Event
	 *
	 * Fired when two planets would unite into one
	 *
	 * args = [int simulationID, int planetID1,int planetID2]
	 */
	UNITE_DETECT
}
