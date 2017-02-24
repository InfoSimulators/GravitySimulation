package com.github.infosimulators.genetictrainer;

/**
 * Simple event class needed for communication between Evaluator and main
 * game loop.
 */
public class EvaluationEvent {
	
	public enum Eventtype {
		FINISHED_ALL, FINISHED_ONE, PLANET_LEFT
	}

	private Eventtype type;
	private String message;

	public EvaluationEvent(Eventtype type, String message) {
		this.type = type;
		setMessage(message);
	}

	public Eventtype getType() {
		return type;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}