package com.github.infosimulators.polygons;

import java.lang.Math;
import java.util.ArrayList;

import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.polygons.Ray.RelativePoisition;

public class Polygon {
    /** points on the outside */
    protected Vector2[] verticies;
    private float mass = 1f;
    protected Vector2 offset = Vector2.zero();
    protected float size = 1f;

    /**
    * Constructor.
    *
    */
    public Polygon() {
        verticies = new Vector2[] {};
    }

    /**
    * Constructor.
    *
    * @param verticies The verticies of the Polygon.
    */
    public Polygon(Vector2[] verticies) {
        this.verticies = verticies;
    }

    /**
    * Constructor.
    *
    * @param verticies The verticies of the Polygon.
    * @param offset The offset towards the origin.
    */
    public Polygon(Vector2[] verticies, Vector2 offset) {
        this.offset = offset;
        this.verticies = verticies;
    }

    /**
    * Constructor. Generates a new regular N-Polygon
    *
    * @param N The number verticies of thi Polygon.
    */
    public Polygon(float N) {
        super();
        this.verticies = getVerticiesOnCircle(N);
    }

    /**
    * Constructor. Generates a new regular N-Polygon
    *
    * @param N The number verticies of thi Polygon.
    * @param offset The offset towards the origin.
    */
    public Polygon(float N, Vector2 offset) {
        this.offset = offset;
        this.verticies = getVerticiesOnCircle(N);
    }

    /**
    * Constructor. Generates a new regular N-Polygon
    *
    * @param N The number verticies of this Polygon.
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
    * @return A list of all points.
    */
    public Vector2[] getVerticies() {
        Vector2[] temp = verticies;
        for (Vector2 i : temp) {
            i.scale(size).add(offset);
        }
        return temp;
    }

    /**
    * Sets the vertecies new.
    *
    * @param verticies The new verticies.
    */
    public void setVerticies(Vector2[] verticies) {
        this.verticies = verticies;
    }

    /**
    * Adds a vertecies.
    *
    * @param vertex The new verticies.
    */
    public void addVertex(Vector2 vertex) {
        Vector2[] temp = new Vector2[verticies.length + 1];
        for (int i = 0; i < verticies.length; i++) {
            temp[i] = verticies[i];
        }
        temp[-1] = vertex;
        this.verticies = temp;

    }

    /**
     * Gets the number of verticies.
     */
    public int getVerticiesCount() {
        return verticies.length;
    }

    /**
     * Returns an array with all edges this Polygon has.
     * @return An array with all edges of this Polygon.
     */
    public Vector2[] allEdges() {
        ArrayList<Vector2> edges = new ArrayList<Vector2>();
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

    public static Vector2[] getVerticiesOnCircle(float N) {
        ArrayList<Vector2> verticies = new ArrayList<Vector2>();
        double theta = 2 * Math.PI / N;
        for (int i = 0; i < N; ++i) {
            float x = (float) Math.cos(theta * -i + Math.PI * 3 / 4);
            float y = (float) Math.sin(theta * -i + Math.PI * 3 / 4);
            verticies.add(new Vector2(x, y));
        }
        return verticies.toArray(new Vector2[verticies.size()]);
    }
}
