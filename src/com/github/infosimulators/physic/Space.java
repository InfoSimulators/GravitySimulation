package com.github.infosimulators.physic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventCategory;
import com.github.infosimulators.events.EventType;
import com.github.infosimulators.polygons.Polygon;

/**
 * This class can be seen as a host for objects that should be effected by
 * physics especially gravity.
 *
 * @author Julisep
 */
public class Space {

	/**
	 * Stores the maximum distance from the origin Objects further appart are
	 * seen lost and will be removed from space register and deleted by GC.
	 */
	public float observedRange = 10e15f;

	/**
	 * Stores the position of the center point. The outside is calculated on the
	 * basis of this point.
	 */
	public Vector2 pointOfOrigin = Vector2.zero();

	/**
	 * Spaceregister that stores all objects that should be effected by gravity.
	 */
	protected ArrayList<PhysicsObject> spaceRegister = new ArrayList<PhysicsObject>();

	/**
	 * Stores the simulationID of the simulation the space belongs to.
	 */
	public long simulationID;

	private int nor = 0;

	/*
	 * Constructors
	 */

	public Space() {
	}

	/*
	 * Attributes
	 */

	/**
	 * Adds objects to the space register.
	 * 
	 * @param objects
	 *            The objects to be added to the register.
	 */
	public void registerPhysicsObject(PhysicsObject... objects) {
		for (PhysicsObject object : objects) {
			spaceRegister.add(object);
		}
	}

	/**
	 * Removes objects from the space register.
	 * 
	 * @param objects
	 *            The Objects to be removed from the register.
	 */
	public void unregisterPhysicsObject(PhysicsObject... objects) {
		for (PhysicsObject object : objects) {
			spaceRegister.remove(object);
		}
	}

	/**
	 * Clears all IDs
	 */
	public void clearIDs() {
		for (PhysicsObject object : spaceRegister) {
			object.clearID();
		}
	}

	/**
	 * @return The space register as ArrayList of {@link PhysicsObject}s.
	 */
	public ArrayList<PhysicsObject> getSpaceRegister() {
		return spaceRegister;
	}

	/**
	 * @return The total mass of all objects in this space.
	 */
	public float summedMass() {
		float sum = 0f;
		for (PhysicsObject o : spaceRegister)
			sum += o.mass;
		return sum;
	}

	/*
	 * Checks
	 */

	/**
	 * Checks weather an object will leave the space.
	 *
	 * @param object
	 *            The object to check.
	 * @return If the object will leave this space.
	 */
	public boolean willLeave(PhysicsObject object) {
		if (!isPositionObservable(object.position))
			return true;
		return false;
		// return Vector2.dot(object.velocity, Vector2.subtract(object.position,
		// pointOfOrigin)) > getEscapeVelocity(object, this);
	}

	/**
	 * Checks whether a position is inside the observed range.
	 *
	 * @param position
	 *            The position to check.
	 * @return If the position is inside the space.
	 */
	public boolean isPositionObservable(Vector2 position) {
		return Vector2.sqrDistance(position, pointOfOrigin) <= observedRange * observedRange;
	}

	/**
	 * Calculates whether two objects are colliding.
	 *
	 * The order of both objects is not important.
	 *
	 * @param one
	 *            One {@link PhysicsObject}.
	 * @param two
	 *            Another {@link PhysicsObject}.
	 */
	protected boolean areColliding(PhysicsObject one, PhysicsObject two) {
		return Polygon.intersectSAT(one.collider, two.collider);
	}

	/**
	 * Calculates whether two objects would unite if they intersect, by
	 * comparing the gravitation to the velocity.
	 *
	 * If the gravitational force has a higher magnitude then the velocity
	 * separating both objects, true will be returned.
	 *
	 * Order is not important.
	 *
	 * @param one
	 *            One Object intersecting the other.
	 * @param two
	 *            An Object intersecting the first.
	 *
	 * @return whether both objects will fuse.
	 */
	protected boolean wouldUnite(PhysicsObject one, PhysicsObject two) {
		return Vector2.sqrDistance(one.velocity, two.velocity) < gravitation(one, two).sqrMagnitude()
				|| Vector2.sqrDistance(one.velocity, two.velocity) == 0f;
	}

	/*
	 * Tick-methods
	 */

	/**
	 * Called on every frame.
	 */
	public void tick() {
		handleCollisions();
		addGravitationForces();
		Iterator<PhysicsObject> registerIterator = spaceRegister.iterator();
		while (registerIterator.hasNext()) {
			PhysicsObject object = registerIterator.next();
			object.playoutForces();
			object.move();
			if (willLeave(object)) {
				registerIterator.remove();
				EventRegistry.fire(new Event(EventType.SIMU_PLANET_LEFT, Arrays.asList(EventCategory.SIMULATION),
						new String[] { "" + simulationID, "" + nor, "" + object.getID() }));
				continue;
			}
		}
		if (spaceRegister.size() <= 1)
			EventRegistry.fire(new Event(EventType.SIMU_END, Arrays.asList(EventCategory.SIMULATION),
					new String[] { "" + simulationID, "" + nor }));
		nor++;
	}

