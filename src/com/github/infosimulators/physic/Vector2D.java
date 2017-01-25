package com.github.infosimulators.physic;

import java.lang.Math;
/**
 * 2D Vector class, stores positions, forces and velocities.
 */
public class Vector2D {
    public float this.x;
    public float this.y;
// Constructors
	public Vector2D(float x, float y) {
		this.x = x;
        this.y = y;
	}
    public Vector2D() {
		this.x = 0;
        this.y = 0;
	}

    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * method to return the squared length of the vector.
	 * Much faster then magitude()
    */
    public float sqrMagnitude(){
        return this.x*2 + this.y*2
    }

    /**
     * method to return the squared length of the vector.
    */
    public float magitude(){
        return Math.sqrt(this.x*2 + this.y*2);
    }

    /**
     * method to multiply the vector by a certian times;
    */
    public void multiply(float r){
        this.x = this.x*r;
        this.y = this.y*r;
    }

    /**
     * method to add a vector to this vector;
    */
    public void add(Vector2D vector){
        this.x += vector.getX();
        this.y += vector.getY());
    }

    /**
     * method to add to this vector;
    */
    public void add(float x, float y){
        this.x += x;
        this.y += y;
    }

    /**
     * method to get the scaleproduct of this vector and
     * @param :  vector
    */
    public float scaleproduct(Vector2D vector){
        return this.x*vector.x + this.y*vector.y;
    }


    // static
    /**
     * method to get the scaleproduct (? TODO) of 2 vectors;
    */
    public static float scaleproduct(Vector2D a, Vector2D b){
        return a.x*b.x + a.y*b.y;
    }
    /**
     * method to return the sum of 2 vectors;
    */
    public static Vector2D r_add(Vector2D a, Vector2D b){
        return new Vector2D(a.x + b.x , a.y + b.y);
    }

}