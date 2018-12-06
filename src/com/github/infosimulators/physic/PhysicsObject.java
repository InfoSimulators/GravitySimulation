package com.github.infosimulators.physic;

import java.util.ArrayList;

import com.github.infosimulators.IDRegistry.IDd;

/**
 * Superclass for all objects, manipulated by physics. It stores position,
 * forces, acceleration, velocity, mass and size. It is assumed to be a sphere
 * with the radius size with the mass located in the middle.
 */
public class PhysicsObject extends IDd {

	/**
	 * The position of the object in a Cartesian coordinate system represented
	 * as a {@link Vector2}
	 */
	protected Vector2 position;

	private ArrayList<Vector2> pastPositions = new ArrayList<Vector2>();

	/**
	 * The velocity of the object in a Cartesian coordinate system represented
	 * as a {@link Vector2}
	 */
	public Vector2 velocity;

	/**
	 * The acceleration of the object in a Cartesian coordinate system
	 * represented as a {@link Vector2}
	 */
	public Vector2 acceleration = Vector2.zero();

	/**
	 * A list storing all forces applied to the object since the last call of
	 * {@link #playoutForces()} as {@link Vector2}
	 */
	protected ArrayList<Vector2> forces = new ArrayList<Vector2>();

	/**
	 * stores the mass of the object as float in kilograms
	 */
	protected float mass;

	protected float radius;

	/**
	 * Constructor.
	 *
	 * @param distance
	 *            The distance the new object should have from the origin.
	 * @param theta
	 *            The angle of the position in relation to the origin.
	 * @param mass
	 *            The mass of the object.
	 * @param radius
	 *            The radius of the object.
	 * @param impulsVelocity
	 *            A float representing the magnitude of the velocity of the
	 *            object.
	 * @param alpha
	 *            The angle of the velocity
	 */
	public PhysicsObject(float distance, float theta, float mass, float impulsVelocity, float alpha, float size) {
		super();
		this.position = Vector2.fromRadial(theta, distance);
		this.velocity = Vector2.fromRadial(alpha, impulsVelocity);
		this.radius = size;
		setMass(mass);
	}

	/**
	 * Constructor.
	 *
	 * @param position
	 *            The position of the new object.
	 * @param velocity
	 *            The velocity of the new object.
	 * @param mass
	 *            The mass of the object.
	 * @param radius
	 *            The radius of the object.
	 */
	public PhysicsObject(Vector2 position, Vector2 velocity, float mass, float radius) {
		super();
		this.position = position;
		this.radius = radius;
		setMass(mass);
		setPosition(position);
		this.velocity = velocity;
	}

	/**
	 * Constructor.
	 *
	 * @param position
	 *            The position of the new object.
	 * @param velocity
	 *            The velocity of the new object.
	 * @param mass
	 *            The mass of the object.
	 * @param collider
	 *            The collider of the object.
	 */

	/**
	 * @return {@link PhysicsObject.mass}
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	* @return The radius of this object.
	*/
	public float getRadius() {
		return this.radius;
	}

	/**
	 * Scales the attached polygons
	 * @param amt Amount of scaling.
	 */
	public void setRadius(float amt) {
		this.radius = amt;
	}

	/**
	 * Sets {@link PhysicsObject.position}
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	/**
	 * @return {@link PhysicsObject.mass}
	 */
	public float getMass() {
		return mass;
	}

	/**
	 * Sets {@link PhysicsObject.mass}
	 */
	public void setMass(float mass) {
		this.mass = mass;

	}

	/**
	 * Adds a force to the protected list of {@link #forces}.
	 *
	 * @param force
	 *            The force to add to the list.
	 */
	public void appendForce(Vector2 force) {
		forces.add(force);
	}

	/**
	 * Clears the list of forces.
	 *
	 * Use this method to remove all current forces from an object.
	 */
	public void resetForces() {
		forces.clear();
	}

	/**
	 * Updates {@link #acceleration} with the current forces and clears
	 * {@link #forces}.
	 */
	public void playoutForces() {
		Vector2 sum = new Vector2();
		for (Vector2 force : forces) {
			sum.add(force);
		}
		acceleration = sum.div(mass);
		resetForces();
	}

	/**
	 * Updates the position based on the velocity and acceleration. Updates the
	 * velocity based on the acceleration.
	 * @param time The time since the last call.
	 */
	public void move(float time) {
		pastPositions.add(position);
		position.add(Vector2.scale(acceleration, 0.5f * time * time).add(Vector2.scale(velocity, time)));
		velocity.add(Vector2.scale(acceleration, time));
	}

	public ArrayList<Vector2> getPastPositions(){
		return pastPositions;
	}
	/**
	 * Unites two PhysicsObjects into one.
	 *
	 * @param one
	 *            The first object.
	 * @param two
	 *            The second object.
	 */
	public static PhysicsObject unite(PhysicsObject one, PhysicsObject two) {
		return new PhysicsObject(Vector2.lerp(one.getPosition(), two.getPosition(), two.mass / (one.mass + two.mass)),
				Vector2.add(one.velocity, two.velocity)
						.setMag((one.mass * one.velocity.magnitude() + two.mass * two.velocity.magnitude())
								/ (one.mass + two.mass)),
				one.mass + two.mass, (float) Math.sqrt(Math.pow(one.getRadius(), 2) + Math.pow(two.getRadius(), 2)));
	}

}
