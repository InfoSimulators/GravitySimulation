package com.github.infosimulators.physic;

import java.util.*;
import java.lang.Math;
/**
 * 2D Vector class, stores positions, forces and velocities.
 */
public class IntertialSystem {
    protected int length;
    protected int width;
    public ArrayList<PhysicsObject> objectsInSpace;

    // Constructors
	public IntertialSystem(int length, int width) {
		this.length = length;
        this.width = width;
        objectsInSpace = new ArrayList<PhysicsObject>();
	}

    public int getLength(){
        return this.length;
    }

    public int getWidth(){
        return this.width;
    }

    public void update(){
        /*
        foreach: find Gravitation between objectsInSpace
                 append force to Object
                 if hit : switch new OBJECTS

        foreach: objectsInSpace move
        */
    }

}