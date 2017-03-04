package com.github.infosimulators.physic;

import java.lang.Math;
import processing.core.PVector;

/**
 * Vector class that is limited to x and y components: Can store positions,
 * forces and velocities.
 *
 * @author Julisep
 */
public class Vector2 {
	public float x;
	public float y;

	@Override
	public String toString() {
		return "[x:'" + x + "', y:" + y + "', magnitude:'" + magnitude() + "']";
	}

	/*
	 * Constructors
	 */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2() {
	}

	/*
	 * METHODS FOR ATTRIBUTESS
	 */

	/**
	 * returns the squared length of the vector. Much faster then magitude()
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
	 * @return The angle between this vector and Vector2.right()
	 */
	public float angle(){
		float sin = x * 0 - 1 * y;
		float cos = x * 1 + y * 0;

		return (float) Math.atan2(sin, cos);
	}

	/**
	 * @return a normalized version of this vector. The vector is not changed.
	 */
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
	public Vector2 copy() {
		return new Vector2(x, y);
	}

	/*
	 * METHODS FOR MANIPULATION
	 */
	/**
	* Sets the x and y components.
	*
	* @param x
	*            new xcomponent
	* @param y
	*            new y component
	* @return this
	*/
	public Vector2 set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Sets the x and y components to the ones of another Vector2.
	 *
	 * @param v
	 *            another Vector2
	 * @return this
	 */
	public Vector2 set(Vector2 v) {
		this.x = v.x;
		this.y = v.y;
		return this;
	}

	/**
	 * method to add a Vector3D to this Vector3D;
	 *
	 * @param v
	 *            to add
	 * @return this Vector2
	 */
	public Vector2 add(Vector2 v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}

	/**
	 * method to add floats to this vector;
	 *
	 * @param x
	 *            first float
	 * @param y
	 *            second float
	 * @return this vector
	 */
	public Vector2 add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * method to subtract a vector to this vector;
	 *
	 * @param v
	 * @return this Vector2 changed
	 */
	public Vector2 subtract(Vector2 v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}

	/**
	 * method to subtract floats from this vector;
	 *
	 * @param x
	 *            first float
	 * @param y
	 *            second float
	 * @return this vector changed
	 */
	public Vector2 subtract(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	/**
	 * Multiplies a vector by a scalar.
	 *
	 * @param r
	 *            the number to multiply with the vector
	 * @return this vector changed
	 */
	public Vector2 scale(float r) {
		this.x *= r;
		this.y *= r;
		return this;
	}

	/**
	 *
	 * Divides a vector by a scalar.
	 *
	 * @param r
	 *            the number by which to divide the vector
	 * @return this vector changed
	 */
	public Vector2 div(float r) {
		x /= r;
		y /= r;
		return this;
	}

	/**
	* method to get the dot product (scalar product) of this and anoher vector;
	*
	* @param a Another Vector2.
	* @return the dot product
	*/
	public float dot(Vector2 a) {
		return x * a.x + y * a.y;
	}
	/**
	 * normalizes this vector. The vector IS changed.
	 *
	 * @return this vector changed
	 */
	public Vector2 normalize() {
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
	 * @param len
	 *            the new length for this vector
	 * @return this vector changed
	 */
	public Vector2 setMag(float len) {
		normalize();
		scale(len);
		return this;
	}

	/**
	 *
	 * Linear interpolate the vector to another vector
	 *
	 * @param v
	 *            the vector to lerp to
	 * @param amt
	 *            The amount of interpolation; some value between 0.0 (old
	 *            vector) and 1.0 (new vector). 0.1 is very near the old vector;
	 *            0.5 is halfway in between.
	 * @return this vector changed
	 */
	public Vector2 lerp(Vector2 v, float amt) {
		x = x + (v.x - x) * amt;
		y = y + (v.y - y) * amt;
		return this;
	}

	/**
	 *
	 * Rotate the vector by an angle, magnitude remains the same
	 *
	 * @param theta
	 *            the angle of rotation
	 * @return this vector changed
	 */
	public Vector2 rotate(float theta) {
		float temp = x;
		x = x * (float) Math.cos(theta) - y * (float) Math.sin(theta);
		y = temp * (float) Math.sin(theta) + y * (float) Math.cos(theta);
		return this;
	}

	/*
	 * STATIC/PURE FUNCTIONS
	 */

	public static final Vector2 zero() {
		return new Vector2(0, 0);
	}

	public static final Vector2 up() {
		return new Vector2(0, 1);
	}

	public static final Vector2 down() {
		return new Vector2(0, -1);
	}

	public static final Vector2 left() {
		return new Vector2(-1, 0);
	}

	public static final Vector2 right() {
		return new Vector2(1, 0);
	}

	/**
	 * Converts PVector (Vectorclass form Processing) to Vector2
	 *
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
	 * @param a
	 *            any variable of type Vector2
	 * @param b
	 *            any variable of type Vector2
	 */
	public static Vector2 subtract(Vector2 a, Vector2 b) {
		return new Vector2(a.x - b.x, a.y - b.y);
	}

	/**
	 * Multiplies a vector by a scalar.
	 *
	 * @param v
	 *            the vector to multiply by the scalar
	 * @param r
	 *            the number to multiply with the vector
	 * @return a new vector that is v * r
	 */
	public static Vector2 scale(Vector2 v, float r) {
		return new Vector2(v.x * r, v.y * r);
	}

	/**
	 * Divide a vector by a scalar and return the result in a new vector.
	 *
	 * @param v
	 *            the vector to divide by the scalar
	 * @param r
	 *            the scalar
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
	 * @param v1
	 *            any variable of type Vector2
	 * @param v2
	 *            any variable of type Vector2
	 * @return the Euclidean distance between v1 and v2
	 */
	static public float distance(Vector2 v1, Vector2 v2) {
		float dx = v1.x - v2.x;
		float dy = v1.y - v2.y;
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * Calculates the squared distance between two points (considering a point
	 * as a Vector3D object).
	 *
	 * @param v1
	 *            any variable of type Vector3D
	 * @param v2
	 *            any variable of type Vector3D
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
	 * @param v1
	 *            any variable of type Vector2
	 * @param v2
	 *            any variable of type Vector2
	 * @return null
	 * @deprecated
	 */
	public static Vector2 cross(Vector2 v1, Vector2 v2) {
		return null;
	}

	/**
	 * Linear interpolate between two vectors (@return a new Vector2 object)
	 *
	 * @param v1
	 *            the vector to start from
	 * @param v2
	 *            the vector to lerp to
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
	 * generates a new vector based on the radial system
	 *
	 * @param theta
	 *
	 * @return a new Vector2
	 */
	static public Vector2 radial(float theta) {
		return new Vector2((float) Math.cos(theta), (float) Math.sin(theta));
	}

	/**
	 * generates a new vector based on the radial system
	 *
	 * @param theta
	 * @param magnitude
	 *
	 * @return a new Vector2
	 */
	static public Vector2 radial(float theta, float magnitude) {
		return Vector2.scale(new Vector2((float) Math.cos(theta), (float) Math.sin(theta)), magnitude);
	}

	/**
	* returns one vector reflected to the normal vector
	*
	* @param inDirection The vector to be reflected
	* @param inNormal The normal vector.
	*
	* @return The reflected vector.
	*/
	static public Vector2 reflect(Vector2 inDirection, Vector2 inNormal){
		return Vector2.add(Vector2.scale(inNormal,-2f*Vector2.dot(inDirection,inNormal)),inDirection);
	}
}
