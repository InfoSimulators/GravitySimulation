package com.github.infosimulators.physic;

import java.util.*;
import java.lang.Math;

/**
 * This class can be seen as a host for objects that should be effected by physics especially gravity.
 *
 * @author Julisep
 */
public class Space {
    /**
     * Stores the maximum distance from the center @see pointOfOrigin.
     * Objects further appart are seen lost and will be removed from space register and deleted by GC.
     */
    public int maxDistance = 1000f;
    public Vector3 pointOfOrigin = new Vector3(0, 0, 0);
    /**
     * Spaceregister that stores all objects that should be effected by gravity.
     */
    public ArrayList<PhysicsObject> spaceRegister = new ArrayList<PhysicsObject>();
    /**
     *  The mathmatical time between cicles of update.
     */
    protected float deltaTime = 1f;

    // Constructors
    public Space() {
    }

    public Space(float maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Space(float maxDistance, float deltaTime) {
        this.maxDistance = maxDistance;
        this.deltaTime = deltaTime;
    }

    /**
     * Gets deltatime, the mathmatical time between cicles of update.
     * @return current deltatime
     */
    public float getDeltaTime() {
        return this.deltaTime;
    }

    /**
     * Sets deltatime, the mathmatical time between cicles of update.
     *
     * @param deltaTime the new tim between cicles. Must be larger then 0
     */
    public void setDeltaTime(float deltaTime) {
        if (deltaTime <= 0) {
            new Err("deltaTime can´t be below / or 0.");
            return;
        }
        this.deltaTime = deltaTime;
    }

    /**
     * Adds objects to the space register
     */
    public void registerPhysicsObject(PhysicsObject... objects) {
        for (PhysicsObject object : objects) {
            spaceRegister.add(object);
        }
    }

    public void unregisterPhysicsObject(PhysicsObject... objects) {
        for (PhysicsObject object : objects) {
            spaceRegister.remove(object);
        }
    }

    public void update() {
        addGravitationForces();
        for (PhysicsObject object : spaceRegister) {
            if (Vector3.sqrDistance(object.position, pointOfOrigin) >= maxDistance * maxDistance)
                return unregisterPhysicsObject(object);
            object.playoutForces();
            object.move(deltaTime);
        }
        getCollisions();
    }

    public void addGravitationForces() {
        for (PhysicsObject object : spaceRegister) {
            for (PhysicsObject other : spaceRegister) {
                if (object == other)
                    return;
                object.appendForce(gravitation(object, other));
            }
        }
    }

    public void getCollisions() {
        for (PhysicsObject object : spaceRegister) {
            for (PhysicsObject other : spaceRegister) {

                /*If both objects overlap*/
                if (Vector3.distance(object.position, other.position) <= object.size + other.size) {
                    Vector3 gravitationForce = gravitation(object, other);
                    /*If the gravitation is stronger than velocity*/
                    if (Vector3.sqrDistance(object.velocity, other.velocity) < gravitationForce.sqrMagnitude()
                            * deltaTime * deltaTime) {

                        //Unite Objects and remove old
                        ArrayList<Vector3> forces = new ArrayList<Vector3>();
                        forces.addAll(object.forces, other.forces);
                        forces.addAll(other.forces);
                        spaceRegister.add(new PhysicsObject(Vector3.lerp(object.position, other.position, 0.5f),
                                Vector3.add(object.velocity, other.velocity), object.mass + other.mass,
                                (float) Math.cbrt(Math.pow(object.size, 3) + Math.pow(other.size, 3)), forces));
                        spaceRegister.remove(object, other);
                    } else { /*Else do an elastic collision*/
                        Vector3 v1 = object.velocity;
                        Vector3 v2 = other.velocity;
                        object.velocity = (object.mass * v1 + other.mass * (2 * v1 - v2)) / (object.mass + other.mass);
                        other.velocity = (other.mass * v2 + object.mass * (2 * v1 - v2)) / (object.mass + other.mass);
                    }
                }
            }
        }
    }

    /**
     * Calculats the force PhysicsObject b acts on PhysicsObject a.
     * Only the force from b to a is returned.
     *
     * @param a PhysicsObject
     * @param b PhysicsObject
     * @return the reulting force as a Vector3
     * @see PhysicsObject
     */
    public static Vector3 gravitation(PhysicsObject a, PhysicsObject b) {
        float force = (float) Constants.G * ((a.mass * b.mass) / Vector3.sqrDistance(a.position, b.position));
        return Vector3.subtract(a.position, b.position).setMag(force);
    }
}