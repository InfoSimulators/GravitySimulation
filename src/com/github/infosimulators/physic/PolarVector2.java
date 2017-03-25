package com.github.infosimulators.physic;

import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import processing.core.PVector;

/**
 * Vector class that defines a vector by it´s length and it´s rotation around
 * the z-axis: Can store positions, forces and velocities.
 *
 * @author Julisep
 */
public class PolarVector2 {

	private static Comparator<PolarVector2> comparator = (PolarVector2 a, PolarVector2 b) -> {
		return (a.theta > b.theta ? -1 : 1);
	};

	/**
	 * The rotation around the z-axis
	 */
	public float theta;

	/**
	 * The length of this {@link PolarVector2}
	 */
	public float r;

	/*
	 * Constructors
	 */

	public PolarVector2(float theta, float r) {
		this.theta = theta;
		this.r = r;
	}

	public PolarVector2(float theta) {
		this.theta = theta;
		this.r = 1;
	}

	public PolarVector2() {
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
		return r * r;
	}

	/**
	 * @return length of the vector.
	 */
	public float magnitude() {
		return r;
	}

	/**
	 * @return The angle between this vector and Vector2.right()
	 */
	public float angle() {
		return theta;
	}

	/**
	 * @return a normalized version of this vector. The vector is not changed.
	 */
	public PolarVector2 normalized() {
		PolarVector2 normalized = new PolarVector2();
		if (r != 0) {
			normalized.r = 1;
		}
		return normalized;
	}

	/**
	 * @return The first normal.
	 */
	public PolarVector2 getNormal1() {
		return new PolarVector2(theta + (float) Math.PI, 1);
	}

	/**
	 * @return The second normal.
	 */
	public PolarVector2 getNormal2() {
		return new PolarVector2(theta - (float) Math.PI, 1);
	}

	/**
	 * @return A new {@link Vector2} based on this {@link PolarVector2}.
	 */
	public Vector2 toCartesian() {
		return new Vector2((float) Math.cos(theta), (float) Math.sin(theta)).scale(r);
	}

	/**
	 * @return a copy of this vector
	 */
	public PolarVector2 copy() {
		return new PolarVector2(theta, r);
	}

	/*
	 * METHODS FOR MANIPULATION
	 */

	/**
	 * Sets theta and length.
	 *
	 * @param theta
	 *            The new angle;
	 * @param r
	 *            The new length.
	 * @return this
	 */
	public PolarVector2 set(float theta, float r) {
		this.theta = theta;
		this.r = r;
		return this;
	}

	/**
	 * Sets the x and y components to the ones of another {@link PolarVector2}.
	 *
	 * @param v
	 *            Another {@link PolarVector2}.
	 * @return this
	 */
	public PolarVector2 set(PolarVector2 v) {
		this.theta = v.theta;
		this.r = v.r;
		return this;
	}

	/**
	 * Adds rotation to this vector.
	 *
	 * @param theta
	 *            The rotation to add.
	 * @return this
	 */
	public PolarVector2 addRotation(float theta) {
		this.theta += theta;
		return this;
	}

	/**
	 * Subtracts rotation from this vector.
	 *
	 * @param theta
	 *            The rotation to subtract.
	 * @return this
	 */
	public PolarVector2 subtractRotation(float theta) {
		this.theta -= theta;
		return this;
	}

	/**
	 * Multiplies this vector by a scalar.
	 *
	 * @param r
	 *            The scalar to multiply.
	 * @return this
	 */
	public PolarVector2 scale(float r) {
		this.r *= r;
		return this;
	}

	/**
	 * Divides a vector by a scalar.
	 *
	 * @param r
	 *            The scalar by which to divide.
	 * @return this
	 */
	public PolarVector2 div(float r) {
		this.r /= r;
		return this;
	}

	/**
	 * Method to get the dot product (scalar product) of this and another
	 * vector.
	 *
	 * @param a
	 *            Another Vector2.
	 * @return the dot product
	 */
	public float dot(PolarVector2 a) {
		return r * a.r * (float) Math.cos(theta - a.theta);
	}

	/**
	 * Normalizes this vector.
	 *
	 * @return this
	 */
	public PolarVector2 normalize() {
		r = 1;
		return this;
	}

	/**
	 * Set the magnitude of this vector to the value used for the <b>len</b>
	 * parameter.
	 *
	 * @param len
	 *            The new length for this PolarVector2.
	 * @return this
	 */
	public PolarVector2 setMag(float len) {
		r = len;
		return this;
	}

	/**
	 * Interpolates this vector linear to another vector.
	 *
	 * @param v
	 *            The vector to lerp to.
	 * @param amt
	 *            The amount of interpolation; some value between 0.0 (old
	 *            vector) and 1.0 (new vector). 0.1 is very near the old vector;
	 *            0.5 is halfway in between.
	 * @return this
	 */
	public PolarVector2 lerp(PolarVector2 v, float amt) {
		theta = theta + (v.theta - theta) * amt;
		r = r + (v.r - r) * amt;
		return this;
	}

	/**
	 * Rotates this vector by an angle. The magnitude remains the same.
	 *
	 * @param theta
	 *            The angle of rotation.
	 * @return this
	 */
	public PolarVector2 rotate(float theta) {
		this.theta += theta;
		return this;
	}

	/*
	 * STATIC FUNCTIONS
	 */

	public static final PolarVector2 zero() {
		return new PolarVector2(0f, 0f);
	}

	public static final PolarVector2 up() {
		return new PolarVector2((float) (0.5f * Math.PI), 1f);
	}

