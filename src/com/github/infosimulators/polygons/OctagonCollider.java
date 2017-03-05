package com.github.infosimulators.polygons;

import com.github.infosimulators.physic.Vector2;

public class OctagonCollider extends PolygonCollider {

    /**
    * Constructor.
    */
    public OctagonCollider() {
        super();
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    */
    public OctagonCollider(Vector2 offset) {
        super(8, offset);
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    * @param size The size of the Collider.
    */
    public OctagonCollider(Vector2 offset, float size) {
        super(8, offset, size);
    }
}
