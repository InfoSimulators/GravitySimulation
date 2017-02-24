package com.github.infosimulators.events;

public class Event {

	private Eventtype type;
	private String[] args;
	private boolean isHandled;
	private long timeFired;

	/**
	 * Creates a new event of type, with no arguments.
	 * 
	 * @param type
	 *            The type of this event.
	 */
	public Event(Eventtype type) {
		this(type, new String[0]);
	}

	/**
	 * Creates a new event of type with arguments.
	 * 
	 * @param type
	 *            The type of this event
	 * @param args
	 *            The arguments necessary
	 */
	public Event(Eventtype type, String[] args) {
		this.type = type;
		this.args = args;

		this.isHandled = false;
	}

	/**
	 * @return The Eventtype of this event
	 */
	public Eventtype getType() {
		return type;
	}

	/**
	 * @return An array of arguments this event
	 */
	public String[] getArgs() {
		return args;
	}

	/**
	 * Sets this event as handled. It won't occur in future event-handling
	 * lists.
	 */
	public void setHandled() {
		isHandled = true;
		EventRegistry.remove(this);
	}

	/**
	 * @return Whether this event got handled already.
	 */
	public boolean isHandled() {
		return isHandled;
	}

	/**
	 * Set the time this event was fired at.
	 * 
	 * @param timeFired
	 *            The System.nanotime() this event was fired at.
	 */
	protected void setTimeFired(long timeFired) {
		this.timeFired = timeFired;
	}

	/**
	 * @return The System.nanotime() this event was fired at.
	 */
	public long getTimeFired() {
		return timeFired;
	}

}
