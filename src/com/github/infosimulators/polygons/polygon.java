package com.github.infosimulators.polygons;

import java.lang.Math;
import java.util.ArrayList;

import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.polygons.Ray.RelativePoisition;

public class Polygon {
    protected Vector2[] verticies;
    protected int N;
    protected Vector2 offset;

    /**
    * Constructor.
    *
    */
    public Polygon() {
        verticies = new Vector2[] {};
        N = 0;
        offset = Vector2.zero();
    }

    /**
    * Constructor.
    *
    * @param verticies The verticies of the polygon.
    */
    public Polygon(Vector2[] verticies) {
        this.verticies = verticies;
        this.N = this.verticies.length;
        offset = Vector2.zero();
    }

    public Polygon(Vector2[] verticies, Vector2 offset) {
        this.verticies = verticies;
        this.N = this.verticies.length;
        this.offset = offset;
    }

    /**
    * @return A list of all points.
    */
    public Vector2[] getVerticies() {
        Vector2[] temp = verticies;
        for (Vector2 i : temp) {
            i.add(offset);
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
     * Returns an array with all edges this polygon has.
     * @return An array with all edges of this polygon.
     */
    public Vector2[] allEdges() {
        ArrayList<Vector2> edges = new ArrayList<Vector2>();
        for (Vector2 verticy1 : verticies) {
            for (Vector2 verticy2 : verticies) {
                if (verticy1 == verticy2)
                    continue;
                Vector2 found = Vector2.subtract(verticy1, verticy2);
                if (edges.contains(found) || edges.contains(found.scale(-1)))
                    edges.add(found);
            }
        }
        return edges.toArray(new Vector2[edges.size()]);
    }

    /**
    * Returns the center of this polygon in relation to the position of the polygon.
    *
    * @return The center of this polygon.
    */
    public Vector2 center() {
        float x = 0f;
        float y = 0f;
        int pointCount = verticies.length;
        for (int i = 0; i == pointCount - 1; i++) {
            final Vector2 point = verticies[i];
            x += point.x;
            y += point.y;
        }
        return new Vector2(x / pointCount, y / pointCount);
    }

    /**
    * Generates a regular Polygon
    *
    * @param numberOfVertices The number of verticies.
    * @return A regular Polygon with "numberOfVertices" verticies.
    */
    public static Polygon regularPolygon(int numberOfVertices) {
        ArrayList<Vector2> verticies = new ArrayList<Vector2>();
        double theta = 2 * Math.PI / numberOfVertices;
        for (int i = 0; i < numberOfVertices; ++i) {
            float x = (float) Math.cos(theta * i);
            float y = (float) Math.sin(theta * i);
            verticies.add(new Vector2(x, y));
        }
        return new Polygon(verticies.toArray(new Vector2[verticies.size()]));
    }

    public static boolean intersect(Polygon a, Polygon b) {
        for (Vector2 vertice1 : a.verticies) {
            for (Vector2 vertice2 : b.verticies) {
                Ray line = Ray.fromTwoPoints(vertice1, vertice2);
                RelativePoisition s1 = line.getRelativePosition(a.offset);
                RelativePoisition s2 = line.getRelativePosition(b.offset);
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