	/**
	 * Adds gravitational forces to each object in the space register.
	 */
	public void addGravitationForces() {
		for (PhysicsObject object : spaceRegister) {
			for (PhysicsObject other : spaceRegister) {
				// If we calculated the force between one object and itself, the
				// force would be infinite.
				if (object == other || Vector2.sqrDistance(object.position, other.position) == 0)
					continue;
				object.appendForce(gravitation(object, other));
			}
		}
	}

	/**
	 * Calculates the new velocities after an elastic collision and sets them
	 * for both involved objects.
	 *
	 * The order of both objects is not important.
	 *
	 * @param one
	 *            The first object to be involved in the collision.
	 * @param two
	 *            The second object to be involved in the collision.
	 *
	 */
	protected void doElasticCollision(PhysicsObject one, PhysicsObject two) {
		System.out.println("doElasticCollision");
		float v1 = one.velocity.magnitude();
		Vector2 v1_vector = one.velocity.copy();
		float v2 = two.velocity.magnitude();
		Vector2 v2_vector = two.velocity.copy();
		one.velocity = v2_vector
				.setMag((one.getMass() * v1 + two.getMass() * (2 * v1 - v2)) / (one.getMass() + two.getMass()));
		two.velocity = v1_vector
				.setMag((two.getMass() * v2 + one.getMass() * (2 * v1 - v2)) / (one.getMass() + two.getMass()));
	}

	/**
	 * Handles collisions.
	 *
	 * If two objects intersect, it is checked weather the gravitation would
	 * lead to a higher velocity towards each other, then the current velocity
	 * separating both objects. If this is the case, then both objects will fuse
	 * together. If it is not the case, doElasticCollision is called with both
	 * objects.
	 */
	public void handleCollisions() {
		ArrayList<PhysicsObject> united = new ArrayList<PhysicsObject>();
		ArrayList<PhysicsObject> remove = new ArrayList<PhysicsObject>();
		ArrayList<PhysicsObject> collided = new ArrayList<PhysicsObject>();

		Iterator<PhysicsObject> registerIterator1 = spaceRegister.iterator();

		while (registerIterator1.hasNext()) {
			PhysicsObject object = registerIterator1.next();
			Iterator<PhysicsObject> registerIterator2 = spaceRegister.iterator();

			while (registerIterator2.hasNext()) {
				PhysicsObject other = registerIterator2.next();

				if (object == other)
					continue;
				if (remove.contains(object) && remove.contains(other))
					continue;

				if (areColliding(object, other)) {
					if (wouldUnite(object, other)) {
						System.out.println("UNITE");
						EventRegistry.fire(new Event(EventType.SIMU_PLANET_UNITE,
								Arrays.asList(EventCategory.SIMULATION),
								new String[] { "" + simulationID, "" + nor, "" + object.getID(), "" + other.getID() }));
						// Unite Objects and remove old
						united.add(PhysicsObject.unite(object, other));
						remove.add(object);
						remove.add(other);
					} else { // Else do an elastic collision
						if (collided.contains(object) && collided.contains(other))
							continue;
						EventRegistry.fire(new Event(EventType.SIMU_PLANET_COLLISION,
								Arrays.asList(EventCategory.SIMULATION),
								new String[] { "" + simulationID, "" + nor, "" + object.getID(), "" + other.getID() }));
						doElasticCollision(object, other);
						collided.add(object);
						collided.add(other);
					}
				}
			}
		}

		for (PhysicsObject unity : united)
			registerPhysicsObject(unity);

		for (PhysicsObject rem : remove)
			unregisterPhysicsObject(rem);
	}

	/*
	 * Static methods
	 */

	/**
	 * TODO: Optimize this into one line.
	 * 
	 * Calculates the force PhysicsObject b acts on PhysicsObject a. Only the
	 * force from b to a is returned.
	 *
	 * @param a
	 *            PhysicsObject
	 * @param b
	 *            PhysicsObject
	 * @return the resulting force as a Vector2
	 */
	public static Vector2 gravitation(PhysicsObject a, PhysicsObject b) {
		float force = (float) Constants.G
				* ((a.getMass() * b.getMass()) / (Vector2.sqrDistance(a.getPosition(), b.getPosition())));
		return Vector2.subtract(b.getPosition(), a.getPosition()).setMag(force);
	}

	/**
	 * Calculates the force an {@link PhysicsObject} needs to escape this space.
	 *
	 * @param a
	 *            PhysicsObject
	 * @param s
	 *            Space
	 * @return The escape velocity.
	 */
	public static float getEscapeVelocity(PhysicsObject a, Space s) {
		return (float) Math
				.sqrt((2f * Constants.G * s.summedMass()) / Vector2.sqrDistance(a.getPosition(), s.pointOfOrigin));
	}

}
