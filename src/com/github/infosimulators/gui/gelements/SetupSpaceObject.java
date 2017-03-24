package com.github.infosimulators.gui.gelements;

public class SetupSpaceObject {

	private float x, y, mass, velocity, angleVel, radius;

	private boolean active;

	public SetupSpaceObject(float x, float y, float mass, float velocity, float angleVel, float radius) {
		this.x = x;
		this.y = y;
		this.mass = mass;
		this.velocity = velocity;
		this.angleVel = angleVel;
		this.radius = radius;

		active = false;
	}

	public int getColor() {
		if (mass < 2e1f) {
			return 50;
		} else if (mass < 2e2f) {
			return 100;
		} else if (mass < 2e3f) {
			return 150;
		} else if (mass < 2e4f) {
			return 200;
		} else if (mass == 2e4f) {
			return 255;
		}
		return 0;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive() {
		active = true;
	}

	public void notActive() {
		active = false;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the mass
	 */
	public float getMass() {
		return mass;
	}

	/**
	 * @param mass
	 *            the mass to set
	 */
	public void setMass(float mass) {
		this.mass = mass;
	}

	/**
	 * @return the velocity
	 */
	public float getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity
	 *            the velocity to set
	 */
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the angleVel
	 */
	public float getAngleVel() {
		return angleVel;
	}

	/**
	 * @param angleVel
	 *            the angleVel to set
	 */
	public void setAngleVel(float angleVel) {
		this.angleVel = angleVel;
	}

	/**
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * @param radius
	 *            the radius to set
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}
}
