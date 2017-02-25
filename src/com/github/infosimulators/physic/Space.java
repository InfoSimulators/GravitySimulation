package com.github.infosimulators.physic;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

/**
 * This class can be seen as a host for objects that should be effected by
 * physics especially gravity.
 *
 * @author Julisep
 */
public class Space {
    /**
     * Stores the maximum distance from the origin
     * Objects further appart are seen lost and will be removed from space register and deleted by GC.
     */
    public float maxDistance = 1000f;
    /**
     * Stores the position of the center point. The outside is calculated on the basis of this point.
     */
    public Vector2 pointOfOrigin = Vector2.zero();
    /**
     * Spaceregister that stores all objects that should be effected by gravity.
     */
    protected ArrayList<PhysicsObject> spaceRegister = new ArrayList<PhysicsObject>();

    // Constructors
    public Space() {
    }

    public Space(float maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Space(float maxDistance, Vector2 pointOfOrigin) {
        this.maxDistance = maxDistance;
        this.pointOfOrigin = pointOfOrigin;
    }

    /**
     * Adds objects to the space register
     */
    public void registerPhysicsObject(PhysicsObject... objects) {
        for (PhysicsObject object : objects) {
            spaceRegister.add(object);
        }
    }

    /**
     * Remove objects from the space register
     */
    public void unregisterPhysicsObject(PhysicsObject... objects) {
        for (PhysicsObject object : objects) {
            spaceRegister.remove(object);
        }
    }

    /**
     * Gets the space register
     * @return the space register (ArrayList<PhysicsObject>)
     */
    public ArrayList<PhysicsObject> getSpaceRegister() {
        return spaceRegister;
    }

    public Vector2 getPositionOnOutside(float angle) {
        return Vector2.radial(angle).scale(maxDistance).add(pointOfOrigin);
    }

    public Vector2 getRandomPositionOnOutside() {
        Random r = new Random();
        return Vector2.radial(r.nextInt(361)).scale(maxDistance).add(pointOfOrigin);
    }

    public boolean isInside(Vector2 position) {
        return Vector2.sqrDistance(position, pointOfOrigin) <= maxDistance * maxDistance;
    }

    public void addGravitationForces() {
        for (PhysicsObject object : spaceRegister) {
            for (PhysicsObject other : spaceRegister) {
                if (object == other)
                    continue;
                //System.out.println("object: "+ object.mass + "other: " + other.mass + " => " + gravitation(object, other));
                object.appendForce(gravitation(object, other));
            }
        }
    }

    public void getCollisions() {
        for (PhysicsObject object : spaceRegister) {
            for (PhysicsObject other : spaceRegister) {
                if (object == other)
                    continue;
                /*If both objects overlap*/
                if (Vector2.distance(object.position, other.position) < (object.size + other.size) / 1.5f) {
                    Vector2 gravitationForce = gravitation(object, other);
                    /*If the gravitation is stronger than velocity*/
                    if (Vector2.sqrDistance(object.velocity, other.velocity) < gravitationForce.sqrMagnitude()) {
                        //System.out.println("connection");
                        //Unite Objects and remove old
                        ArrayList<Vector2> forces = new ArrayList<Vector2>();
                        forces.addAll(object.forces);
                        forces.addAll(other.forces);
                        spaceRegister.add(new PhysicsObject(
                                Vector2.lerp(object.position, other.position, other.mass / (object.mass + other.mass)),
                                Vector2.add(object.velocity, other.velocity)
                                        .setMag((object.mass * object.velocity.magnitude()
                                                + other.mass * other.velocity.magnitude())
                                                / (object.mass + other.mass)),
                                object.mass + other.mass,
                                (float) Math.cbrt(Math.pow(object.size, 3) + Math.pow(other.size, 3)), forces));
                        spaceRegister.remove(object);
                        spaceRegister.remove(other);
                        break;
                    } else { /*Else do an elastic collision*/
                        //System.out.println("elatic collision");
                        float v1 = object.velocity.magnitude();
                        Vector2 v1_vector = object.velocity;
                        float v2 = other.velocity.magnitude();
                        Vector2 v2_vector = other.velocity;
                        object.velocity = v2_vector
                                .setMag((object.mass * v1 + other.mass * (2 * v1 - v2)) / (object.mass + other.mass));
                        other.velocity = v1_vector
                                .setMag((other.mass * v2 + object.mass * (2 * v1 - v2)) / (object.mass + other.mass));
                    }
                }
            }
        }
    }

    /**
     * @return number of planets that left the system
     */
    public int tick() {
            int planetsLeft = 0;
            addGravitationForces();
            for (PhysicsObject object : spaceRegister) {
                if (!isInside(object.position)){
                    unregisterPhysicsObject(object);
                    planetsLeft++;
                    continue;
                }
                object.playoutForces();
                object.move();
            }
            getCollisions();
            return planetsLeft;
    }

    /**
     * Calculats the force PhysicsObject b acts on PhysicsObject a.
     * Only the force from b to a is returned.
     *
     * @param a PhysicsObject
     * @param b PhysicsObject
     * @return the reulting force as a Vector2
     */
    public static Vector2 gravitation(PhysicsObject a, PhysicsObject b) {
        float force = (float) Constants.G * ((a.mass * b.mass) / (Vector2.sqrDistance(a.position, b.position)));
        return Vector2.subtract(b.position, a.position).setMag(force);
    }
}