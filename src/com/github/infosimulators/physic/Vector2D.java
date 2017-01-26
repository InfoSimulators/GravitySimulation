package com.github.infosimulators.physic;

import java.lang.Math;
import java.util.Vector;

/**
 * 2D Vector class, stores positions, forces and velocities.
 */
public class Vector2D {
    public float x;
    public float y;

    @Override
    public String toString() {
        return "Vector2D: x='" + x + "', y=" + y + "', magnitude=" + magnitude() + "', direction=" + direction();
    }

    // Constructors
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    //METHODES FOR ATTRIBUTESS

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * method to return the squared length of the vector.
     * Much faster then magitude()
    */
    public float sqrMagnitude() {
        return x * x + y * y;
    }

    /**
     * method to return the squared length of the vector.
    */
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * method to return the direction of the vector in Degree.
    */
    public float direction() {
        return (float) Math.atan(this.x / this.y);
    }

    /**
     * @return a normalized version of this vector. The vector is not changed.
    */
    public Vector2D normalized() {
        Vector2D normalized = new Vector2D();
        float magnitude = magnitude();
        if (magnitude != 0) {
            normalized.x = x / magnitude;
            normalized.y = y / magnitude;
        }
        return normalized;
    }
    //METHODES FOR MANIPULATION

    /**
     * method to scale the vector by a certian times;
    */
    public void scale(float r) {
        this.x = this.x * r;
        this.y = this.y * r;
    }

    /**
     * method to add a vector to this vector;
    */
    public void add(Vector2D vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    /**
     * method to subtract a vector to this vector;
    */
    public void subtract(Vector2D vector) {
        this.x -= vector.x;
        this.y -= vector.y;
    }

    /**
     * method to add to this vector;
    */
    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    /**
     * method to subtract from this vector;
    */
    public void subtract(float x, float y) {
        this.x -= x;
        this.y -= y;
    }

    /**
     * method to get the scaleproduct of this vector and
     * @param :  vector
    */
    public float scaleproduct(Vector2D vector) {
        return this.x * vector.x + this.y * vector.y;
    }

    /**
     * normalizes this vector. The vector IS changed.
    */
    public void normalize() {
        float magnitude = magnitude();
        if (magnitude != 0) {
            x = x / magnitude;
            y = y / magnitude;
        }
    }

    //STATIC/PURE FUNCTIONS
    /**
     * method to get the scaleproduct (? TODO) of 2 vectors;
    */
    public static float scaleproduct(Vector2D a, Vector2D b) {
        return a.x * b.x + a.y * b.y;
    }

    /**
     * method to get
     * @param vector multiplied by
     * @param r
    */
    public static Vector2D scale(Vector2D vector, float r) {
        return new Vector2D(vector.x * r, vector.y * r);
    }

    /**
     * method to return the sum of 2 vectors;
    */
    public static Vector2D add(Vector2D a, Vector2D b) {
        return new Vector2D(a.x + b.x, a.y + b.y);
    }

    /**
     * method to return the difference of
     * @param a , b vectors;
    */
    public static Vector2D subtract(Vector2D a, Vector2D b) {
        return new Vector2D(a.x - b.x, a.y - b.y);
    }

}