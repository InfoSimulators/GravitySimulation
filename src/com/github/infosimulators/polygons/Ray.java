package com.github.infosimulators.polygons;

import com.github.infosimulators.physic.Vector2;

/**
 * A class to store information about lines in space.
 *
 * It is kind of an extension  for {@link Vector2}.
 */
public class Ray {

	/**
	 * Enum to store position relative to the line.
	 */
	public enum RelativePosition {
		ABOVE, UNDER, ON, NEXT_TO
	};

	/**
	 * Base vector of the ray.
	 */
	Vector2 a = Vector2.zero();

	/**
	 * Directional vector of the ray.
	 */
	Vector2 v;

	/**
	 * Constructor.
	 *
	 * @param a
	 *            The base {@link Vector2}.
	 */
	public Ray(Vector2 a, Vector2 v) {
		this.a = a;
		this.v = v;
	}

	/**
	 * Constructor.
	 *
	 * @param a
	 *            The base {@link Vector2}.
	 * @param v
	 *            The direction {@link Vector2}.
	 */
	public Ray(Vector2 v) {
		this.v = v;
	}

	/**
	 * @param r
	 *            The parameter to insert.
	 * @return The {@link Vector2} resulting from inserting r.
	 */
	public Vector2 insert(float r) {
		return Vector2.add(a, Vector2.scale(v, r));
	}

	/**
	 * @param point
	 *            The point to check.
	 * @return If the point is on this ray.
	 */
	public boolean isPointOnRay(Vector2 point) {
		Vector2 relative = Vector2.subtract(point, a);
		float r1 = relative.x / v.x;
		float r2 = relative.y / v.y;
		return r1 == r2;
	}

	/**
	 * @return The closest way between a point and the ray as {@link Vector2} or
	 *         Vector2(0,0) if the point is on the ray.
	 *
	 * @param point
	 *            The point from which the way is searched.
	 */
	public Vector2 getShortestWay(Vector2 point) {
		if (isPointOnRay(point))
			return Vector2.zero();
		point = Vector2.subtract(point, a);
		float b = Vector2.dot(point, v) / Vector2.dot(v, v);
		return insert(b);
	}

	/**
	 * @return The first normal.
	 */
	public Vector2 getNormal1() {
		return new Vector2(-v.y, v.x);
	}

	/**
	 * @return The second normal.
	 */
	public Vector2 getNormal2() {
		return new Vector2(v.y, -v.x);
	}

	/**
	 * Returns the relative position of the point towards the Vector.
	 *
	 * It checks wether the angle of the closest path is larger then pi.
	 *
	 * @param point
	 *            The point.
	 * @return The relative position of the point as {@link RelativePosition}.
	 */
	public RelativePosition getRelativePosition(Vector2 point) {
		Vector2 closest = getShortestWay(point);
		if (closest == Vector2.zero())
			return RelativePosition.ON;
		float angle = closest.angle();
		if (angle > (float) Math.PI && angle < (float) 2 * Math.PI)
			return RelativePosition.UNDER;
		else if (angle > 0 && angle < (float) Math.PI)
			return RelativePosition.ABOVE;
		else if (angle == (float) Math.PI || angle == 0)
			return RelativePosition.NEXT_TO;
		// SHOULD NEVER BE REACHED WITH VALID ARGUMENTS
		return RelativePosition.ON;
	}

	/**
	 * Returns a new Ray based on 2 points.
	 *
	 * @param point1
	 *            The first point.
	 * @param point2
	 *            The second point.
	 * @return A ray through both points.
	 */
	public static Ray fromTwoPoints(Vector2 point1, Vector2 point2) {
		return new Ray(point1, Vector2.subtract(point2, point1));
	}

}
