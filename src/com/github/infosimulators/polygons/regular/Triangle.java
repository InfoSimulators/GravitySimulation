package com.github.infosimulators.polygons.regular;

import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.polygons.Polygon;

public class Triangle extends Polygon {
    /**
    * Constructor.
    */
    public Triangle() {
        super();
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    */
    public Triangle(Vector2 offset) {
        super(3, offset);
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    * @param size The size of the Collider.
    */
    public Triangle(Vector2 offset, float size) {
        super(3, offset, size);
    }
}
