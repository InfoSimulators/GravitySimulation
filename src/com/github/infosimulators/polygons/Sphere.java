package com.github.infosimulators.polygons;

import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.polygons.Polygon;

public class Sphere extends Polygon {

	public final static int NUMBER_OF_POLYGONS_IN_SPHERE = 50;
	public float radius = 1;

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
	* @param scale
	*            The size of the object.
	*/
	public Sphere(float scale) {
		super(NUMBER_OF_POLYGONS_IN_SPHERE);
		scale(scale);

	}

	/**
	 * Constructor.
	 *
	 * @param offset
	 *            The offset towards the origin.
	 * @param size
	 *            The size of the collider.
	 */
	public Sphere(Vector2 offset, float size) {
		super(NUMBER_OF_POLYGONS_IN_SPHERE, offset);
		scale(size);
	}

	/**
	 * Scales the circle.
	 * @param amount
	 * 				The amount to scale with.
	 */
	@Override
	public void scale(float amount){
		radius *= amount;
		super.scale(amount);
	}

}
