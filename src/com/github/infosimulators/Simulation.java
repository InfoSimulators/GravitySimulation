package com.github.infosimulators;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.github.infosimulators.IDRegistry.IDd;
import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventCategory;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;
import com.github.infosimulators.physic.PhysicsObject;
import com.github.infosimulators.physic.Space;
import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.polygons.Polygon;

/**
 * Calculates data on given parameters.
 */
public class Simulation extends IDd {

	protected Space space;
	private float[][] initialConfig;
	public boolean finished = false;

	/**
	 * Constructor. Starts a simulation with a space filled with objects.
	 * defined by the parameter configuration.
	 *
	 * @param configuration
	 *            The properties of the objects the space should be filled with.
	 *            The first dimension defines the object. The second dimension
	 *            the properties. This list is structured like this:
	 *            configuration[n][0] The distance from the origin.
	 *            configuration[n][1] The angle from the origin.
	 *            configuration[n][2] The mass of the object.
	 *            configuration[n][3] The magnitude of the velocity.
	 *            configuration[n][4] The angle of the velocity.
	 *            configuration[n][5] The size of the object.
	 *            configuration[n][6] The shape of the object.
	 */
	public Simulation(float[][] configuration) {
		super();
		space = new Space();
		space.simulationID = getID();

		if (configuration[0].length == 6)
			for (float[] object : configuration)
				space.registerPhysicsObject(
						new PhysicsObject(object[0], object[1], object[2], object[3], object[4], object[5]));
		else
			for (float[] object : configuration)
				space.registerPhysicsObject(
						new PhysicsObject(object[0], object[1], object[2], object[3], object[4], object[5], object[6]));
		space.simulationID = getID();
		initialConfig = configuration;
	}

	/**
	 * Creates a new object in space and adds it to the space.
	 *
	 * @param position
	 *            The initial position of this object
	 * @param velocity
	 *            The initial velocity of this object
	 * @param mass
	 *            The mass of this object
	 * @param size
	 *            The collider of this object
	 */
	public void addObject(Vector2 position, Vector2 velocity, float mass, Polygon collider) {
		PhysicsObject a = new PhysicsObject(position, velocity, mass, collider);
		space.registerPhysicsObject(a);
	}

	/**
	 * Called on each frame
	 */
	public void update() {
		if (finished)
			return;
		space.tick();

		if (space.getSpaceRegister().size() <= 1) {
			EventRegistry.fire(new Event(EventType.SIMU_END, Arrays.asList(EventCategory.SIMULATION),
					new String[] { "" + getID(), "" + space.getNumberOfRuns() }));
			clearID();
			finished = true;
		}
	}

	/**
	 * @return The content of the simulation as ArrayList of
	 *         {@link PhysicsObject}s.
	 */
	public ArrayList<PhysicsObject> getContent() {
		return space.getSpaceRegister();
	}

	/**
	 * @return The initial configuration.
	 */
	public float[][] getInitialConfig() {
		return initialConfig;
	}

	/**
	 * Sets the time between updates. Equals space.setDeltaTime()
	 * @param The new time between updates.
	 */
	public void setDeltaTime(float deltaTime){
		space.setDeltaTime(deltaTime);
	}
	/**
	 * Loads a simulation from a file.
	 *
	 * @param name
	 *            The name of the Simulation to load.
	 * @return The loaded simulation.
	 */
	public Simulation loadSimulation(String name) {
		float[][] configuration = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(name + ".simulation"));
			ArrayList<String> content = new ArrayList<String>();
			String line;
			while ((line = br.readLine()) != null) {
				content.add(line);
			}
			configuration = new float[content.size()][content.get(0).split(",").length];
			for (int i = 0; i < content.size() - 1; i++) {
				for (int x = 0; x < content.get(i).split(",").length; x++) {
					configuration[i][x] = Float.parseFloat(content.get(i).split(",")[x]);
				}
			}
			br.close();
		} catch (IOException i) {
			i.printStackTrace();
			configuration = null;
		}
		return new Simulation(configuration);
	}

	/**
	 * Writes a simulation into a file, which can be read with {@link load}().
	 *
	 * @param name
	 *            THe name to store the simulation in.
	 * @param simulation
	 *            The simulation to store.
	 * @return Whether the storing was a success.
	 */
	public boolean writeSimulation(String name, Simulation simulation) {
		boolean success = false;
		float[][] configuration = simulation.getInitialConfig();
		try {
			String content = "";
			for (float[] object : configuration) {
				content += ("" + Arrays.asList(object)).toString().replaceAll("(^.|.$)", "").replace(", ", ",");
				content += "\n";
			}
			FileWriter br = new FileWriter(name + ".simulation");
			br.write(content);
			br.close();
			success = true;
		} catch (IOException i) {
			i.printStackTrace();
			success = false;
		}
		return success;
	}

	@Override
	public void clearID() {
		super.clearID();
		space.clearIDs();
	}

}