	public static final PolarVector2 down() {
		return new PolarVector2((float) (1.5f * Math.PI), 1f);
	}

	public static final PolarVector2 left() {
		return new PolarVector2((float) (Math.PI), 1f);
	}

	public static final PolarVector2 right() {
		return new PolarVector2(0f, 1f);
	}

	/**
	 * Converts PVector (Vectorclass of Processing) to Vector2
	 *
	 * @param pvector
	 * @return Collidered vector
	 */
	public static PolarVector2 fromPVector(PVector pv) {
		return new PolarVector2((float) Math.atan2(pv.x * 0 - 1 * pv.y, pv.x * 1 + pv.y * 0), pv.mag());
	}

	/**
	 * Multiplies a vector by a scalar. Equals new {@link PolarVector2}(v.theta,
	 * v.r*r).
	 *
	 * @param v
	 *            The vector to multiplied by the scalar.
	 * @param r
	 *            The scalar to multiply with.
	 * @return A new vector that is v * r.
	 */
	public static PolarVector2 scale(PolarVector2 v, float r) {
		return new PolarVector2(v.theta, v.r * r);
	}

	/**
	 * Divide a vector by a scalar and return the result in a new vector. Equals
	 * new {@link PolarVector2}(v.theta, v.r/r).
	 *
	 * @param v
	 *            The vector to divide by the scalar.
	 * @param r
	 *            The scalar to divide by.
	 * @return A new vector that is v / r.
	 */
	public static PolarVector2 div(PolarVector2 v, float r) {
		return new PolarVector2(v.theta, v.r / r);
	}

	/**
	 *
	 * Calculates the distance between two points (considering a point as a
	 * {@link PolarVector2}).
	 *
	 * @param v1
	 *            Any variable of type {@link PolarVector2}.
	 * @param v2
	 *            Any variable of type {@link PolarVector2}.
	 * @return The Euclidean distance between v1 and v2.
	 */
	public static float distance(PolarVector2 v1, PolarVector2 v2) {
		return (float) Math.sqrt(v1.r * v1.r + v2.r * v2.r - 2f * v1.r * v2.r * (float) Math.cos(v1.theta - v2.theta));

	}

	/**
	 * Calculates the squared distance between two points (considering a point
	 * as a {@link PolarVector2}).
	 *
	 * @param v1
	 *            Any variable of type {@link PolarVector2}.
	 * 
	 * @param v2
	 *            Any variable of type {@link PolarVector2}.
	 * 
	 * @return The squared Euclidean distance between v1 and v2.
	 */
	public static float sqrDistance(PolarVector2 v1, PolarVector2 v2) {
		return v1.r * v1.r + v2.r * v2.r - 2f * v1.r * v2.r * (float) Math.cos(v1.theta - v2.theta);
	}

	/**
	 * @param a
	 * @param b
	 * @return The dot product of 2 {@link PolarVector2}.
	 */
	public static float dot(PolarVector2 a, PolarVector2 b) {
		return a.r * b.r * (float) Math.cos(a.theta - b.theta);
	}

	/**
	 * Interpolates two vectors linear.
	 *
	 * @param v1
	 *            The vector to lerp from.
	 * @param v2
	 *            The vector to lerp to.
	 * @param amt
	 *            The amount of interpolation; some value between 0.0 (old
	 *            vector) and 1.0 (new vector). 0.1 is very near the old vector;
	 *            0.5 is halfway in between.
	 * @return The resulting {@link PolarVector2}.
	 */
	public static PolarVector2 lerp(PolarVector2 v1, PolarVector2 v2, float amt) {
		return new PolarVector2(v1.theta + (v2.theta - v1.theta) * amt, v1.r + (v2.r - v1.r) * amt);
	}

	/**
	 * Calculates the angle between two vectors in radians.
	 *
	 * @param v1
	 *            The first {@link PolarVector2}.
	 * @param v2
	 *            The second {@link PolarVector2}.
	 * @return The angle between the two vectors in radians.
	 */
	public static float angleBetween(PolarVector2 v1, PolarVector2 v2) {
		return (float) Math.abs(v1.theta - v2.theta);
	}

	/**
	 * @param v
	 *            The {@link PolarVector2} to transform.
	 * @return A new {@link Vector2} based on the given {@link PolarVector2}.
	 */
	public static Vector2 toCartesian(PolarVector2 v) {
		return new Vector2((float) Math.cos(v.theta), (float) Math.sin(v.theta)).scale(v.r);
	}

	/**
	 * Orders the {@link PolarVector2}s from the array in counterclockwise
	 * direction.
	 * 
	 * @param unsorted
	 *            The array with unsorted {@link PolarVector2}s.
	 * @return An array with sorted {@link PolarVector2}s.
	 */
	public static PolarVector2[] order(PolarVector2[] unsorted) {
		List<PolarVector2> list = Arrays.asList(unsorted);
		Collections.sort(list, comparator);
		PolarVector2[] sorted = new PolarVector2[list.size()];
		list.toArray(sorted);
		return sorted;
	}

	/**
	 * Orders the {@link PolarVector2}s from the array in counterclockwise
	 * direction.
	 * 
	 * @param unsorted
	 *            The List with unsorted {@link PolarVector2}s.
	 * @return An array with sorted {@link PolarVector2}s.
	 */
	public static PolarVector2[] order(List<PolarVector2> unsorted) {
		Collections.sort(unsorted, comparator);
		PolarVector2[] sorted = new PolarVector2[unsorted.size()];
		unsorted.toArray(sorted);
		return sorted;
	}
	
}
