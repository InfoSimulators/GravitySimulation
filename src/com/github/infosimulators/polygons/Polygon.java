package com.github.infosimulators.polygons;

import java.lang.Math;
import java.util.ArrayList;

import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.physic.PolarVector2;
import com.github.infosimulators.polygons.Ray.RelativePoisition;
import com.github.infosimulators.polygons.regular.Sphere;

/**
 * A class to store simple convex polygons.
 */
public class Polygon {
    /** points on the outside */
    protected PolarVector2[] verticies;
    /** The mass of this polygon. Included to specify a density in polygongroups */
    private float mass = 1f;
    private Vector2 offset = Vector2.zero();
    private float size = 1f;

    /**
    * Constructor.
    *
    */
    public Polygon() {
        verticies = new PolarVector2[] {};
    }

    /**
    * Constructor.
    *
    * @param verticies The verticies of the Polygon.
    */
    public Polygon(PolarVector2[] verticies) {
        this.verticies = PolarVector2.order(verticies);
    }

    /**
    * Constructor.
    *
    * @param verticies The verticies of the Polygon.
    * @param offset The offset towards the origin.
    */
    public Polygon(PolarVector2[] verticies, Vector2 offset) {
        this.offset = offset;
        this.verticies = PolarVector2.order(verticies);
    }

    /**
    * Constructor. Generates a new regular N-Polygon
    *
    * @param N The number verticies of this polygon.
    */
    public Polygon(float N) {
        super();
        this.verticies = getVerticiesOnCircle(N);
    }

    /**
    * Constructor. Generates a new regular N-Polygon
    *
    * @param N The number verticies of this polygon.
    * @param offset The offset towards the origin.
    */
    public Polygon(float N, Vector2 offset) {
        this.offset = offset;
        this.verticies = getVerticiesOnCircle(N);
    }

    /**
    * Constructor. Generates a new regular N-Polygon
    *
    * @param N The number verticies of this polygon.
    * @param offset The offset towards the origin.
    * @param size The size of this object.
    */
    public Polygon(float N, Vector2 offset, float size) {
        this.offset = offset;
        this.size = size;
        this.verticies = getVerticiesOnCircle(N);
    }

    /**
    * @return The center of mass of this polygon.
    */
    public Vector2 center() {
        return offset;
    }

    /**
     * @returns {@link Polygon.offset}.
     */
    public Vector2 getOffset() {
        return offset;
    }

    /**
    * Sets the offset new.
    *
    * @param offset The new offset.
    */
    public void setOffset(Vector2 offset) {
        this.offset = offset;
    }

    /**
    * Sets the offset new.
    *
    * @param offset The new offset.
    */
    public void setOffset(float x, float y) {
        this.offset = new Vector2(x, y);
    }

    /**
    * @returns {@link Polygon.size}.
    */
    public float getSize() {
        return size;
    }

    /**
    * Sets the size new.
    *
    * @param size The new size.
    */
    public void setSize(float size) {
        this.size = size;
    }

    /**
    * @returns {@link Polygon.mass}.
    */
    public float getMass() {
        return mass;
    }

    /**
    * Sets the mass new.
    *
    * @param mass The new size.
    */
    public void setMass(float mass) {
        this.mass = mass;
    }

    /**
    * @return A list of all points relative to world space (with size and offest applied).
    */
    public Vector2[] getVerticies() {
        Vector2[] temp = new Vector2[verticies.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = verticies[i].toCartesian().scale(size).add(offset);
        }
        return temp;
    }

    /**
    * @return A list of all points relative to local space.
    */
    public PolarVector2[] getLocalVerticies() {
        return verticies;
    }

    /**
    * Sets the vertecies new. <b>Use with caution</b>.
    *
    * @param verticies The new verticies relative to local space as {@link PolarVector2}.
    */
    public void setVerticies(PolarVector2[] verticies) {
        this.verticies = PolarVector2.order(verticies);
    }

    /**
    * Adds a vertex.
    *   TODO
    * @param vertex The new vertex relative to local space.
    */
    public void addVertex(PolarVector2 vertex) {
        PolarVector2[] temp = new PolarVector2[verticies.length + 1];
        for (int i = 0; i < verticies.length; i++) {
            temp[i] = verticies[i];
        }
        temp[temp.length - 1] = vertex;
        this.verticies = PolarVector2.order(temp);
    }

    /**
     * Gets the number of verticies.
     */
    public int getVerticiesCount() {
        return getVerticies().length;
    }

    /**
     * Returns an array with all edges this polygon has.
     * @return An array with all edges of this polygon.
     */
    public Vector2[] allEdges() {
        ArrayList<Vector2> edges = new ArrayList<Vector2>();
        Vector2[] verticies = getVerticies();
        for (int i = 0; i < verticies.length; i++) {
            if (i == 0) {
                continue;
            } else if (i == verticies.length - 1) {
                Vector2.subtract(verticies[i], verticies[0]);
            } else {
                edges.add(Vector2.subtract(verticies[i], verticies[i + 1]));
            }
        }
        return edges.toArray(new Vector2[edges.size()]);
    }

