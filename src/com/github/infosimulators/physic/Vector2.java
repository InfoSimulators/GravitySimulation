package com.github.infosimulators.physic;

import java.lang.Math;
import processing.core.PVector;

/**
 * Vector class that is limited to x and y components: Can store positions, forces and velocities.
 *
 * @author Julisep
 */
public class Vector2 extends Vector3 {
    @Override
    public String toString() {
        return "[x:'" + x + "', y:" + y + "', magnitude:'" + magnitude() + "']";
    }

    // Constructors
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
    }

    //METHODES FOR ATTRIBUTESS
    /**
     * returns the squared length of the vector. Much faster then magitude()
     *
     * @return squared length of the vector
     */
    @Override
    public float sqrMagnitude() {
        return x * x + y * y;
    }

    /**
     * @return length of the vector.
     */
    @Override
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * @return a normalized version of this vector. The vector is not changed.
     */
    @Override
    public Vector2 normalized() {
        Vector2 normalized = new Vector2();
        float magnitude = magnitude();
        if (magnitude != 0) {
            normalized.x = x / magnitude;
            normalized.y = y / magnitude;
        }
        return normalized;
    }

    /**
     * @return a copy of this vector
     */
    @Override
    public Vector2 copy() {
        return new Vector2(x, y);
    }

    //METHODES FOR MANIPULATION
    /**
     *
     * Rotate the vector by an angle, magnitude remains the same
     *
     * @param theta the angle of rotation
     * @return this vector changed
     */
    public Vector2 rotate(float theta) {
        float temp = x;
        x = x * (float) Math.cos(theta) - y * (float) Math.sin(theta);
        y = temp * (float) Math.sin(theta) + y * (float) Math.cos(theta);
        return this;
    }

    //STATIC/PURE FUNCTIONS
    public static final Vector2 zero = new Vector2(0, 0);
    public static final Vector2 up = new Vector2(0, 1);
    public static final Vector2 down = new Vector2(0, -1);
    public static final Vector2 left = new Vector2(-1, 0);
    public static final Vector2 right = new Vector2(1, 0);

    /**
     * Converts PVector (Vectorclass form Processing) to Vector2
     * @param pvector
     * @return transformed vector
     */
    public static Vector2 fromPVector(PVector pvector) {
        return new Vector2(pvector.x, pvector.y);
    }

    /**
     * @param a
     * @param b
     * @return the sum of 2 vectors as new Vector;
     */
    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    /**
     * @return the difference of two vectors as new Vector
     * @param a any variable of type Vector2
     * @param b any variable of type Vector2
     */
    public static Vector2 subtract(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param v the vector to multiply by the scalar
     * @param r the number to multiply with the vector
     * @return a new vector that is v * r
     */
    public static Vector2 scale(Vector2 v, float r) {
        return new Vector2(v.x * r, v.y * r);
    }

    /**
     * Divide a vector by a scalar and return the result in a new vector.
     *
     * @param v the vector to divide by the scalar
     * @param r the scalar
     * @return a new vector that is v / r
     */
    public static Vector2 div(Vector2 v, float r) {
        return new Vector2(v.x / r, v.y / r);
    }

    /**
     *
     * Calculates the distance between two points (considering a point as a
     * vector object).
     *
     * @param v1 any variable of type Vector2
     * @param v2 any variable of type Vector2
     * @return the Euclidean distance between v1 and v2
     */
    static public float distance(Vector2 v1, Vector2 v2) {
        float dx = v1.x - v2.x;
        float dy = v1.y - v2.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calculates the squared distance between two points (considering a point as a
     * Vector3D object).
     *
     * @param v1 any variable of type Vector3D
     * @param v2 any variable of type Vector3D
     * @return squared Euclidean distance between v1 and v2
     */
    static public float sqrDistance(Vector2 v1, Vector2 v2) {
        float dx = v1.x - v2.x;
        float dy = v1.y - v2.y;
        return (float) dx * dx + dy * dy;
    }

    /**
     * method to get the dot product (scalar product) of 2 vectors;
     *
     * @param a
     * @param b
     * @return the dot product
     */
    public static float dot(Vector2 a, Vector2 b) {
        return a.x * b.x + a.y * b.y;
    }

    /**
     * @param v1 any variable of type Vector2
     * @param v2 any variable of type Vector2
     * @return null
     * @deprecated
     */
    public static Vector2 cross(Vector2 v1, Vector2 v2) {
        return null;
    }

    /**
     * Linear interpolate between two vectors (@return a new Vector2 object)
     *
     * @param v1 the vector to start from
     * @param v2 the vector to lerp to
     * @param amt
     * @return
     */
    public static Vector2 lerp(Vector2 v1, Vector2 v2, float amt) {
        Vector2 v = v1.copy();
        v.lerp(v2, amt);
        return v;
    }

    /**
     * Calculates the angle between two vectors in radiants
     *
     * @param vector1
     * @param vector2
     * @return angle between two vectors in radiants
     */
    public static float angleBetween(Vector2 vector1, Vector2 vector2) {
        float sin = vector1.x * vector2.y - vector2.x * vector1.y;
        float cos = vector1.x * vector2.x + vector1.y * vector2.y;

        return (float) Math.atan2(sin, cos);
    }

    /**
     * @return a new vector based on
     * @param angle ;
     *
     */
    static public Vector2 fromAngle(float angle) {
        return new Vector2((float) Math.cos(angle), (float) Math.sin(angle));
    }

}
