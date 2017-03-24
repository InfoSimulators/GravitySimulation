package com.github.infosimulators.gui;

/**
 * Simple class matching an ID (activation name) and a Runnable.
 *
 */
public class Listener {

	private String ID;
	private Runnable action;

	public Listener(String ID, Runnable action) {
		this.ID = ID;
		this.action = action;
	}

	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * @return the action
	 */
	public Runnable getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(Runnable action) {
		this.action = action;
	}

}