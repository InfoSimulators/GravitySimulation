package com.github.infosimulators.events;

import java.util.ArrayList;
import java.util.List;

public class Event {

	private EventType type;
	private List<EventCategory> categories;
	private String[] args;
	private boolean isHandled;
	private long timeFired;

	/**
	 * Creates a new event of type, with no arguments.
	 * 
	 * @param type
	 *            The type of this event.
	 */
	public Event(EventType type) {
		this(type, new String[0]);
	}

	/**
	 * Creates a new event of categories.
	 * 
	 * @param type
	 *            The type of this event.
	 * @param categories
	 *            The categories of this event.
	 */
	public Event(EventType type, List<EventCategory> categories) {
		this(type, categories, new String[0]);
	}

	/**
	 * Creates a new event of type with arguments.
	 * 
	 * @param type
	 *            The type of this event
	 * @param args
	 *            The arguments necessary
	 */
	public Event(EventType type, String[] args) {
		this(type, null, args);
	}

	/**
	 * Creates a new event of type, in categories and with args.
	 * 
	 * @param type
	 *            The type of this event
	 * @param categories
	 *            The categories this event is in
	 * @param args
	 *            The arguments this event needs
	 */
	public Event(EventType type, List<EventCategory> categories, String[] args) {
		this.type = type;
		if (categories == null)
			categories = new ArrayList<EventCategory>();
		this.categories = categories;
		this.args = args;

		this.isHandled = false;
	}

	/**
	 * @return The Eventtype of this event
	 */
	public EventType getType() {
		return type;
	}

	/**
	 * @return An array of arguments this event
	 */
	public String[] getArgs() {
		if (args == null)
			return new String[0];
		return args;
	}
	
	/**
	 * @return The categories this event is in.
	 */
	public List<EventCategory> getCategories() {
		return categories;
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
