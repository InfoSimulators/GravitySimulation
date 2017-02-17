package com.github.infosimulators.physic;

import java.lang.Math;
import processing.core.PVector;

/**
 * Vector3 class: Can store things like positions, forces and velocities.
 * Used as superclass for @see Vector2.
 *
 * @author Julisep
 */
public class Vector3 {

    public float x = 0f;
    public float y = 0f;
    public float z = 0f;

    @Override
    public String toString() {
        return "[x='" + x + "', y='" + y + "', z=" + z + "', magnitude='" + magnitude() + "']";
    }

    // Constructors
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
    }

    //METHODES FOR ATTRIBUTESS
    /**
     * returns the squared length of the vector. Much faster then magitude()
     *
     * @return squared length of the vector
     */
    public float sqrMagnitude() {
        return x * x + y * y + z * z;
    }

    /**
     * @return length of the vector.
     */
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * @return a normalized version of this vector. The vector is not
     * changed.
     */
    public Vector3 normalized() {
        Vector3 normalized = new Vector3();
        float magnitude = magnitude();
        if (magnitude != 0) {
            normalized.x = x / magnitude;
            normalized.y = y / magnitude;
            normalized.z = z / magnitude;
        }
        return normalized;
    }

    /**
     * @return a copy of this Vector3D
     */
    public Vector3 copy() {
        return new Vector3(x, y, z);
    }

    //METHODES FOR MANIPULATION
    /**
     * Sets the x and y components.
     *
     * @param x new xcomponent
     * @param y new y component
     * @param z
     * @return this
     */
    public Vector3 set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Sets the x and y components to the ones of another Vector3D.
     *
     * @param v another Vector3D
     * @return this
     */
    public Vector3 set(Vector3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        return this;
    }

    /**
     * method to add a Vector3D to this Vector3D;
     *
     * @param v to add
     * @return this Vector3D
     */
    public Vector3 add(Vector3 v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }

    /**
    * method to add floats to this vector;
    *
    * @param x first float
    * @param y second float
    * @return this vector
    */
    public Vector3 add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * method to add floats to this Vector3D;
     *
     * @param x first float
     * @param y second float
     * @param z
     * @return this Vector3D
     */
    public Vector3 add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * method to subtract a vector to this vector;
     *
     * @param v
     * @return this Vector3D changed
     */
    public Vector3 subtract(Vector3 v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        return this;
    }

    /**
    * method to subtract floats from this vector;
    *
    * @param x first float
    * @param y second float
    * @return this vector changed
    */
    public Vector3 subtract(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    /**
     * method to subtract floats from this vector;
     *
     * @param x first float
     * @param y second float
     * @param z third float
     * @return this vector changed
     */
    public Vector3 subtract(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param r the number to multiply with the vector
     * @return this vector changed
     */
    public Vector3 scale(float r) {
        this.x *= r;
        this.y *= r;
        this.z *= r;
        return this;
    }

    /**
     *
     * Divides a vector by a scalar.
     *
     * @param r the number by which to divide the vector
     * @return this vector changed
     */
    public Vector3 div(float r) {
        x /= r;
        y /= r;
        z /= r;
        return this;
    }

    /**
     * normalizes this vector. The vector IS changed.
     *
     * @return this vector changed
     */
    public Vector3 normalize() {
        float magnitude = magnitude();
        if (magnitude != 0) {
            x /= magnitude;
            y /= magnitude;
            z /= magnitude;
        }
        return this;
    }

    /**
     *
     * Set the magnitude of this vector to the value used for the <b>len</b>
     * parameter.
     *
     * @param len the new length for this vector
     * @return this vector changed
     */
    public Vector3 setMag(float len) {
        normalize();
        scale(len);
        return this;
    }

    /**
     *
     * Linear interpolate the vector to another vector
     *
     * @param v the vector to lerp to
     * @param amt The amount of interpolation; some value between 0.0 (old
     * vector) and 1.0 (new vector). 0.1 is very near the old vector; 0.5
     * is halfway in between.
     * @return this vector changed
     */
    public Vector3 lerp(Vector3 v, float amt) {
        x = x + (v.x - x) * amt;
        y = y + (v.y - y) * amt;
        z = z + (v.z - z) * amt;
        return this;
    }

    //STATIC/PURE FUNCTIONS
    public static  Vector3 zero() {
        return  new Vector3(0, 0, 0);
    }
    public static  Vector3 up() {
        return  new Vector3(0, 1, 0);
    }
    public static  Vector3 down() {
        return  new Vector3(0, -1, 0);
    }
    public static  Vector3 left() {
        return  new Vector3(-1, 0, 0);}
    public static  Vector3 right() {
        return  new Vector3(1, 0, 0);
    }
    public static final Vector3 forwards() {
         return  new Vector3(0, 0, 1);
    }
    public static final Vector3 backwards() {
        return new Vector3(0, 0, -1);
    }

    /**
     * Converts PVector (Vectorclass form Processing) to Vector2
     * @param pvector
     * @return transformed vector
     */
    public static Vector3 fromPVector(PVector pvector) {
        return new Vector3(pvector.x, pvector.y, pvector.z);
    }

    /**
     * @param a
     * @param b
     * @return the sum of 2 vector as new vector;
     */
    public static Vector3 add(Vector3 a, Vector3 b) {
        return new Vector3(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    /**
     * @return the difference of two vectors as new vector
     * @param a any variable of type vector
     * @param b any variable of type vector
     */
    public static Vector3 subtract(Vector3 a, Vector3 b) {
        return new Vector3(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param v the vector to multiply by the scalar
     * @param r the number to multiply with the vector
     * @return a new vector that is v * r
     */
    public static Vector3 scale(Vector3 v, float r) {
        return new Vector3(v.x * r, v.y * r, v.z * r);
    }

    /**
     * Divide a vector by a scalar and return the result in a new vector.
     *
     * @param v the vector to divide by the scalar
     * @param r the scalar
     * @return a new vector that is v / r
     */
    public static Vector3 div(Vector3 v, float r) {
        return new Vector3(v.x / r, v.y / r, v.z / r);
    }

    /**
     *
     * Calculates the distance between two points (considering a point as a
     * Vector3D object).
     *
     * @param v1 any variable of type Vector3D
     * @param v2 any variable of type Vector3D
     * @return the Euclidean distance between v1 and v2
     */
    static public float distance(Vector3 v1, Vector3 v2) {
        float dx = v1.x - v2.x;
        float dy = v1.y - v2.y;
        float dz = v1.z - v2.z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Calculates the squared distance between two points (considering a point as a
     * Vector3D object).

     * @param v1 any variable of type Vector3D
     * @param v2 any variable of type Vector3D
     * @return squared Euclidean distance between v1 and v2
     */
    static public float sqrDistance(Vector3 v1, Vector3 v2) {
        float dx = v1.x - v2.x;
        float dy = v1.y - v2.y;
        float dz = v1.z - v2.z;
        return (float) dx * dx + dy * dy + dz * dz;
    }

    /**
     * method to get the dot product (scalar product) of 2 vectors;
     *
     * @param a
     * @param b
     * @return the dot product
     */
    public static float dot(Vector3 a, Vector3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    /**
     * @param v1 any variable of type Vector3D
     * @param v2 any variable of type Vector3D
     * @return null
     * @deprecated
     */
    public static Vector3 cross(Vector3 v1, Vector3 v2) {
        return null;
    }

    /**
     * Linear interpolate between two vectors
     *
     * @param v1 the vector to start from
     * @param v2 the vector to lerp to
     * @param amt
     * @return a new Vector3D object
     */
    public static Vector3 lerp(Vector3 v1, Vector3 v2, float amt) {
        Vector3 v = v1.copy();
        v.lerp(v2, amt);
        return v;
    }

    /**
    * @return a new vector based on
    * @param angle around y;
    * @param angle around z between
    *
    */
    static public Vector3 fromAngle(float alpha, float beta) {
        return new Vector3((float) Math.cos(alpha) * (float) Math.cos(beta), (float) Math.sin(alpha)* (float)Math.cos(beta), (float) Math.sin(beta));
    }
}
