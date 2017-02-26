package com.github.infosimulators.physic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.IDRegistry.IDd;
import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventType;

/**
 * This class can be seen as a host for objects that should be effected by
 * physics especially gravity.
 *
 * @author Julisep
 */
public class Space extends IDd {
    /**
     * Stores the maximum distance from the origin
     * Objects further appart are seen lost and will be removed from space register and deleted by GC.
     */
    public float maxDistance = 1000f;
    /**
     * Stores the position of the center point.
     * The outside is calculated on the basis of this point.
     */
    public Vector2 pointOfOrigin = Vector2.zero();
    /**
     * Spaceregister that stores all objects that should be effected by gravity.
     */
    protected ArrayList<PhysicsObject> spaceRegister = new ArrayList<PhysicsObject>();

    // Constructors
    public Space() {
        super();
    }

    /**
     * Adds objects to the space register.
     * @param objects The Objects to be added to the register.
     */
    public void registerPhysicsObject(PhysicsObject... objects) {
        for (PhysicsObject object : objects) {
            spaceRegister.add(object);
        }
    }

    /**
     * Removes objects from the space register.
     * @param objects The Objects to be removed from the register.
     */
    public void unregisterPhysicsObject(PhysicsObject... objects) {
        for (PhysicsObject object : objects) {
            spaceRegister.remove(object);
        }
    }

    /**
     * Gets the space register.
     * @return The space register as ArrayList of {@link PhysicsObject}s.
     */
    public ArrayList<PhysicsObject> getSpaceRegister() {
        return spaceRegister;
    }

    /**
     * Returns a position on the outside of the space based on the parameter angle.
     *
     * @param angle The angle of the position in radians.
     *
     * @return A position on the outer rim.
     */
    public Vector2 getPositionOnOutside(float angle) {
        return Vector2.radial(angle).scale(maxDistance).add(pointOfOrigin);
    }

    /**
     * Returns a random position on the outside of the space.
     *
     * @return A random position on the outer rim.
     */
    public Vector2 getRandomPositionOnOutside() {
        Random r = new Random();
        return Vector2.radial(r.nextInt(361)).scale(maxDistance).add(pointOfOrigin);
    }

    /**
     * Checks weather a position is inside the space.
     *
     * @param position The position to check.
     * @return If the position is inside the space.
     */
    public boolean isInside(Vector2 position) {
        return Vector2.sqrDistance(position, pointOfOrigin) <= maxDistance * maxDistance;
    }

    /**
     * Adds gravitational forces to each object in the space register.
     */
    public void addGravitationForces() {
        for (PhysicsObject object : spaceRegister) {
            for (PhysicsObject other : spaceRegister) {
                if (object == other)//If we calculated the force between one object and itself, the force would be infinite and the simulation would crash.
                    continue;
                object.appendForce(gravitation(object, other));
            }
        }
    }

    /**
     * Calculates the new velocities after an elastic collision and sets them for both involved objects.
     *
     * The order of both objects is not important.
     *
     * @param one The first object to be involved in the collision.
     * @param two The secound object to be involved in the collision.
     *
     */
    protected void doElasticCollision(PhysicsObject one, PhysicsObject two) {
        float v1 = one.velocity.magnitude();
        Vector2 v1_vector = one.velocity;
        float v2 = two.velocity.magnitude();
        Vector2 v2_vector = two.velocity;
        one.velocity = v2_vector.setMag((one.mass * v1 + two.mass * (2 * v1 - v2)) / (one.mass + two.mass));
        two.velocity = v1_vector.setMag((two.mass * v2 + one.mass * (2 * v1 - v2)) / (one.mass + two.mass));
    }

    /**
     * Calculates weather two objects are colliding.
     *
     * The order of both objects is not important.
     *
     * @param one One {@link PhysicsObject}.
     * @param two Another {@link PhysicsObject}.
     */
    protected boolean areColliding(PhysicsObject one, PhysicsObject two) {
        return Vector2.distance(one.position, two.position) < (one.size + two.size);
    }

    /**
     * Calculates weather two objects would unite if they intersect, by comparing the gravitation to the velocity.
     *
     * If the gravitational force has a higher magnitude then the velocity separating both objects, true will be returned.
     *
     * Order is not important.
     *
     * @param one One Object intersecting the other.
     * @param two Another Object intersecting the first.
     *
     * @return weather both objects will fuse.
     */
    protected boolean wouldUnite(PhysicsObject one, PhysicsObject two) {
        return Vector2.sqrDistance(one.velocity, two.velocity) < gravitation(one, two).sqrMagnitude();
    }

    /**
     * Handles collisions.
     *
     * If two objects intersect, it is checked weather the gravitation would lead to a higher velocity towardseachother, then the current velocity separating both objects.
     * If this is the case, then both objects will fuse together.
     * If it is not the case doElasticCollision is called with both objects.
     */
    public void handleCollisions() {
        ArrayList<PhysicsObject> add = new ArrayList<PhysicsObject>();
        ArrayList<PhysicsObject> remove = new ArrayList<PhysicsObject>();
        ArrayList<PhysicsObject> collided = new ArrayList<PhysicsObject>();
        for (PhysicsObject object : spaceRegister) {
            for (PhysicsObject other : spaceRegister) {
                if (object == other)
                    continue;
                if (remove.contains(object) || remove.contains(other))
                    continue;
                /*If both objects overlap*/
                if (areColliding(object, other)) {
                    EventRegistry.fire(new Event(EventType.SIMU_PLANET_COLLISION));
                    if (wouldUnite(object, other)) {
                        EventRegistry.fire(new Event(EventType.SIMU_PLANET_UNITE));
                        //Unite Objects and remove old
                        add.add(PhysicsObject.unite(object, other));
                        remove.add(object);
                        remove.add(other);
                    } else { /*Else do an elastic collision*/
                        if (collided.contains(object) && collided.contains(other))
                            continue;
                        doElasticCollision(object, other);
                        collided.add(object);
                        collided.add(other);
                    }
                }
            }
        }
        for (PhysicsObject object : remove)
            unregisterPhysicsObject(object);
        for (PhysicsObject object : add)
            registerPhysicsObject(object);
    }

    /**
     *  Called on every frame.
     */
    public void tick() {
        addGravitationForces();
        Iterator<PhysicsObject> registerIterator = spaceRegister.iterator();
        while (registerIterator.hasNext()) {
            if (!isInside(registerIterator.next().position)) {
                unregisterPhysicsObject(registerIterator.next());
                EventRegistry.fire(new Event(EventType.SIMU_PLANET_LEFT));
                continue;
            }
            registerIterator.next().playoutForces();
            registerIterator.next().move();
        }
        handleCollisions();
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