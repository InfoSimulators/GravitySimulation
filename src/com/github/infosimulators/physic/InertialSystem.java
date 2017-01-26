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

    // Constructors
    public InertialSystem(int length, int width) {
        this.length = length;
        this.width = width;
        objectsInSpace = new ArrayList<PhysicsObject>();
    }

    public int getLength() {
        return this.length;
    }

    public int getWidth() {
        return this.width;
    }
    public void registerPhysicsObject(PhysicsObject object){
        objectsInSpace.add(object);
    }
    public void unregisterPhysicsObject(PhysicsObject object){
        objectsInSpace.remove(object);
    }
    public void update() {
        /*
        foreach: find Gravitation between objectsInSpace
                 append force to Object
                 if hit : switch new OBJECTS

        foreach: objectsInSpace move
        */
    }

    public void gravTick() {
        for (PhysicsObject object : objectsInSpace) {
            for (PhysicsObject other : objectsInSpace) {
                if (object == other)
                    return;
                object.appendForce(gravitation(object,other));
            }
        }
    }
    public static Vector2D gravitation(PhysicsObject a, PhysicsObject b ){
        float force = (float) (6.673*(Math.pow(10,-11)) * ((a.mass * b.mass)/ Math.pow(Vector2D.subtract(a.position, b.position).magnitude(),2)));
        System.out.println("force: " + force);
        System.out.println("normalized: "+Vector2D.subtract(a.position, b.position).normalized());
        return Vector2D.scale(Vector2D.subtract(a.position, b.position).normalized(),force);
    }
}