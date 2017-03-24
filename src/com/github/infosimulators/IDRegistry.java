package com.github.infosimulators;

import java.util.List;

public class IDRegistry {

	/**
	 * Counter variable to keep track of already given IDs.
	 * 
	 * No ID under 1 is given, to allow free'd objects to have 0 as ID-Value.
	 */
	private static long current = 1;

	/**
	 * A list of long[]-s of IDs that have been freed again.
	 */
	private static List<long[]> freed;

	/**
	 * @return The next available ID
	 */
	private static long nextID() {
		return current++;
	}

	/**
	 * @return The next ID that will be given out
	 */
	public static long getNextID() {
		return current + 1;
	}

	/**
	 * @return The last ID that was given out to an object.
	 */
	public static long getLastID() {
		return current;
	}

	public static void clear(long id) {
		// TODO clear
	}

	/**
	 * Superclass for all objects that should get an ID. Automatically assigns a
	 * unique ID for the object.
	 */
	public static abstract class IDd {

		/**
		 * The unique ID of the object.
		 */
		private long id = 0;

		/**
		 * @return The unique ID of this object.
		 */
		public long getID() {
			if (id == 0)
				id = IDRegistry.nextID();
			return id;
		}

		/**
		 * Frees this object's ID again.
		 */
		public void clearID() {
			IDRegistry.clear(id);
			id = 0;
		}

	}

}