    /**
    * Checks if two Polygon intersect.
    * @param a One Polygon.
    * @param b Another Polygon.
    * @return If two collliders intersect.
    */
    public static boolean intersect(Polygon one, Polygon two) {
        if (one == two)
            return true;
        for (Vector2 vertice1 : one.getVerticies()) {
            for (Vector2 vertice2 : two.getVerticies()) {
                Ray line = Ray.fromTwoPoints(vertice1, vertice2);
                RelativePoisition s1 = line.getRelativePosition(one.offset);
                RelativePoisition s2 = line.getRelativePosition(two.offset);
                if (s1 == RelativePoisition.ON && s2 == RelativePoisition.ON)
                    continue;
                if (s1 != s2) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
    * Checks if two polygons intersect with the seperating axis theorem.
    * @param a One Polygon.
    * @param b Another Polygon.
    * @return If two polygons intersect.
     */
    public static boolean intersectSAT(Polygon p1, Polygon p2) {
        if (p1 instanceof Sphere && p2 instanceof Sphere) {
            if (p1.getSize() + p2.getSize() >= Vector2.distance(p1.getOffset(), p2.getOffset()))
                return false;
        } else if (p1 instanceof Sphere || p2 instanceof Sphere) {

            Polygon sphere = p1 instanceof Sphere ? p1 : p2;
            Polygon polygon = p1 instanceof Sphere ? p2 : p1;
            Vector2[] polygon_verticies = polygon.getVerticies();
            Vector2 closest = Vector2.zero();
            float min_distance = Float.NEGATIVE_INFINITY;
            for (int i = 0; i < polygon.getVerticiesCount(); i++)
                if (Vector2.distance(polygon_verticies[i], sphere.getOffset()) > min_distance) {
                    min_distance = Vector2.distance(polygon_verticies[i], sphere.getOffset());
                    closest = polygon_verticies[i];
                }

            Vector2 axis = Vector2.subtract(sphere.getOffset(), closest);
            ArrayList<Float> projectedPoints = new ArrayList<Float>();
            for (int i = 0; i < polygon.getVerticiesCount(); i++)
                projectedPoints.add(polygon_verticies[i].copy().dot(axis));

            float min1 = getMin(projectedPoints);
            float max1 = getMax(projectedPoints);
            float min2 = sphere.getOffset().dot(axis);
            float max2 = sphere.getOffset().dot(axis);
            if (max1 < min2 || max2 < min1)
                return false;

        } else {
            ArrayList<Vector2> normals = new ArrayList<Vector2>();
            Vector2[] p1_verticies = p1.getVerticies();
            Vector2[] p2_verticies = p2.getVerticies();
            int p1_verticies_count = p1.getVerticiesCount();
            int p2_verticies_count = p2.getVerticiesCount();
            //recover normal vectors for p1 and p2
            for (int i = 0; i < p1_verticies_count; i++) {
                if (i < p1_verticies_count - 1) {
                    float x = p1_verticies[i + 1].x - p1_verticies[i].x;
                    float y = p1_verticies[i + 1].y - p1_verticies[i].y;
                    normals.add(new Vector2(x, y).getNormal1());
                } else {
                    float x = p1_verticies[0].x - p1_verticies[i].x;
                    float y = p1_verticies[0].y - p1_verticies[i].y;
                    normals.add(new Vector2(x, y).getNormal1());
                }
            }

            for (int i = 0; i < p2_verticies_count; i++) {
                if (i < p2_verticies_count - 1) {
                    float x = p2_verticies[i + 1].x - p2_verticies[i].x;
                    float y = p2_verticies[i + 1].y - p2_verticies[i].y;
                    normals.add(new Vector2(x, y).getNormal1());
                } else {
                    float x = p2_verticies[0].x - p2_verticies[i].x;
                    float y = p2_verticies[0].y - p2_verticies[i].y;
                    normals.add(new Vector2(x, y).getNormal1());
                }
            }

            //project points of p1 and p2 on each normal vector until a gap is found

            for (int n = 0; n < normals.size(); n++) {
                ArrayList<Float> projectedPoints1 = new ArrayList<Float>();
                ArrayList<Float> projectedPoints2 = new ArrayList<Float>();

                for (int i = 0; i < p1_verticies_count; i++)
                    projectedPoints1
                            .add(new Vector2(p1_verticies[i].x, p1_verticies[i].y).dot(normals.get(n)));

                for (int i = 0; i < p2_verticies_count; i++)
                    projectedPoints2
                            .add(new Vector2(p2_verticies[i].x, p2_verticies[i].y).dot(normals.get(n)));

                float min1 = getMin(projectedPoints1);
                float max1 = getMax(projectedPoints1);
                float min2 = getMin(projectedPoints2);
                float max2 = getMax(projectedPoints2);

                System.out.println(normals.get(n));
                System.out.println(min1 + " " + max1);
                System.out.println(min2 + " " + max2);
                System.out.println((max1 - min1) + " " + (max2 - min1));
                if (max1 < min2 || max2 < min1)
                    return false;
            }
        }
        return true;
    }

    private static float getMin(ArrayList<Float> list) {
        float min = list.get(0);
        for (float f : list)
            min = f < min ? f : min;
        return min;
    }

    private static float getMax(ArrayList<Float> list) {
        float max = list.get(0);
        for (float f : list)
            max = f > max ? f : max;
        return max;
    }

    public static PolarVector2[] getVerticiesOnCircle(float N) {
        ArrayList<PolarVector2> verticies = new ArrayList<PolarVector2>();
        double theta = 2 * Math.PI / N;
        for (int i = 0; i < N; ++i) {
            verticies.add(new PolarVector2((float) theta * i));
        }
        return verticies.toArray(new PolarVector2[verticies.size()]);
    }
}
