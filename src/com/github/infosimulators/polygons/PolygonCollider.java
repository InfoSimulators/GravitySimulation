package com.github.infosimulators.polygons;

import java.lang.Math;
import java.util.ArrayList;

import com.github.infosimulators.physic.Vector2;

public class PolygonCollider extends Collider
{
    /** points on the outside */
    protected Vector2[] verticies;

    /**
    * Constructor.
    *
    */
    public PolygonCollider() {
        super();
        verticies = new Vector2[] {};
    }

    /**
    * Constructor.
    *
    * @param verticies The verticies of the PolygonCollider.
    */
    public PolygonCollider(Vector2[] verticies) {
        super();
        this.verticies = verticies;
    }

    /**
    * Constructor.
    *
    * @param verticies The verticies of the PolygonCollider.
    * @param offset The offset towards the origin.
    */
    public PolygonCollider(Vector2[] verticies, Vector2 offset) {
        super(offset);
        this.verticies = verticies;
    }

    /**
    * Constructor. Generates a new regular N-PolygonCollider
    *
    * @param N The number verticies of thi PolygonCollider.
    * @param offset The offset towards the origin.
    */
    public PolygonCollider(float N, Vector2 offset) {
        super(offset);
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
        super(offset, size);
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

    @Override
    public PolygonCollider toPolygonCollider(){
        return this;
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
}
