package com.github.infosimulators.physic;

import java.util.ArrayList;
import java.util.Arrays;

import com.github.infosimulators.IDRegistry.IDd;
import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventCategory;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;
import com.github.infosimulators.polygons.Polygon;
import com.github.infosimulators.polygons.Sphere;

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

	protected Polygon collider = new Sphere();

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
		this.position = new PolarVector2(theta, distance).toCartesian();
		this.velocity = new PolarVector2(alpha, impulsVelocity).toCartesian();
		this.collider = new Sphere(position, size);
		setMass(mass);
	}

	/**
	 * Constructor.
	 *
	 * @param distance
	 *            The distance the new object should have from the origin.
	 * @param theta
	 *            The angle of the position in relation to the origin.
	 * @param mass
	 *            The mass of the object.
	 * @param size
	 *            The size of the object.
	 * @param impulsVelocity
	 *            A float representing the magnitude of the velocity of the
	 *            object.
	 * @param alpha
	 *            The angle of the velocity.
	 * @param shape
	 *            A float representing the number of vertices the object will have.
	 */
	public PhysicsObject(float distance, float theta, float mass, float impulsVelocity, float alpha, float size,
			float shape) {
		super();
		this.velocity = new PolarVector2(alpha, impulsVelocity).toCartesian();
		this.collider = ((int) shape > 0) ? new Polygon((int) shape) : new Sphere();
		this.collider.scale(size);
		setPosition(new PolarVector2(theta, distance).toCartesian());
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
		this.collider = new Sphere(radius);
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
	public PhysicsObject(Vector2 position, Vector2 velocity, float mass, Polygon collider) {
		super();
		this.collider = collider;
		setPosition(position);
		setMass(mass);
		this.velocity = velocity;
	}

	/**
	 * @return {@link PhysicsObject.mass}
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * @return All vertices of this object.
	 */
	public Vector2[] getVertices() {
		Vector2[] temp = collider.getVertices();
		for(int i = 0; i<temp.length; i++){
			temp[i].add(position);
		}
		return temp;
	}
	/**
	 * Scales the attached polygons
	 * @param amt Amount of scaling.
	 */
	public void scale(float amt){
		this.collider.scale(amt);
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
		this.collider.setMass(mass);
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
		position.add(Vector2.scale(acceleration, 0.5f*time*time).add(velocity));
		velocity.add(acceleration.scale(time));
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
		Polygon united = new Polygon();
		float angel = Vector2.subtract(one.getPosition(), two.getPosition()).angle();
		for (PolarVector2 v : one.collider.getLocalVertices()) {
			if (Math.abs(angel - v.theta) <= Math.PI)
				united.addVertex(v);
		}
		for (PolarVector2 v : two.collider.getLocalVertices()) {
			if (Math.abs(angel - v.theta) > Math.PI)
				united.addVertex(v);
		}
		return new PhysicsObject(Vector2.lerp(one.getPosition(), two.getPosition(), two.mass / (one.mass + two.mass)),
				Vector2.add(one.velocity, two.velocity)
						.setMag((one.mass * one.velocity.magnitude() + two.mass * two.velocity.magnitude())
								/ (one.mass + two.mass)),
				one.mass + two.mass, united);
	}

}
