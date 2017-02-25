package com.github.infosimulators.events;

import java.util.ArrayList;
import java.util.List;

public class EventRegistry {

	/**
	 * The list of events that got fired.
	 */
	private static List<Event> events = new ArrayList<Event>();

	/**
	 * Adds an event to the list of fired and unhandled events.
	 * 
	 * @param event
	 *            The event to add.
	 */
	public static void fire(Event event) {
		event.setTimeFired(System.nanoTime());
		events.add(event);
	}

	/**
	 * @return A list of all fired events that have not yet been handled.
	 */
	public static List<Event> getEvents() {
		// need to create copy to avoid ConcurrentModificationException
		List<Event> lst = new ArrayList<Event>(events.size());
		lst.addAll(events);
		return lst;
	}

	/**
	 * A list of all fired events of the given type that have not yet been
	 * handled.
	 * 
	 * @param type
	 *            The Eventtype to filter
	 * @return A list of unhandled events
	 */
	public static List<Event> getEventsOfType(EventType type) {
		List<Event> es = new ArrayList<Event>();

		for (Event e : events)
			if (e.getType() == type)
				es.add(e);

		return es;
	}

	/**
	 * A list of all fired events of the given category that have not yet been
	 * handled.
	 * 
	 * @param category
	 *            The category to filter for.
	 * @return A list of unhandled events
	 */
	public static List<Event> getEventsOfCategory(EventCategory category) {
		List<Event> es = new ArrayList<Event>();

		for (Event e : events)
			if (e.getCategories().contains(category))
				es.add(e);

		return es;
	}

	/**
	 * Removes the event from the list of fired events.
	 * 
	 * @param event
	 *            The event to remove.
	 */
	protected static void remove(Event event) {
		events.remove(event);
	}

}
