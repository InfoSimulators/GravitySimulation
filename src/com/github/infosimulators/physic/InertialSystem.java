package com.github.infosimulators.physic;

import java.util.*;
import java.lang.Math;

/**
 * 2D Vector class, stores positions, forces and velocities.
 */
public class InertialSystem {
    protected int length;
    protected int width;
    public ArrayList<PhysicsObject> objectsInSpace;
    protected float deltaTime;

    // Constructors
    public InertialSystem(int length, int width) {
        this.length = length;
        this.width = width;
        objectsInSpace = new ArrayList<PhysicsObject>();
        deltaTime = 1f;
    }

    public InertialSystem(int length, int width, float deltaTime) {
        this.length = length;
        this.width = width;
        this.deltaTime = deltaTime;
        objectsInSpace = new ArrayList<PhysicsObject>();
    }

    public int getLength() {
        return this.length;
    }

    public int getWidth() {
        return this.width;
    }

    public float getDeltaTime() {
        return this.deltaTime;
    }

    public void setDeltaTime(float deltaTime) {
        if (deltaTime <= 0) {
            new Err("deltaTime can´t be below / or 0.");
            return;
        }
        this.deltaTime = deltaTime;
    }

    public void registerPhysicsObject(PhysicsObject object) {
        objectsInSpace.add(object);
    }

    public void unregisterPhysicsObject(PhysicsObject object) {
        objectsInSpace.remove(object);
    }

    public void update() {
        addGravitationForces();
        getCollisions();
        for (PhysicsObject object : objectsInSpace) {
            object.playoutForces();
            object.move(deltaTime);
        }
        getCollisions();
    }

    public void addGravitationForces() {
        for (PhysicsObject object : objectsInSpace) {
            for (PhysicsObject other : objectsInSpace) {
                if (object == other)
                    return;
                object.appendForce(gravitationForce);
            }
        }
    }

    public void getCollisions() {
        for (PhysicsObject object : objectsInSpace) {
            for (PhysicsObject other : objectsInSpace) {

                /*If both objects overlap*/
                Vector2D gravitationForce = gravitation(object, other);
                if (Vector2D.subtract(object.position, other.position).magnitude() <= object.size + other.size) {
                    /*If the gravitation is stronger than velocity*/
                    if (Vector2D.subtract(object.velocity, other.velocity)
                            .sqrMagnitude() < gravitationForce.sqrMagnitude() * deltaTime) {
                        //Unite Objects and remove old
                        ArrayList<Vector2D> forces = new ArrayList<Vector2D>();
                        forces.addAll(object.forces);
                        forces.addAll(other.forces);
                        objectsInSpace.add(new PhysicsObject(
                                Vectror2D.add(Vector2D.subtract(object.position, other.position).scale(0.5f),
                                        object.position),
                                Vector2D.add(object.velocity, other.velocity), object.mass + other.mass,
                                (float) Math.cbrt(Math.pow(object.size, 3) + Math.pow(other.size, 3)), forces));
                        objectsInSpace.remove(object);
                        objectsInSpace.remove(other);
                    } else { /*Else do and elastic collision*/
                        Vector2D objectVelocityOld = object.velocity;
                        Vector2D otherVelocityOld = other.velocity;
                        object.velocity = otherVelocityOld;
                        other.velocity = objectVelocityOld;
                    }
                }
            }
        }
    }

    public static Vector2D gravitation(PhysicsObject a, PhysicsObject b) {
        float force = (float) (6.673 * (Math.pow(10, -11))
                * ((a.mass * b.mass) / Math.pow(Vector2D.subtract(a.position, b.position).magnitude(), 2)));
        /*System.out.println("force: " + force);
        System.out.println("normalized: " + Vector2D.subtract(a.position, b.position).normalized());*/
        return Vector2D.scale(Vector2D.subtract(a.position, b.position).normalized(), force);
    }
}