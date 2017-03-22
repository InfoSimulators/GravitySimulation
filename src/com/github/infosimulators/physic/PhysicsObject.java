package com.github.infosimulators.physic;

import java.util.ArrayList;
import com.github.infosimulators.IDRegistry.IDd;
import com.github.infosimulators.polygons.Polygon;
import com.github.infosimulators.polygons.regular.Sphere;

/**
 * Baseclass for all objects, manipulated by physics.
 * It stores position, forces,acceleration, velocity, mass and size
 * It is assumed to be a sphere with the radius size with the mass located in the middle
 */
public class PhysicsObject extends IDd {

	/** The position of the object in a Cartesian coordinate system represented as a {@link Vector2} */
	protected Vector2 position;

	/** The velocity of the object in a Cartesian coordinate system represented as a {@link Vector2} */
	public Vector2 velocity;

	/** The acceleration of the object in a Cartesian coordinate system represented as a {@link Vector2} */
	public Vector2 acceleration = Vector2.zero();

	/** A list storing all forces applied to the object since the last call of {@link #playoutForces()} as {@link Vector2} */
	protected ArrayList<Vector2> forces = new ArrayList<Vector2>();

	/** stores the mass of the object as float in kilogramm */
	protected float mass;

	public Polygon collider = new Sphere();

	/**
	 * Constructor.
	 *
	 * @param distance The distance the new object should have from the origin.
	 * @param theta The angle of the position in relation to the origin.
	 * @param mass The mass of the Object.
	 * @param radius The radius of the object.
	 * @param impulsVelocity A float representing the magnitude of the velocity of the object.
	 * @param alpha The angle of the velocity
	 */
	public PhysicsObject(float distance, float theta, float mass, float impulsVelocity, float alpha, float size) {
		super();
		this.position = new PolarVector2(theta, distance).toCartesian();
		this.velocity = new PolarVector2(alpha, impulsVelocity).toCartesian();
		this.collider = new Sphere(position, size);
		setMass(mass);
	}

	/**
	* Constructor.
	*
	* @param position The position of the new object.
	* @param velocity The velocity of the new object.
	* @param mass The mass of the Object.
	* @param radius The radius of the object.
	*/
	public PhysicsObject(Vector2 position, Vector2 velocity, float mass, float radius) {
		super();
		this.position = position;
		this.collider = new Sphere(position, radius);
		setMass(mass);
		this.velocity = velocity;
	}

	/**
	* Constructor.
	*
	* @param position The position of the new object.
	* @param velocity The velocity of the new object.
	* @param mass The mass of the Object.
	* @param radius The radius of the object.
	*/
	public PhysicsObject(Vector2 position, Vector2 velocity, float mass, Polygon collider) {
		super();
		this.collider = collider;
		setPosition(position);
		setMass(mass);
		this.velocity = velocity;
	}

	/**
	 * @return {@link PhysicsObject.mass}.
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	* Sets {@link PhysicsObject.position}.
	*/
	public void setPosition(Vector2 position) {
		this.position = position;
		this.collider.setOffset(position);
	}

	/**
	* @return {@link PhysicsObject.mass}.
	*/
	public float getMass() {
		return mass;
	}

	/**
	* Sets {@link PhysicsObject.mass}.
	*/
	public void setMass(float mass) {
		this.mass = mass;
		this.collider.setMass(mass);
	}

	/**
	 * Adds a force to the protected list of forces {@link #forces}.
	 *
	 * @param force The force to add to the list.
	 */
	public void appendForce(Vector2 force) {
		forces.add(force);
	}

	/**
	 * Clears the list of forces.
	 *
	 * Use this methode to remove all current forces from an object.
	 */
	public void resetForces() {
		forces.clear();
	}

	/**
	 * Updates {@link #acceleration} with the current forces.
	 * It also resets the {@link #forces}-list and therefore calling it in a row will result in nothing.
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
	 * Updates the position based on the velocity and acceleration.
	 * And updates the velocity based on the acceleration.
	 */
	public void move() {
		position.add(Vector2.scale(acceleration, 0.5f).add(velocity));
		velocity.add(acceleration);
		collider.setOffset(position);
	}

	/**
	 * Unites two PhysicsObjects into one.
	 *
	 * @param one The first object.
	 * @param two The secound object.
	 */
	public static PhysicsObject unite(PhysicsObject one, PhysicsObject two) {
		Polygon united = new Polygon();
		float angel = Vector2.subtract(one.getPosition(), two.getPosition()).angle();
		for(PolarVector2 v : one.collider.getLocalVerticies()){
			if(Math.abs(angel-v.theta)<=Math.PI)
				united.addVertex(v);
		}
		for (PolarVector2 v : two.collider.getLocalVerticies()) {
			if (Math.abs(angel - v.theta) > Math.PI)
				united.addVertex(v);
		}
		return new PhysicsObject(Vector2.lerp(one.getPosition(), two.getPosition(), two.mass / (one.mass + two.mass)),
				Vector2.add(one.velocity, two.velocity)
						.setMag((one.mass * one.velocity.magnitude() + two.mass * two.velocity.magnitude())
								/ (one.mass + two.mass)),
				one.mass + two.mass,
				 united);
	}

}
