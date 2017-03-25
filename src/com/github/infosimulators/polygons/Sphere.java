package com.github.infosimulators.polygons;

import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.polygons.Polygon;

public class Sphere extends Polygon {

	public final static int NUMBER_OF_POLYGONS_IN_SPHERE = 50;
	public float radius;

	/**
	 * Constructor.
	 */
	public Sphere() {
		super(NUMBER_OF_POLYGONS_IN_SPHERE);
	}

	/**
	 * Constructor.
	 *
	 * @param offset
	 *            The offset towards the origin.
	 */
	public Sphere(Vector2 offset) {
		super(NUMBER_OF_POLYGONS_IN_SPHERE, offset);

	}

	/**
	 * Constructor.
	 *
	 * @param offset
	 *            The offset towards the origin.
	 * @param size
	 *            The size of the Collider.
	 */
	public Sphere(Vector2 offset, float size) {
		super(NUMBER_OF_POLYGONS_IN_SPHERE, offset);
		scale(size);
	}

}
