package com.github.infosimulators.physic;

import java.lang.Math;

/**
 * 2D Vector class, stores positions, forces and velocities.
 */
public class Vector2D extends Vector3D {
    @Override
    public String toString() {
        return "[x='" + x + "', y=" + y + "', magnitude='" + magnitude() + "']";
    }

    // Constructors
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0f;
    }

    public Vector2D() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
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
    @Override
    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    //METHODES FOR MANIPULATION
    /**
     * Sets the x and y components.
     *
     * @param x new xcomponent
     * @param y new y component
     * @return this
     */
    public Vector2D Set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Sets the x and y components to the ones of another vector.
     *
     * @param v another vector
     * @return this
     */
    public Vector2D Set(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    /**
     * method to add a vector to this vector;
     *
     * @param vector to add
     * @return this vector
     */
    public Vector2D Add(Vector2D vector) {
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }

    /**
     * method to add floats to this vector;
     *
     * @param x first float
     * @param y second float
     * @return this vector
     */
    public Vector2D Add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * method to subtract a vector to this vector;
     *
     * @param vector
     * @return this vector changed
     */
    public Vector2D Subtract(Vector2D vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        return this;
    }

    /**
     * method to subtract from this vector;
     *
     * @param x first float
     * @param y second float
     * @return this vector changed
     */
    public Vector2D Subtract(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param r the number to multiply with the vector
     * @return this vector changed
     */
    @Override
    public Vector2D Scale(float r) {
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
     * @return this vector changed
     */
    @Override
    public Vector2D Div(float r) {
        x /= r;
        y /= r;
        return this;
    }


    /**
     * normalizes this vector. The vector IS changed.
     *
     * @return this vector changed
     */
    @Override
    public Vector2D Normalize() {
        float magnitude = magnitude();
        if (magnitude != 0) {
            x /= magnitude;
            y /= magnitude;
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
    @Override
    public Vector2D setMag(float len) {
        Normalize();
        Scale(len);
        return this;
    }

    /**
     *
     * Rotate the vector by an angle, magnitude remains the same
     *
     * @brief Rotate the vector by an angle,
     * @param theta the angle of rotation
     * @return this vector changed
     */
    public Vector2D Rotate(float theta) {
        float temp = x;
        x = x * (float) Math.cos(theta) - y * (float) Math.sin(theta);
        y = temp * (float) Math.sin(theta) + y * (float) Math.cos(theta);
        return this;
    }

    /**
     *
     * Linear interpolate the vector to another vector
     *
     * @brief Linear interpolate the vector to another vector
     * @param v the vector to lerp to
     * @param amt The amount of interpolation; some value between 0.0 (old
     * vector) and 1.0 (new vector). 0.1 is very near the old vector; 0.5 is
     * halfway in between.
     * @return this vector changed
     */
    
    public Vector2D Lerp(Vector2D v, float amt) {
        x = x + (v.x - x) * amt;
        y = y + (v.y - y) * amt;
        return this;
    }

    //STATIC/PURE FUNCTIONS
    /**
     * @param a
     * @param b
     * @return the sum of 2 vectors as new Vector;
     */
    public static Vector2D Add(Vector2D a, Vector2D b) {
        return new Vector2D(a.x + b.x, a.y + b.y);
    }

    /**
     * @return the difference of two vectors as new Vector
     * @param a any variable of type Vector2D
     * @param b any variable of type Vector2D
     */
    public static Vector2D Subtract(Vector2D a, Vector2D b) {
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
    public static Vector2D Scale(Vector2D v, float r) {
        return new Vector2D(v.x * r, v.y * r);
    }

    /**
     * Divide a vector by a scalar and return the result in a new vector.
     *
     * @param v the vector to divide by the scalar
     * @param r the scalar
     * @return a new vector that is v / r
     */
    public static Vector2D Div(Vector2D v, float r) {
        return new Vector2D(v.x / r, v.y / r);
    }

    /**
     *
     * Calculates the distance between two points (considering a point as a
     * vector object).
     *
     * @param v1 any variable of type Vector2D
     * @param v2 any variable of type Vector2D
     * @return the Euclidean distance between v1 and v2
     */
    static public float Distance(Vector2D v1, Vector2D v2) {
        float dx = v1.x - v2.x;
        float dy = v1.y - v2.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * method to get the dot product (scalar product) of 2 vectors;
     *
     * @param a
     * @param b
     * @return the dot product
     * @brief Calculate the dot product of two vectors
     */
    public static float Dot(Vector2D a, Vector2D b) {
        return a.x * b.x + a.y * b.y;
    }

    /**
     * @param v1 any variable of type Vector2D
     * @param v2 any variable of type Vector2D
     * @return null
     */
    public static Vector2D Cross(Vector2D v1, Vector2D v2) {
        return null;
    }

    /**
     * Linear interpolate between two vectors (@return a new Vector2D object)
     *
     * @param v1 the vector to start from
     * @param v2 the vector to lerp to
     * @param amt
     * @return
     */
    public static Vector2D lerp(Vector2D v1, Vector2D v2, float amt) {
        Vector2D v = v1.copy();
        v.Lerp(v2, amt);
        return v;
    }

    /**
     * Calculates the angle between two vectors in radiants
     *
     * @param vector1
     * @param vector2
     * @return angle between two vectors in radiants
     */
    public static float AngleBetween(Vector2D vector1, Vector2D vector2) {
        float sin = vector1.x * vector2.y - vector2.x * vector1.y;
        float cos = vector1.x * vector2.x + vector1.y * vector2.y;

        return (float) Math.atan2(sin, cos);
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
