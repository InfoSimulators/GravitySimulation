package com.github.infosimulators.physic;

import java.util.*;

/**
 * Baseclass for all objects, manipulated by physics.
 * stores position, forces, velocity, mass and Size
 * It is assumed to be a sphere with the radius size with the mass located in the middle
 */
public class PhysicsObject {
    public Vector3 position = new Vector3();
    public Vector3 velocity = new Vector3();
    public Vector3 acceleration = new Vector3();
    protected ArrayList<Vector3> forces = new ArrayList<Vector3>();
    public float mass = 1f;
    public float size = 1f;

    @Override
    public String toString() {
        return "PhysicsObject:{\n position: '" + position + "',\n velocity: '" + velocity + "',\n mass: '" + mass
                + "',\n size: '" + size + "'\n}";
    }

    // Constructors
    public PhysicsObject() {
    }

    public PhysicsObject(Vector3 position) {
        position = position;
    }

    public PhysicsObject(Vector3 position, Vector3 velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public PhysicsObject(Vector3 position, Vector3 velocity, float mass) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
    }

    public PhysicsObject(Vector3 position, Vector3 velocity, float mass, float size) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.size = size;
    }

    public PhysicsObject(Vector3 position, float mass) {
        this.position = position;
        this.mass = mass;
        this.size = 0f;
    }

    public PhysicsObject(Vector3 position, float mass, float size) {
        this.position = position;
        this.mass = mass;
        this.size = size;
    }

    public PhysicsObject(Vector3 position, Vector3 velocity, float mass, ArrayList<Vector3> forces) {
        this.forces = forces;
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
    }

    public PhysicsObject(Vector3 position, Vector3 velocity, float mass, float size, ArrayList<Vector3> forces) {
        this.forces = forces;
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.size = size;
    }

    // FUNCTIONS
    public void appendForce(Vector3 force) {
        forces.add(force);
    }

    public void resetForces() {
        forces.clear();
    }

    public void playoutForces() {
        Vector3 sum = new Vector3();
        for (Vector3 force : forces) {
            sum.add(force);
        }
        acceleration = sum.div(mass);
        resetForces();
    }
    /**
     * @param time between calculationcircles
     */
    public void move() {
        position.add(Vector3.scale(acceleration, 1/2).add(velocity));
        velocity.add(acceleration);
    }
}