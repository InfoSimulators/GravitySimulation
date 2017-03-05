package com.github.infosimulators.polygons;

import java.lang.Math;
import java.util.ArrayList;

import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.polygons.Ray.RelativePoisition;

public class PolygonCollider {
    /** points on the outside */
    protected Vector2[] verticies;
    private float mass = 1f;
    protected Vector2 offset = Vector2.zero();
    protected float size = 1f;

    /**
    * Constructor.
    *
    */
    public PolygonCollider() {
        verticies = new Vector2[] {};
    }

    /**
    * Constructor.
    *
    * @param verticies The verticies of the PolygonCollider.
    */
    public PolygonCollider(Vector2[] verticies) {
        this.verticies = verticies;
    }

    /**
    * Constructor.
    *
    * @param verticies The verticies of the PolygonCollider.
    * @param offset The offset towards the origin.
    */
    public PolygonCollider(Vector2[] verticies, Vector2 offset) {
        this.offset = offset;
        this.verticies = verticies;
    }

    /**
    * Constructor. Generates a new regular N-PolygonCollider
    *
    * @param N The number verticies of thi PolygonCollider.
    */
    public PolygonCollider(float N) {
        super();
        ArrayList<Vector2> verticies = new ArrayList<Vector2>();
        double theta = 2 * Math.PI / N;
        for (int i = 0; i < N; ++i) {
            float x = (float) Math.cos(theta * i);
            float y = (float) Math.sin(theta * i);
            verticies.add(new Vector2(x, y));
        }
        this.verticies = verticies.toArray(new Vector2[verticies.size()]);
    }

    /**
    * Constructor. Generates a new regular N-PolygonCollider
    *
    * @param N The number verticies of thi PolygonCollider.
    * @param offset The offset towards the origin.
    */
    public PolygonCollider(float N, Vector2 offset) {
        this.offset = offset;
        ArrayList<Vector2> verticies = new ArrayList<Vector2>();
        double theta = 2 * Math.PI / N;
        for (int i = 0; i < N; ++i) {
            float x = (float) Math.cos(theta * i);
            float y = (float) Math.sin(theta * i);
            verticies.add(new Vector2(x, y));
        }
        this.verticies = verticies.toArray(new Vector2[verticies.size()]);
    }

    /**
    * Constructor. Generates a new regular N-PolygonCollider
    *
    * @param N The number verticies of thi PolygonCollider.
    * @param offset The offset towards the origin.
    * @param size THe size of this object.
    */
    public PolygonCollider(float N, Vector2 offset, float size) {
        this.offset = offset;
        this.size = size;
        ArrayList<Vector2> verticies = new ArrayList<Vector2>();
        double theta = 2 * Math.PI / N;
        for (int i = 0; i < N; ++i) {
            float x = (float) Math.cos(theta * i);
            float y = (float) Math.sin(theta * i);
            verticies.add(new Vector2(x, y));
        }
        this.verticies = verticies.toArray(new Vector2[verticies.size()]);
    }

    /**
     * @returns {@link PolygonCollider.offset}.
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
    * @returns {@link PolygonCollider.size}.
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
    * @returns {@link PolygonCollider.mass}.
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
     * Gets the number of verticies.
     */
    public int getVerticiesCount() {
        return verticies.length;
    }

    /**
     * Returns an array with all edges this PolygonCollider has.
     * @return An array with all edges of this PolygonCollider.
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
     * @returns the mass center in ralation to the origin.
     */
    /**
    * Generates a regular PolygonCollider
    *
    * @param numberOfVertices The number of verticies.
    * @return A regular PolygonCollider with "numberOfVertices" verticies.
    */
    public static PolygonCollider regularPolygonCollider(int numberOfVertices) {
        ArrayList<Vector2> verticies = new ArrayList<Vector2>();
        double theta = 2 * Math.PI / numberOfVertices;
        for (int i = 0; i < numberOfVertices; ++i) {
            float x = (float) Math.cos(theta * i);
            float y = (float) Math.sin(theta * i);
            verticies.add(new Vector2(x, y));
        }
        return new PolygonCollider(verticies.toArray(new Vector2[verticies.size()]));
    }

    /**
    * Checks if two PolygonCollider intersect.
    * @param a One PolygonCollider.
    * @param b Another PolygonCollider.
    * @return If two collliders intersect.
    */
    public static boolean intersect(PolygonCollider one, PolygonCollider two) {
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
}
