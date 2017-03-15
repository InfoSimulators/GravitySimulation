package com.github.infosimulators.polygons.regular;

import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.polygons.Polygon;

public class Hexagon extends Polygon {

    /**
    * Constructor.
    */
    public Hexagon() {
        super(6);
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    */
    public Hexagon(Vector2 offset) {
        super(6, offset);
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    * @param size The size of the Collider.
    */
    public Hexagon(Vector2 offset, float size) {
        super(6, offset, size);
    }
}
