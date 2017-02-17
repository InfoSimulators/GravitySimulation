package com.github.infosimulators;

import com.github.infosimulators.physic.*;
import java.util.*;
import java.util.Random;

/**
 * Calculates data on given parameters.
 */
public class Simulation {
	protected Space space;
	/**
	* The rate of new baseobjects spawning.
	* A new Object is spawned every spawnRate turns.
	* If set to -1 no baseobjects will spawn.
	*/
	public int spawnRate = 10;
	private int spawnCounter = 0;
	public Vector3 basePosition = new Vector3(100f,100f,0f);
	public Vector3 baseVelocity = new Vector3(1f,1f,0f);
	public float baseSize = 30f;
	public float baseMass = 2E5f;
	/**
	 * The randomness of the velocity new random objects, spawned based on time, have.
	 * Multiplies the random Vector that is added to is added to {@link com.github.infosimulators.Simulation#baseVelocity baseVelocity}.
	 */
	public float maxVelocityRandomness = 0.5f;
	private final Random r = new Random();

	/**
	 * Auto-generated constructor.
         * @param size
	 */
	public Simulation(float size) {
		space = new Space(size);
	}

	/**
	 * Creates a new random object in space and adds it to the space.
	 */
	public void addObject(){
		PhysicsObject a = new PhysicsObject(space.getRandomPositionOnOutside(), new Vector2(r.nextFloat()*2 - 1, r.nextFloat()*2 - 1 ),(float) Math.pow(1,r.nextInt()*15), r.nextFloat()*15);
		space.registerPhysicsObject(a);
	}
	/**
	 * Creates a (partly) random new object in space and adds it to the space.
	 *
	 * @param position the initial position of this object
	 * @param velocity the initial velocity of this object
	 * @param mass the mass of this object
	 * @param size the size of this object
	 */
	public void addObject(Vector3 position, Vector3 velocity, float mass, float size){
		PhysicsObject a = new PhysicsObject(position, velocity, mass, size);
		space.registerPhysicsObject(a);
	}
	/**
	 * Creates a new object and adds it to the space.
	 *
        * @return the new object
	 */
	public PhysicsObject addBaseObject(){
		PhysicsObject a = new PhysicsObject(basePosition, Vector3.add(baseVelocity, new Vector2( r.nextFloat()*2 - 1, r.nextFloat()*2 - 1).scale(maxVelocityRandomness)), baseMass, baseSize);
		space.registerPhysicsObject(a);
		return a;
	}

	/**
	 * Called on each frame
	 */
	public void update() {
		if(spawnRate != -1){
			if(spawnCounter >= spawnRate){
				addBaseObject();
				spawnCounter = 0;
			} else {
				spawnCounter++;
			}
		}
		space.tick();
	}
	/**
 	* @return the content of the Simulation as ArrayList of PhysicsObjects
 	*/
	public ArrayList<PhysicsObject> getContent(){
		return space.getSpaceRegister();
	}
}
