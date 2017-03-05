package com.github.infosimulators.polygons.collider;

import com.github.infosimulators.physic.Vector2;

public class SphereCollider extends PolygonCollider {
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
        super(NUMBER_OF_POLYGONS_IN_SPHERE,offset);
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    * @param size The size of the Collider.
    */
    public SphereCollider(Vector2 offset, float size) {
        super(NUMBER_OF_POLYGONS_IN_SPHERE,offset, size);
    }
}
