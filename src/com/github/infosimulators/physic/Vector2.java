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
	 * METHODS FOR ATTRIBUTES
	 */

	/**
	 * returns the squared length of the vector. Much faster then magnitude()
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
	public float angle() {
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
	 * @return The first normal.
	 */
	public Vector2 getNormal1() {
		return new Vector2(-y, x);
	}

	/**
	 * @return The second normal.
	 */
	public Vector2 getNormal2() {
		return new Vector2(y, -x);
	}

	/**
	 * @return A new {@link PolarVector2} based on this {@link Vector2}.
	 */
	public PolarVector2 toRadial() {
		return new PolarVector2(angle(), magnitude());
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
	 *            The new x component.
	 * @param y
	 *            The new y component.
	 * @return this
	 */
	public Vector2 set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Sets the x and y components to the ones of the {@link Vector2} v.
	 *
	 * @param v
	 *            Another {@link Vector2}.
	 * @return this
	 */
	public Vector2 set(Vector2 v) {
		this.x = v.x;
		this.y = v.y;
		return this;
	}

	/**
	 * Add a {@link Vector2} to this {@link Vector2};
	 *
	 * @param v
	 *            The {@link Vector2} to add.
	 * @return this
	 */
	public Vector2 add(Vector2 v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}

	/**
	 * Adds floats to this vector.
	 *
	 * @param x
	 *            First float.
	 * @param y
	 *            Second float.
	 * @return this
	 */
	public Vector2 add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * Subtracts a vector from this vector;
	 *
	 * @param v
	 * @return this
	 */
	public Vector2 subtract(Vector2 v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}

	/**
	 * Subtracts floats from this vector.
	 *
	 * @param x
	 *            First float.
	 * @param y
	 *            Second float.
	 * @return this
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
	 *            The number to multiply with the vector.
	 * @return this
	 */
	public Vector2 scale(float r) {
		this.x *= r;
		this.y *= r;
		return this;
	}

	/**
	 * Multiplies a vector's components with the ones another vector provides.
	 *
	 * @param r
	 *            The vector those parts to multiply with the vectors
	 *            corresponding parts individually.
	 * @return this
	 */
	public Vector2 scaleIndividual(Vector2 r) {
		this.x *= r.x;
		this.y *= r.y;
		return this;
	}

	/**
	 *
	 * Divides a vector by a scalar.
	 *
	 * @param r
	 *            The number by which to divide the vector.
	 * @return this
	 */
	public Vector2 div(float r) {
		x /= r;
		y /= r;
		return this;
	}

	/**
	 * Divides a vectors components by the ones another vector provides.
	 *
	 * @param r
	 *            The vector by which parts to divide the vectors corresponding
	 *            parts individually.
	 * @return this
	 */
	public Vector2 divIndividual(Vector2 r) {
		this.x /= r.x;
		this.y /= r.y;
		return this;
	}

	/**
	 * @param a
	 *            Another Vector2.
	 * @return The dot product (scalar product) of this and another
	 *         {@link Vector2}.
	 */
	public float dot(Vector2 a) {
		// return (x * a.x + y * a.y) * (float) Math.cos(angleBetween(this, a));
		return (x * a.x + y * a.y);
	}

	/**
	 * Normalizes this vector.
	 *
	 * @return this
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
	 *            The new length for this vector.
	 * @return this
	 */
	public Vector2 setMag(float len) {
		normalize();
		scale(len);
		return this;
	}

	/**
	 *
	 * Linear interpolate the vector to another vector.
	 *
	 * @param v
	 *            the vector to interpolate to
	 * @param amt
	 *            The amount of interpolation; some value between 0.0 (old
	 *            vector) and 1.0 (new vector). 0.1 is very near the old vector;
	 *            0.5 is halfway in between.
	 * @return this
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
	 *            The angle of rotation.
	 * @return this
	 */
	public Vector2 rotate(float theta) {
		float temp = x;
		x = x * (float) Math.cos(theta) - y * (float) Math.sin(theta);
		y = temp * (float) Math.sin(theta) + y * (float) Math.cos(theta);
		return this;
	}

	/**
	 * Converts this into a {@link PVector} (Vectorclass form Processing).
	 *
	 * @return {@link PVector}
	 */
	public PVector toPVector() {
		return new PVector(x, y);
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

	public static final Vector2 one() {
		return new Vector2(1, 1);
	}

	/**
	 * Converts {@link PVector} (Vectorclass of Processing) to {@link Vector2}
	 *
	 * @param pvector
	 * @return {@link Vector2}
	 */
	public static Vector2 fromPVector(PVector pvector) {
		return new Vector2(pvector.x, pvector.y);
	}

	/**
	 * @param a
	 * @param b
	 * @return The sum of 2 vectors as new {@link Vector2}.
	 */
	public static Vector2 add(Vector2 a, Vector2 b) {
		return new Vector2(a.x + b.x, a.y + b.y);
	}

	/**
	 * @param a
	 *            Any variable of type {@link Vector2}.
	 * @param b
	 *            Any variable of type {@link Vector2}.
	 * @return the difference of two vectors as new Vector
	 */
	public static Vector2 subtract(Vector2 a, Vector2 b) {
		return new Vector2(a.x - b.x, a.y - b.y);
	}

	/**
	 * Multiplies a vector by a scalar.
	 *
	 * @param v
	 *            The vector to multiply by the scalar.
	 * @param r
	 *            The number to multiply with the vector.
	 * @return a new vector that is v * r
	 */
	public static Vector2 scale(Vector2 v, float r) {
		return new Vector2(v.x * r, v.y * r);
	}

	/**
	 * Multiplies a vector's components with the ones another vector provides.
	 * Equals new {@link Vector2}(v.x*r.x, v.y*r.y).
	 *
	 * @param v
	 *            One vector.
	 * @param r
	 *            Another vector.
	 * @return this
	 */
	public static Vector2 scaleIndividual(Vector2 v, Vector2 r) {
		return new Vector2(v.x * r.x, v.y * r.y);
	}

	/**
	 * Divide a vector by a scalar and return the result in a new vector. Equals
	 * new {@link Vector2}(v.x/r, v.y/r).
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
	 * Divides a vectors components by the ones another vector provides. Equals
	 * new {@link Vector2}(v.x/r, v.y/r).
	 *
	 * @param v
	 *            One vector.
	 * @param r
	 *            Another vector.
	 * @return this
	 */
	public static Vector2 divIndividual(Vector2 v, Vector2 r) {
		return new Vector2(v.x / r.x, v.y / r.y);
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
	 *            Any variable of type {@link Vector2}.
	 * @param v2
	 *            Any variable of type {@link Vector2}.
	 * @return The squared Euclidean distance between v1 and v2.
	 */
	static public float sqrDistance(Vector2 v1, Vector2 v2) {
		float dx = v1.x - v2.x;
		float dy = v1.y - v2.y;
		return (float) dx * dx + dy * dy;
	}

	/**
	 * Method to get the dot product (scalar product) of 2 vectors;
	 *
	 * @param a
	 * @param b
	 * @return The dot product.
	 */
	public static float dot(Vector2 a, Vector2 b) {
		// return (a.x * b.x + a.y * b.y) * (float) Math.cos(angleBetween(a,
		// b));
		return (a.x * b.x + a.y * b.y);
	}

	/**
	 * @param v1
	 *            Any variable of type {@link Vector2}.
	 * @param v2
	 *            Any variable of type {@link Vector2}.
	 * @return null
	 * @deprecated
	 */
	public static Vector2 cross(Vector2 v1, Vector2 v2) {
		return null;
	}

	/**
	 * Linear interpolate between two {@link Vector2}.
	 *
	 * @param v1
	 *            The vector to start from.
	 * @param v2
	 *            The vector to interpolate to.
	 * @param amt
	 *            The amount of interpolating.
	 * @return The interpolated {@link Vector2}.
	 */
	public static Vector2 lerp(Vector2 v1, Vector2 v2, float amt) {
		Vector2 v = v1.copy();
		v.lerp(v2, amt);
		return v;
	}

	/**
	 * Calculates the angle between two vectors in radians
	 *
	 * @param vector1
	 *            Any variable of type {@link Vector2}.
	 * @param vector2
	 *            Any variable of type {@link Vector2}.
	 * @return The angle between two vectors in radians.
	 */
	public static float angleBetween(Vector2 vector1, Vector2 vector2) {
		float sin = vector1.x * vector2.y - vector2.x * vector1.y;
		float cos = vector1.x * vector2.x + vector1.y * vector2.y;

		return (float) Math.atan2(sin, cos);
	}

	/**
	 * @param v
	 *            A {@link Vector2}.
	 *
	 * @return A new {@link PolarVector2} based on the given {@link Vector2}.
	 */
	public static PolarVector2 toRadial(Vector2 v) {
		return new PolarVector2(v.angle(), v.magnitude());
	}

	/**
	 * returns one vector reflected to the normal vector
	 *
	 * @param inDirection
	 *            The vector to be reflected
	 * @param inNormal
	 *            The normal vector.
	 *
	 * @return The reflected vector.
	 */
	static public Vector2 reflect(Vector2 inDirection, Vector2 inNormal) {
		return Vector2.add(Vector2.scale(inNormal, -2f * Vector2.dot(inDirection, inNormal)), inDirection);
	}
}
