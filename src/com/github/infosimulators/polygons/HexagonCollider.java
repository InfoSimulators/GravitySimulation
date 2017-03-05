package com.github.infosimulators.polygons;

import com.github.infosimulators.physic.Vector2;

public class HexagonCollider extends PolygonCollider {

    /**
    * Constructor.
    */
    public HexagonCollider() {
        super();
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    */
    public HexagonCollider(Vector2 offset) {
        super(6, offset);
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    * @param size The size of the Collider.
    */
    public HexagonCollider(Vector2 offset, float size) {
        super(6, offset, size);
    }
}
