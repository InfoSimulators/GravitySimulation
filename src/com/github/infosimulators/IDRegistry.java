package com.github.infosimulators;

public class IDRegistry {

	/**
	 * Counter variable to keep track of already given IDs.
	 */
	private static long current = 0;

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

	/**
	 * Superclass for all objects that should get an ID. Automatically assigns a
	 * unique ID for the object.
	 */
	public static abstract class IDd {

		/**
		 * The unique ID of the object.
		 */
		private long id;

		/**
		 * Creates a new IDd and registered object. Automatically assigns the
		 * next available ID.
		 */
		public IDd() {
			id = IDRegistry.nextID();
		}

		/**
		 * @return The unique ID of this object.
		 */
		public long getID() {
			return id;
		}

	}

}
