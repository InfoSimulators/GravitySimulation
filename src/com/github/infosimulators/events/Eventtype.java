package com.github.infosimulators.events;

public enum Eventtype {

	/**
	 * GUI-Event
	 * 
	 * Determines that a button was pressed.
	 * 
	 * args = [int buttonID]
	 */
	GUI_BUTTON_PRESSED,

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
	EVAL_PLANET_LEFT

}
