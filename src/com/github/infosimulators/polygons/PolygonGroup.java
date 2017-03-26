package com.github.infosimulators.polygons;

import java.util.ArrayList;

import com.github.infosimulators.physic.Vector2;

/**
 * Class for building more complex polygons.
 */
public class PolygonGroup extends Polygon {

	protected ArrayList<Polygon> polygons;

	/**
	 * Constructor.
	 */
	public PolygonGroup() {
		super();
		polygons = new ArrayList<Polygon>();
	}

	/**
	 * @return The summed mass of all subPolygon.
	 */
	@Override
	public float getMass() {
		float temp = 0f;
		for (Polygon poly : polygons) {
			temp += poly.getMass();
		}
		return temp;
	}

	/**
	 * @return The center of mass of this polygon.
	 */
	@Override
	public Vector2 center() {
		float offsetToCenterX = 0f;
		float offsetToCenterY = 0f;
		for (Polygon poly : polygons) {
			offsetToCenterX += poly.getOffset().x * poly.getMass();
			offsetToCenterY += poly.getOffset().y * poly.getMass();
		}
		offsetToCenterX /= getMass();
		offsetToCenterY /= getMass();

		return new Vector2(offsetToCenterX + getOffset().x, offsetToCenterY + getOffset().y);
	}

	/**
	 * Sets the mass of the overall polygon new. The new mass of every
	 * subPolygon is calculated as. sPC.massNew = sPC.massNew *
	 * (this.massOld/this.massNew)
	 *
	 * @param newMass
	 *            The new mass of the PolygonGroupCollider.
	 */
	@Override
	public void setMass(float newMass) {
		float oldMass = getMass();
		for (Polygon poly : polygons) {
			poly.setMass(poly.getMass() * (oldMass / newMass));
		}
	}

	/**
	 * @return the outer vertices TODO
	 */
	@Override
	public Vector2[] getVertices() {
		ArrayList<Vector2> temp = new ArrayList<Vector2>();
		for (Polygon poly : polygons) {
			for (Vector2 v : poly.getVertices()) {
				if (!temp.contains(v))
					temp.add(v);
			}
		}
		return temp.toArray(new Vector2[temp.size()]);
	}

}
