package com.github.infosimulators.physic;

import java.util.*;
/**
 * 2D Vector class, stores positions, forces and velocities.
 */
public class PhysicsObject{
    public Vector2D position;
    public Vector2D velocity;
    protected ArrayList<Vector2D> forces;
    public float mass;

// Constructors
    public PhysicsObject() {
        forces = new ArrayList<Vector2D>();
        position = new Vector2D(0f,0f);
        velocity = new Vector2D(0f,0f);
        mass = 0f;
    }
    public PhysicsObject(Vector2D position) {
        forces = new ArrayList<Vector2D>();
        position = position;
        velocity = new Vector2D(0f,0f);
        mass = 0f;
    }
    public PhysicsObject(Vector2D position,Vector2D velocity) {
        forces = new ArrayList<Vector2D>();
        this.position = position;
        this.velocity = velocity;
        mass = 0f;
    }
    public PhysicsObject(Vector2D position,Vector2D velocity, float mass) {
        forces = new ArrayList<Vector2D>();
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
    }
    public PhysicsObject(Vector2D position, float mass) {
        forces = new ArrayList<Vector2D>();
        this.position = position;
        this.velocity = new Vector2D(0f,0f);;
        this.mass = mass;
    }
    public PhysicsObject(Vector2D position,Vector2D velocity, float mass, ArrayList<Vector2D> forces) {
        this.forces = forces;
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
    }
// FUNCTIONS
    public void appendForce(Vector2D force){
        forces.add(force);
    }
    public void resetForces(){
        forces = new ArrayList<Vector2D>();
    }
    public void playoutForces(float deltaTime){
        float sumX = 0f;
        float sumY = 0f;

        for(Vector2D force: forces){
            sumX += force.x;
            sumY += force.y;
        }
        velocity.add((sumX/mass)*deltaTime, (sumY/mass)*deltaTime);
    }
    public void move(float deltaTime){
        position = Vector2D.scale(velocity, deltaTime);
    }
}