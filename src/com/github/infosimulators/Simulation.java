package com.github.infosimulators;

import java.util.ArrayList;

import com.github.infosimulators.IDRegistry.IDd;
import com.github.infosimulators.physic.PhysicsObject;
import com.github.infosimulators.physic.Space;
import com.github.infosimulators.physic.Vector2;

/**
 * Calculates data on given parameters.
 */
public class Simulation extends IDd {

	protected Space space;

	/**
	 * Constructor.
	 * Startes a simulation with a {@link Space} of the size size;
	 *
	 * @param size The size of the {@link Space} containing the simulation.
	 */
	public Simulation(float size) {
		super();
		space = new Space();
		space.observedRange = size;
		space.simulationID = getID();
	}

	/**
	 * Constructor.
	 * Startes a simulation with a space filled with spaceobjects defined by the parameter content.
	 *
	 * @param content
	 * 		The properties of the objects the space should be filled with.
	 * 		The first dimension defines the object. The secound dimension the properties.
	 * 		This list is structured like this:
	 * 		content[n][0] The distance form the origin.
	 * 		content[n][1] The angle from the origin.
	 * 		content[n][2] The mass of the object.
	 * 		content[n][3] The magnitude of the velocity.
	 * 		content[n][4] The angle of the velocity.
	 * 	 	content[n][5] The radius of the object.
	 */
	public Simulation(float[][] content) {
		super();
		space = new Space();
		for (float[] object : content) {
			space.registerPhysicsObject(
					new PhysicsObject(object[0], object[1], object[2], object[3], object[4], object[5]));
		}
	}

	/**
	 * Creates a (partly) random new object in space and adds it to the space.
	 *
	 * @param position
	 *            the initial position of this object
	 * @param velocity
	 *            the initial velocity of this object
	 * @param mass
	 *            the mass of this object
	 * @param size
	 *            the size of this object
	 */
	public void addObject(Vector2 position, Vector2 velocity, float mass, float size) {
		PhysicsObject a = new PhysicsObject(position, velocity, mass, size);
		space.registerPhysicsObject(a);
	}

	/**
	 * Called on each frame
	 */
	public void update() {
		space.tick();
	}

	/**
	 * @return The content of the simulation as ArrayList of {@link PhysicsObject}s.
	 */
	public ArrayList<PhysicsObject> getContent() {
		return space.getSpaceRegister();
	}
}
