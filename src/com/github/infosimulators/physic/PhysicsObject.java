package com.github.infosimulators.physic;

import java.util.ArrayList;

/**
 * Baseclass for all objects, manipulated by physics. stores position, forces,
 * velocity, mass and Size It is assumed to be a sphere with the radius size
 * with the mass located in the middle
 */
public class PhysicsObject {
	public Vector2 position = Vector2.zero();
	public Vector2 velocity = Vector2.zero();
	public Vector2 acceleration = Vector2.zero();
	protected ArrayList<Vector2> forces = new ArrayList<Vector2>();
	public float mass = 1f;
	public float size = 1f;
	public String ID;

	@Override
	public String toString() {
		return "PhysicsObject:{\n position: '" + position + "',\n velocity: '" + velocity + "',\n mass: '" + mass
				+ "',\n size: '" + size + "'\n}";
	}

	/*
	 * Constructors
	 */

	public PhysicsObject() {}

	public PhysicsObject(float distance,float theta, float mass,float radius, float impulsVelocity, float alpha, String ID){
		this.position =  Vector2.radial(theta, distance);
		this.mass = mass;
		this.size = radius;
		this.velocity = Vector2.radial(alpha,impulsVelocity);
		this.ID = ID;
	}

	public PhysicsObject(Vector2 position, Vector2 velocity, float mass, float size) {
		this.position = position;
		this.velocity = velocity;
		this.mass = mass;
		this.size = size;
	}

	public PhysicsObject(Vector2 position, Vector2 velocity, float mass, float size, ArrayList<Vector2> forces) {
		this.forces = forces;
		this.position = position;
		this.velocity = velocity;
		this.mass = mass;
		this.size = size;
	}

	/*
	 * FUNCTIONS
	 */

	public void appendForce(Vector2 force) {
		forces.add(force);
	}

	public void resetForces() {
		forces.clear();
	}

	public void playoutForces() {
		Vector2 sum = new Vector2();
		for (Vector2 force : forces) {
			sum.add(force);
		}
		acceleration = sum.div(mass);
		resetForces();
	}

	public void move() {
		position.add(Vector2.scale(acceleration, 0.5f).add(velocity));
		velocity.add(acceleration);
	}

}