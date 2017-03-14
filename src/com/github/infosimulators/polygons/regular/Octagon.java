package com.github.infosimulators.polygons.regular;

import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.polygons.Polygon;

public class Octagon extends Polygon {

    /**
    * Constructor.
    */
    public Octagon() {
        super();
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    */
    public Octagon(Vector2 offset) {
        super(8, offset);
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    * @param size The size of the Collider.
    */
    public Octagon(Vector2 offset, float size) {
        super(8, offset, size);
    }
}
