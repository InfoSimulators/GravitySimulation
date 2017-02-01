package com.github.infosimulators.physic;

import java.lang.Math;

/**
 * 2D Vector3D class, stores positions, forces and velocities.
 */
public class Vector3D {

    public float x;
    public float y;
    public float z;

    @Override
    public String toString() {
        return "[x='" + x + "', y='" + y + "', z=" + z + "', magnitude='" + magnitude() + "']";
    }

    // Constructors
    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
    }

//METHODES FOR ATTRIBUTESS
    /**
     * returns the squared length of the Vector3D. Much faster then magitude()
     *
     * @return squared length of the Vector3D
     */
    public float sqrMagnitude() {
        return x * x + y * y + z * z;
    }

    /**
     * @return length of the Vector3D.
     */
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * @return a normalized version of this Vector3D. The Vector3D is not
     * changed.
     */
    public Vector3D normalized() {
        Vector3D normalized = new Vector3D();
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
     *
     * @brief Get a copy of this Vector3D
     */
    public Vector3D copy() {
        return new Vector3D(x, y, z);
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
    public Vector3D Set(float x, float y, float z) {
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
    public Vector3D Set(Vector3D v) {
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
    public Vector3D Add(Vector3D v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
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
    public Vector3D Add(float x, float y, float z) {
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
    public Vector3D Subtract(Vector3D v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        return this;
    }

    /**
     * method to subtract from this vector;
     *
     * @param x first float
     * @param y second float
     * @param z
     * @return this vector changed
     */
    public Vector3D Subtract(float x, float y, float z) {
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
    public Vector3D Scale(float r) {
        this.x *= r;
        this.y *= r;
        this.z *= r;
        return this;
    }

    /**
     *
     * Divides a vector by a scalar.
     *
     * @brief Divide a vector by a scalar
     * @param r the number by which to divide the vector
     * @return this vector changed
     */
    public Vector3D Div(float r) {
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
    public Vector3D Normalize() {
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
     * @brief Set the magnitude of the vector
     * @return this vector changed
     */
    public Vector3D setMag(float len) {
        Normalize();
        Scale(len);
        return this;
    }


    /**
     *
     * Linear interpolate the vector to another vector
     *
     * @brief Linear interpolate the vector to another vector
     * @param v the vector to lerp to
     * @param amt The amount of interpolation; some value between 0.0 (old
     * vector) and 1.0 (new vector). 0.1 is very near the old vector; 0.5
     * is halfway in between.
     * @return this vector changed
     */
    public Vector3D Lerp(Vector3D v, float amt) {
        x = x + (v.x - x) * amt;
        y = y + (v.y - y) * amt;
        z = z + (v.z - z) * amt;
        return this;
    }

    //STATIC/PURE FUNCTIONS
    /**
     * @param a
     * @param b
     * @return the sum of 2 vector as new vector;
     */
    public static Vector3D Add(Vector3D a, Vector3D b) {
        return new Vector3D(a.x + b.x, a.y + b.y, a.z +b.z);
    }

    /**
     * @return the difference of two vectors as new vector
     * @param a any variable of type vector
     * @param b any variable of type vector
     */
    public static Vector3D Subtract(Vector3D a, Vector3D b) {
        return new Vector3D(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @brief Multiply a vector by a scalar
     * @param v the vector to multiply by the scalar
     * @param r the number to multiply with the vector
     * @return a new vector that is v * r
     */
    public static Vector3D Scale(Vector3D v, float r) {
        return new Vector3D(v.x * r, v.y * r, v.z * r);
    }

    /**
     * Divide a vector by a scalar and return the result in a new vector.
     *
     * @param v the vector to divide by the scalar
     * @param r the scalar
     * @return a new vector that is v / r
     */
    public static Vector3D Div(Vector3D v, float r) {
        return new Vector3D(v.x / r, v.y / r, v.z / r);
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
    static public float Distance(Vector3D v1, Vector3D v2) {
        float dx = v1.x - v2.x;
        float dy = v1.y - v2.y;
        float dz = v1.z - v2.z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * method to get the dot product (scalar product) of 2 vectors;
     *
     * @param a
     * @param b
     * @return the dot product
     * @brief Calculate the dot product of two vectors
     */
    public static float Dot(Vector3D a, Vector3D b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    /**
     * @param v1 any variable of type Vector3D
     * @param v2 any variable of type Vector3D
     * @return null
     */
    public static Vector3D Cross(Vector3D v1, Vector3D v2) {
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
    public static Vector3D lerp(Vector3D v1, Vector3D v2, float amt) {
        Vector3D v = v1.copy();
        v.Lerp(v2, amt);
        return v;
    }
}
