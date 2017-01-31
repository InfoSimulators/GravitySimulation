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
        return "[x='" + x + "', y=" + y + "', magnitude='" + magnitude() + "', direction='" + direction() + "']";
    }

    // Constructors
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this.x = 0f;
        this.y = 0f;
    }

//METHODES FOR ATTRIBUTESS
    /**
     * Sets the x and y components.
     * @param new x component
     * @param new y component
     * @return this
     */
    public Vector2D set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Sets the x and y components to the ones of another vector.
     * @param another vector
     * @return this
     */
    public void set(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    /**
     * returns the squared length of the vector.
     * Much faster then magitude()
     *
     * @return squared length of the vector
    */
    public float sqrMagnitude() {
        return x * x + y * y;
    }

    /**
     * @return length of the vector.
    */
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * @return direction of the vector in degree.
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

    /**
    * @return a copy of this vector
    *
    * @brief Get a copy of this vector
    */
    public Vector2D copy() {
        return new Vector2D(x, y);
    }
    //METHODES FOR MANIPULATION

    /**
     * method to add a vector to this vector;
     * @return this vector
    */
    public Vector2D add(Vector2D vector) {
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }

    /**
     * method to add to this vector;
     *  @return this vector
    */
    public Vector2D add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * method to subtract a vector to this vector;
     * @return this vector
    */
    public Vector2D subtract(Vector2D vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        return this;
    }

    /**
     * method to subtract from this vector;
     * @return ths vector
    */
    public Vector2D subtract(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param n the number to multiply with the vector
     * @return this vector
    */
    public Vector2D scale(float r) {
        this.x *= r;
        this.y *= r;
        return this;
    }

    /**
    *
    * Divides a vector by a scalar.
    *
    * @brief Divide a vector by a scalar
    * @param r the number by which to divide the vector
    * @return this vector
    */
    public Vector2D div(float r) {
        x /= r;
        y /= r;
        return this;
    }

    /**
    * Calculates the dot product of two vectors.
    *
    * @param second vector
    */
    public float dot(Vector2D vector) {
        return this.x * vector.x + this.y * vector.y;
    }

    /**
    * @param x x component of the other vector
    * @param y y component of the other vector
    * @param z z component of the other vector
    */
    public float dot(float x, float y, float z) {
        return this.x * x + this.y * y + this.z * z;
    }

    /**
    * Calculates the cross product of this vector and another vector.
    * @deprecated as there are no cross products for 2 dimensional vectors.
    * Kept for compability with a future Vector3D class.
    *
    * @param v : another vector;
    * @return null
    */
    public Vector2D cross(Vector2D v) {
        return null;
    }

    /**
     * normalizes this vector. The vector IS changed.
     * @return this
    */
    public Vector2D normalize() {
        float magnitude = magnitude();
        if (magnitude != 0) {
            x /= magnitude;
            y /= magnitude;
        }
        return this;
    }

    /**
    *
    * Set the magnitude of this vector to the value used for the <b>len</b> parameter.
    *
    * @param len the new length for this vector
    * @brief Set the magnitude of the vector
    * @return this
    */
    public Vector2D setMag(float len) {
        normalize();
        scale(len);
        return this;
    }

    /**
     *
     * Rotate the vector by an angle, magnitude remains the same
     *
     * @brief Rotate the vector by an angle,
     * @param theta the angle of rotation
     * @return this
     */
    public Vector2D rotate(float theta) {
        float temp = x;
        x = x * Math.cos(theta) - y * Math.sin(theta);
        y = temp * Math.sin(theta) + y * Math.cos(theta);
        return this;
    }

    /**
    *
    * Linear interpolate the vector to another vector
    *
    * @brief Linear interpolate the vector to another vector
    * @param v the vector to lerp to
    * @param amt  The amount of interpolation; some value between 0.0 (old vector) and 1.0 (new vector). 0.1 is very near the old vector; 0.5 is halfway in between.
    * @return this
    */
    public Vector2D lerp(Vector2D v, float amt) {
        x = x + (v.x - x) * amt;
        y = y + (v.y - y) * amt;
        return this;
    }

    //STATIC/PURE FUNCTIONS
    /**
     * @return the sum of 2 vectors;
    */
    public static Vector2D add(Vector2D a, Vector2D b) {
        return new Vector2D(a.x + b.x, a.y + b.y);
    }

    /**
     * @return the difference of two vectors
     * @param a any variable of type Vector2D
     * @param b any variable of type Vector2D
    */
    public static Vector2D subtract(Vector2D a, Vector2D b) {
        return new Vector2D(a.x - b.x, a.y - b.y);
    }

    /**
    * Multiplies a vector by a scalar.
    *
    * @brief Multiply a vector by a scalar
    * @param v the vector to multiply by the scalar
    * @param r the number to multiply with the vector
    * @return a new vector that is v * r
    */
    public static Vector2D scale(Vector2D vector, float r) {
        return new Vector2D(vector.x * r, vector.y * r);
    }

    /**
     * Divide a vector by a scalar and return the result in a new vector.
     * @param v the vector to divide by the scalar
     * @param r the scalar
     * @return a new vector that is v / r
     */
    public static Vector2D div(Vector2D v, float r) {
        return new Vector2D(v.x / r, v.y / r);
    }

    /**
     *
     * Calculates the distance between two points (considering a
     * point as a vector object).
     *
     * @param v1 any variable of type Vector2D
     * @param v2 any variable of type Vector2D
     * @return the Euclidean distance between v1 and v2
     */
    static public float dist(Vector2D v1, Vector2D v2) {
        float dx = v1.x - v2.x;
        float dy = v1.y - v2.y;
        float dz = v1.z - v2.z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * method to get the dot product (scalar product) of 2 vectors;
     *
     * @return the dot product
     * @brief Calculate the dot product of two vectors
     */
    public static float dot(Vector2D a, Vector2D b) {
        return a.x * b.x + a.y * b.y;
    }

    /**
     * @param v1 any variable of type Vector2D
     * @param v2 any variable of type Vector2D
     * @param target PVector to store the result
     * @return null
     */
    public static Vector2D cross(Vector2D v1, Vector2D v2) {
        return null;
    }

    /**
    * Linear interpolate between two vectors (@return a new Vector2D object)
    * @param v1 the vector to start from
    * @param v2 the vector to lerp to
    */
    public static Vector2D lerp(Vector2D v1, Vector2D v2, float amt) {
        Vector2D v = v1.copy();
        v.lerp(v2, amt);
        return v;
    }

    /**
    * @return a new vector based on
    * @param angle ;
    *
    */
    static public Vector2D fromAngle(float angle) {
        return new Vector2D((float) Math.cos(angle), (float) Math.sin(angle));
    }

}