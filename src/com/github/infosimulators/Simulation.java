package com.github.infosimulators;

import java.util.ArrayList;

import com.github.infosimulators.IDRegistry.IDd;
import com.github.infosimulators.physic.PhysicsObject;
import com.github.infosimulators.physic.Space;

/**
 * Calculates data on given parameters.
 */
public class Simulation extends IDd {

	protected Space space;
	// /**
	//  * The rate of new baseobjects spawning. A new Object is spawned every
	//  * spawnRate turns. If set to -1 no baseobjects will spawn.
	//  */
	// public Vector2 basePosition = new Vector2(100f, 100f);
	// public Vector2 baseVelocity = new Vector2(1f, 1f);
	// public float baseSize = 30f;
	// public float baseMass = 2E5f;

	// /**
	//  * The randomness of the velocity new random objects, spawned based on time,
	//  * have. Multiplies the random Vector that is added to is added to
	//  * {@link com.github.infosimulators.Simulation#baseVelocity baseVelocity}.
	//  */
	// public float maxVelocityRandomness = 0.5f;

	/**
	 * Auto-generated constructor.
	 *
	 * @param size
	 */
	public Simulation(float size) {
		super();
		space = new Space(size);
	}
	public Simulation(float[][] content){
		super();
		space = new Space();
		for(float[] object : content){
			space.registerPhysicsObject(new PhysicsObject(object[0], object[1], object[2], object[3], object[4], object[5]));
		}
	}
	public Simulation(ArrayList<PhysicsObject> content) {
		super();
		space = new Space();
		for (PhysicsObject object : content) {
			space.registerPhysicsObject(object);
		}
	}

	public Simulation(ArrayList<PhysicsObject> content, float size) {
		super();
		space = new Space(size);
		for (PhysicsObject object : content) {
			space.registerPhysicsObject(object);
		}
	}
	/*
	// /**
	//  * Creates a new random object in space and adds it to the space.
	//  */
	// public void addObject() {
	// 	PhysicsObject a = new PhysicsObject(space.getRandomPositionOnOutside(),
	// 			new Vector2(r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1), (float) Math.pow(1, r.nextInt() * 15),
	// 			r.nextFloat() * 15);
	// 	space.registerPhysicsObject(a);
	// }

	// /**
	//  * Creates a (partly) random new object in space and adds it to the space.
	//  *
	//  * @param position
	//  *            the initial position of this object
	//  * @param velocity
	//  *            the initial velocity of this object
	//  * @param mass
	//  *            the mass of this object
	//  * @param size
	//  *            the size of this object
	//  */
	// public void addObject(Vector2 position, Vector2 velocity, float mass, float size) {
	// 	PhysicsObject a = new PhysicsObject(position, velocity, mass, size);
	// 	space.registerPhysicsObject(a);
	// }

	// /**
	//  * Creates a new object and adds it to the space.
	//  *
	//  * @return the new object
	//  */
	// public PhysicsObject addBaseObject() {
	// 	PhysicsObject a = new PhysicsObject(basePosition,
	// 			Vector2.add(baseVelocity,
	// 					new Vector2(r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1).scale(maxVelocityRandomness)),
	// 			baseMass, baseSize);
	// 	space.registerPhysicsObject(a);
	// 	return a;
	// }

	/**
	 * Called on each frame
	 *
	 * @return number of planets that left the system
	 */
	public void update() {
		space.tick();
	}

	/**
	 * @return the content of the Simulation as ArrayList of PhysicsObjects
	 */
	public ArrayList<PhysicsObject> getContent() {
		return space.getSpaceRegister();
	}
}
