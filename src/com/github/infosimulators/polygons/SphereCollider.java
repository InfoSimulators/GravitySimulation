package com.github.infosimulators.polygons;

public class SphereCollider extends Collider {
    public final static int NUMBER_OF_POLYGONS_TO_SPHERE = 50;

    /**
    * Constructor.
    *
    */
    public SphereCollider() {
        super();
    }

    /**
     * Converts this sphere into a polygon with
     *
     * @return This sphere as {@link PolygonCollider}.
     */
    @Override
    public PolygonCollider toPolygonCollider() {
        return new PolygonCollider(NUMBER_OF_POLYGONS_TO_SPHERE, offset, size);
    }
}
