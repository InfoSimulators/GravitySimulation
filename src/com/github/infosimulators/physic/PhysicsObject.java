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
	}

    public void appendForce(Vector2D force){
        forces.add(force);
    }
    public void resetForces(){
        forces = new ArrayList<Vector2D>();
    }
    public void playoutForces(PhysicsObject object,float deltaTime){
        float sumX = 0f;
        float sumY = 0f;

        for(Vector2D force: forces){
            sumX += force.getX();
            sumY += force.getY();
        }
        velocity = velocity.add((sumX/mass)*deltaTime, (sumY/mass)*deltaTime);
    }
    public void move(float deltaTime){

    }
}