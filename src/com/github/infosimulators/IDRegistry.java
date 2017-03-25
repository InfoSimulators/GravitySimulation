package com.github.infosimulators;

import java.util.ArrayList;
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
	private static ArrayList<long[]> freed = new ArrayList<long[]>();

	/**
	 * @return The next available ID
	 */
	private static long nextID() {
		return getNextID(true);
	}

	/**
	 * @return The next ID that will be given out
	 */
	public static long getNextID() {
		return getNextID(false);
	}

	/**
	 * @param delete
	 *            Whether or not to remove the ID from available ones.
	 * @return The next ID to be given out.
	 */
	private static long getNextID(boolean delete) {
		if (freed.size() > 0) {
			// give out last ID from freed space, if available

			long[] lastInterval = freed.get(freed.size() - 1);
			if (lastInterval.length == 1) { // one-ID-interval
				freed.remove(lastInterval);
				return lastInterval[0];
			}
			if (lastInterval[0] + 1 == lastInterval[1]) { // two-ID-interval
				freed.set(freed.size() - 1, new long[] { lastInterval[0] });
				return lastInterval[1];
			}
			// three-or-more-ID-interval
			freed.set(freed.size() - 1, new long[] { lastInterval[0], lastInterval[1] - 1 });
			return lastInterval[1];
		}

		// else: give out ID by current counter
		if (delete)
			return current++;
		return current + 1;
	}

	/**
	 * Sets the given ID on the list of free IDs or re-sets current to a fitting
	 * value and clears out freed if possible.
	 * 
	 * @param id
	 *            The ID to free again.
	 */
	public static void clear(long id) {
		if (freed.size() == 0) {
			freed.add(new long[] { id });
			return;
		}

		for (int i = 0; i < freed.size(); i++) {
			long[] interval = freed.get(i);
			long start = interval[0];
			long end = interval.length == 2 ? freed.get(i)[1] : start;

			if (start <= id && id <= end) // in interval
				break;
			else if (start > id + 1) { // no neighbor
				freed.add(i, new long[] { id });
				break;
			} else if (start == id + 1) { // before interval
				freed.set(i, new long[] { id, end });
				break;
			} else if (end == id - 1) { // after interval
				freed.set(i, new long[] { start, id });
				break;
			} else if (i == freed.size() - 1) { //
				freed.add(new long[] { id });
			}
		}

		mergeOverlappingIntervals();

		// if the last interval reaches to the current value, set the current
		// value to the start of the interval and remove the interval from the
		// list

		long absEnd = freed.get(freed.size() - 1).length == 2 ? freed.get(freed.size() - 1)[1]
				: freed.get(freed.size() - 1)[0];

		if (absEnd + 1 >= id) {
			current = freed.get(freed.size() - 1)[0];
			freed.remove(freed.size() - 1);
		}
	}

	private static void mergeOverlappingIntervals() {
		if (freed.size() < 2)
			return;

		List<long[]> toRemove = new ArrayList<long[]>();

		boolean keepCurr = true;
		long[] currInterval = freed.get(0);
		long[] nextInterval;

		for (int i = 0; i < freed.size() - 1; i++) {
			// -1 because the highest interval can not merge with a higher one

			if (!keepCurr) {
				currInterval = freed.get(i);
				keepCurr = false;
			}

			nextInterval = freed.get(i + 1);

			long currStart = currInterval[0];
			long currEnd = currInterval.length == 2 ? currInterval[1] : currStart;
			long nextStart = nextInterval[0];
			long nextEnd = nextInterval.length == 2 ? nextInterval[1] : nextStart;

			if (!(currEnd + 1 < nextStart)) {
				currInterval = new long[] { currStart, nextEnd };
				toRemove.add(nextInterval);
				keepCurr = true;
			}
		}

		// remove unnecessary intervals
		for (long[] interval : toRemove)
			freed.remove(interval);
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
