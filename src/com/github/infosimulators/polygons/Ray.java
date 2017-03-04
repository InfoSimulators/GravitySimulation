package com.github.infosimulators.polygons;

import com.github.infosimulators.physic.Vector2;

/**
 * A class to store information about lines in space.
 */
public class Ray {
    /**
     * Enum to store position relative to the line.
     */
    public enum RelativePoisition {
        ABOVE, UNDER, ON
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
     * @param a The base {@link Vector2}.
     */
    public Ray(Vector2 a, Vector2 v) {
        this.a = a;
        this.v = v;
    }

    /**
     * Constructor.
     * @param a The base {@link Vector2}.
     * @param v The dirction {@link Vector2}.
     */
    public Ray(Vector2 v) {
        this.v = v;
    }

    /**
     * Returns the vector resulting from inserting r.
     *
     * @param r The parameter to insert.
     * @return The resulting {@link Vector2}.
     */
    public Vector2 insert(float r) {
        return Vector2.add(a, Vector2.scale(v, r));
    }

    /**
    * Returns if point is part of the ray.
    *
    * @param point The point to check.
    * @return If the point is on this ray.
    */
    public boolean isPointOnRay(Vector2 point) {
        Vector2 relative = Vector2.subtract(point, a);
        float r1 = relative.x / v.x;
        float r2 = relative.y / v.y;
        return r1 == r2;
    }

    /**
     * Returns the closest way between a point and the ray.
     * Returns Vector2(0,0) if the point is on the ray.
     *
     * @param point The point from which the way is searched.
     * @return The shortest way as {@link Vector2}.
     */
    public Vector2 getShortestWay(Vector2 point) {
        if (isPointOnRay(point))
            return Vector2.zero();
        point = Vector2.subtract(point, a);
        float b = Vector2.dot(point, v) / Vector2.dot(v, v);
        return insert(b);
    }

    /**
     * Returns the relative position of the point towards the Vector.
     *
     * It checks wether the angle of the closest path is larger then pi.
     *
     * @param point The point.
     * @return The reative position of the point as {@link RelativePoisition}.
     */
    public RelativePoisition getRelativePosition(Vector2 point) {
        Vector2 closest = getShortestWay(point);
        if (closest == Vector2.zero())
            return RelativePoisition.ON;
        float angle = closest.angle();
        if (angle > (float) Math.PI % (float) Math.PI)
            return RelativePoisition.UNDER;
        else if (angle > 0 && angle < (float) Math.PI)
            return RelativePoisition.ABOVE;
        // else if (angle == 0 || angle == (float) Math.PI)
        return RelativePoisition.ON;
    }

    /**
     * Returns a new Ray based on 2 points.
     *
     * @param point1 The first point.
     * @param point2 The secound point.
     * @return A ray through both points.
     */
    public static Ray fromTwoPoints(Vector2 point1, Vector2 point2) {
        return new Ray(point1, Vector2.subtract(point2, point1));
    }
}