package com.github.infosimulators.polygons.regular;

import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.polygons.Polygon;

/**
 * A polygon with 3 verticies.
 */
public class Triangle extends Polygon {
    /**
    * Constructor.
    */
    public Triangle() {
        super(3);
    }

    /**
    * Constructor.
    *
    * @param offset The offset towards the origin.
    */
    public Triangle(Vector2 offset) {
        super(3, offset);
    }
}
