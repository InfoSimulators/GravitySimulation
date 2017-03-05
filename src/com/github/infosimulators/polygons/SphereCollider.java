package com.github.infosimulators.polygons;

import com.github.infosimulators.physic.Vector2;

public class SphereCollider extends Collider {
    public final static int NUMBER_OF_POLYGONS_IN_SPHERE = 50;

    /**
    * Constructor.
    */
    public SphereCollider() {
        super();
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    */
    public SphereCollider(Vector2 offset) {
        super(offset);
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    * @param size The size of the Collider.
    */
    public SphereCollider(Vector2 offset, float size) {
        super(offset, size);
    }

    /**
     * Converts this sphere into a polygon with
     *
     * @return This sphere as {@link PolygonCollider}.
     */
    @Override
    public PolygonCollider toPolygonCollider() {
        return new PolygonCollider(NUMBER_OF_POLYGONS_IN_SPHERE, offset, size);
    }
}
