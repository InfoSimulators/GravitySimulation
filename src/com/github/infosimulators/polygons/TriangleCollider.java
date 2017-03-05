package com.github.infosimulators.polygons;

import com.github.infosimulators.physic.Vector2;

public class TriangleCollider extends PolygonCollider {
    /**
    * Constructor.
    */
    public TriangleCollider() {
        super();
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    */
    public TriangleCollider(Vector2 offset) {
        super(3, offset);
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    * @param size The size of the Collider.
    */
    public TriangleCollider(Vector2 offset, float size) {
        super(3, offset, size);
    }
}
