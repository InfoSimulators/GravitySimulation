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
	private float[][] initialConfig;

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
	 * Startes a simulation with a space filled with spaceobjects defined by the parameter configuration.
	 *
	 * @param configuration
	 * 		The properties of the objects the space should be filled with.
	 * 		The first dimension defines the object. The second dimension the properties.
	 * 		This list is structured like this:
	 * 		configuration[n][0] The distance from the origin.
	 * 		configuration[n][1] The angle from the origin.
	 * 		configuration[n][2] The mass of the object.
	 * 		configuration[n][3] The magnitude of the velocity.
	 * 		configuration[n][4] The angle of the velocity.
	 * 	 	configuration[n][5] The radius of the object.
	 */
	public Simulation(float[][] configuration) {
		super();
		space = new Space();
		space.simulationID = getID();
		for (float[] object : configuration) {
			space.registerPhysicsObject(
					new PhysicsObject(object[0], object[1], object[2], object[3], object[4], object[5]));
		}
		space.simulationID = getID();
		initialConfig = configuration;
	}

	/**
	* Constructor.
	* Startes a simulation with a space filled with spaceobjects defined by the parameter configuration.
	*
	* @param configuration See {@link Simulation#Simulation(float[][])}.
	* @param size The viewable size of this simulation.
	*/
	public Simulation(float[][] configuration, float size) {
		super();
		space = new Space();
		space.simulationID = getID();
		for (float[] object : configuration) {
			space.registerPhysicsObject(
					new PhysicsObject(object[0], object[1], object[2], object[3], object[4], object[5]));
		}
		space.simulationID = getID();
		space.observedRange = size;
		initialConfig = configuration;
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
	/**
	 * @return The inital configuration.
	 */
	public float[][] getInitialConfig(){
		return initialConfig;
	}
}
