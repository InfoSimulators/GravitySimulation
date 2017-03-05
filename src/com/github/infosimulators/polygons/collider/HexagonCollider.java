package com.github.infosimulators.polygons.collider;

import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.polygons.PolygonCollider;

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
