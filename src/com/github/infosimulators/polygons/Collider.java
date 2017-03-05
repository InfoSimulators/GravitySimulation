package com.github.infosimulators.polygons;

import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.polygons.Ray.RelativePoisition;

public abstract class Collider {
    protected Vector2 offset = Vector2.zero();
    protected float size = 1f;

    /**
    * Constructor.
    *
    */
    public Collider() {
        offset = Vector2.zero();
    }

    /**
    * Constructor.
    *
    * @param verticies The verticies of the PolygonCollider.
    */
    public Collider(Vector2 offset) {
        this.offset = offset;
    }

    /**
    * Constructor.
    *
    * @param verticies The verticies of the PolygonCollider.
    * @param offset The offset towards the origin.
    */
    public Collider(Vector2 offset, float size) {
        this.size = size;
        this.offset = offset;
    }
    /**
     * @return This Collider as a {@link PolygonCollider}.
     */
    public abstract PolygonCollider toPolygonCollider();

    /**
     * Returns the offset.
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
    * @return The size of the Collider.
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
     * Checks if two Collider intersect.
     *
     * @param a One PolygonCollider.
     * @param b Another PolygonCollider.
     */
    public static boolean intersect(Collider a, Collider b) {
        PolygonCollider one = a.toPolygonCollider();
        PolygonCollider two = b.toPolygonCollider();
        if (a == b)
            return true;
        for (Vector2 vertice1 : one.verticies) {
            for (Vector2 vertice2 : two.verticies) {
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
