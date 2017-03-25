package com.github.infosimulators.polygons;

import java.lang.Math;
import java.util.ArrayList;

import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.physic.PolarVector2;
import com.github.infosimulators.polygons.Ray.RelativePosition;

/**
 * A class to store simple convex polygons.
 */
public class Polygon {

	/** points on the outside */
	protected PolarVector2[] vertices;

	/**
	 * The mass of this polygon. Included to specify a density in polygongroups
	 */
	private float mass = 1f;
	private Vector2 offset = Vector2.zero();

	/**
	 * Constructor.
	 *
	 */
	public Polygon() {
		vertices = new PolarVector2[] {};
	}

	/**
	 * Constructor.
	 *
	 * @param vertices
	 *            The vertices of the Polygon.
	 */
	public Polygon(PolarVector2[] vertices) {
		this.vertices = PolarVector2.order(vertices);
	}

	/**
	 * Constructor.
	 *
	 * @param vertices
	 *            The vertices of the Polygon.
	 * @param offset
	 *            The offset towards the origin.
	 */
	public Polygon(PolarVector2[] vertices, Vector2 offset) {
		this.offset = offset;
		this.vertices = PolarVector2.order(vertices);
	}

	/**
	 * Constructor. Generates a new regular N-Polygon
	 *
	 * @param N
	 *            The number vertices of this polygon.
	 */
	public Polygon(float N) {
		super();
		this.vertices = getVerticiesOnCircle(N);
	}

	/**
	 * Constructor. Generates a new regular N-Polygon
	 *
	 * @param N
	 *            The number vertices of this polygon.
	 * @param offset
	 *            The offset towards the origin.
	 */
	public Polygon(float N, Vector2 offset) {
		this.offset = offset;
		this.vertices = getVerticiesOnCircle(N);
	}

	/**
	 * @return The center of mass of this polygon.
	 */
	public Vector2 center() {
		return offset;
	}

	/**
	 * @returns {@link Polygon.offset}.
	 */
	public Vector2 getOffset() {
		return offset;
	}

	/**
	 * Sets the offset new.
	 *
	 * @param offset
	 *            The new offset.
	 */
	public void setOffset(Vector2 offset) {
		this.offset = offset;
	}

	/**
	 * Sets the offset new.
	 *
	 * @param offset
	 *            The new offset.
	 */
	public void setOffset(float x, float y) {
		this.offset = new Vector2(x, y);
	}

	/**
	 * Resizes the polygon.
	 *
	 * @param amount
	 *            The amount to scale with.
	 */
	public void scale(float size) {
		for (int i = 0; i < vertices.length; i++)
			vertices[i].scale(size);
	}

	/**
	 * @returns {@link Polygon.mass}.
	 */
	public float getMass() {
		return mass;
	}

	/**
	 * Sets the mass new.
	 *
	 * @param mass
	 *            The new size.
	 */
	public void setMass(float mass) {
		this.mass = mass;
	}

	/**
	 * @return A list of all points relative to world space (with offest
	 *         applied).
	 */
	public Vector2[] getVertices() {
		Vector2[] temp = new Vector2[vertices.length];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = vertices[i].toCartesian().add(offset);
		}
		return temp;
	}

	/**
	 * @return A list of all points relative to local space.
	 */
	public PolarVector2[] getLocalVerticies() {
		return vertices;
	}

	/**
	 * Sets the vertecies new. <b>Use with caution</b>.
	 *
	 * @param vertices
	 *            The new vertices relative to local space as
	 *            {@link PolarVector2}.
	 */
	public void setVerticies(PolarVector2[] vertices) {
		this.vertices = PolarVector2.order(vertices);
	}

	/**
	 * Adds a vertex. May not work on inherting classes.
	 *
	 * @param vertex
	 *            The new vertex relative to local space.
	 */
	public void addVertex(PolarVector2... vertex) {
		PolarVector2[] temp = new PolarVector2[vertices.length + vertex.length];
		for (int i = 0; i < vertices.length; i++)
			temp[i] = vertices[i];
		for (int i = 0; i < vertex.length; i++)
			temp[vertices.length + i] = vertex[i];
		this.vertices = PolarVector2.order(temp);
	}

	/**
	 * @return The number of vertices.
	 */
	public int getVerticiesCount() {
		return getVertices().length;
	}

	/**
	 * Checks if two Polygon intersect.
	 *
	 * @param a
	 *            One Polygon.
	 * @param b
	 *            Another Polygon.
	 * @return If two collliders intersect.
	 */
	public static boolean intersect(Polygon one, Polygon two) {
		if (one == two)
			return true;
		for (Vector2 vertice1 : one.getVertices()) {
			for (Vector2 vertice2 : two.getVertices()) {
				Ray line = Ray.fromTwoPoints(vertice1, vertice2);
				RelativePosition s1 = line.getRelativePosition(one.offset);
				RelativePosition s2 = line.getRelativePosition(two.offset);
				if (s1 == RelativePosition.ON && s2 == RelativePosition.ON)
					continue;
				if (s1 != s2) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if two polygons intersect with the seperating axis theorem.
	 *
	 * @param a
	 *            One Polygon.
	 * @param b
	 *            Another Polygon.
	 * @return If two polygons intersect.
	 */
	public static boolean intersectSAT(Polygon p1, Polygon p2) {
		if (p1 instanceof Sphere && p2 instanceof Sphere) {
			Sphere s1 = (Sphere) p1;
			Sphere s2 = (Sphere) p2;
			if ((s1.radius + s2.radius) * (s1.radius + s2.radius) >= Vector2.sqrDistance(p1.getOffset(),
					p2.getOffset()))
				return false;
		} else if (p1 instanceof Sphere || p2 instanceof Sphere) {

			Polygon sphere = p1 instanceof Sphere ? p1 : p2;
			Polygon polygon = p1 instanceof Sphere ? p2 : p1;
			Vector2[] polygonVerticies = polygon.getVertices();
			Vector2 sphereOffset = sphere.getOffset();
			Vector2 closest = null;
			float min_distance = Float.POSITIVE_INFINITY;
			for (int i = 0; i < polygon.getVerticiesCount(); i++)
				if (Vector2.sqrDistance(polygonVerticies[i], sphereOffset) < (min_distance * min_distance)) {
					min_distance = Vector2.distance(polygonVerticies[i], sphereOffset);
					closest = polygonVerticies[i];
				}

			Vector2 axis = Vector2.subtract(sphereOffset, closest);
			ArrayList<Float> projectedPoints = new ArrayList<Float>();
			for (int i = 0; i < polygon.getVerticiesCount(); i++)
				projectedPoints.add(Vector2.dot(polygonVerticies[i], axis));

			float min1 = getMin(projectedPoints);
			float max1 = getMax(projectedPoints);
			float min2 = sphereOffset.dot(axis);
			float max2 = sphereOffset.dot(axis);
			if (max1 < min2 || max2 < min1)
				return false;

		} else {
			ArrayList<Vector2> normals = new ArrayList<Vector2>();
			Vector2[] p1_verticies = p1.getVertices();
			Vector2[] p2_verticies = p2.getVertices();
			int p1_verticies_count = p1.getVerticiesCount();
			int p2_verticies_count = p2.getVerticiesCount();
			// recover normal vectors for p1 and p2
			for (int i = 0; i < p1_verticies_count; i++) {
				/*
				 * Old version. Not sure if new version works if (i <
				 * p1_verticies_count - 1) { float x = p1_verticies[i + 1].x -
				 * p1_verticies[i].x; float y = p1_verticies[i + 1].y -
				 * p1_verticies[i].y; normals.add(new Vector2(x,
				 * y).getNormal1()); } else { float x = p1_verticies[0].x -
				 * p1_verticies[i].x; float y = p1_verticies[0].y -
				 * p1_verticies[i].y; normals.add(new Vector2(x,
				 * y).getNormal1()); }
				 */
				float x = p1_verticies[(i + 1) % p1_verticies_count].x - p1_verticies[i].x;
				float y = p1_verticies[(i + 1) % p1_verticies_count].y - p1_verticies[i].y;
				normals.add(new Vector2(x, y).getNormal1());
			}

			for (int i = 0; i < p2_verticies_count; i++) {
				/*
				 * Old version. Not sure if new version works if (i <
				 * p2_verticies_count - 1) { float x = p2_verticies[i + 1].x -
				 * p2_verticies[i].x; float y = p2_verticies[i + 1].y -
				 * p2_verticies[i].y; normals.add(new Vector2(x,
				 * y).getNormal1()); } else { float x = p2_verticies[0].x -
				 * p2_verticies[i].x; float y = p2_verticies[0].y -
				 * p2_verticies[i].y; normals.add(new Vector2(x,
				 * y).getNormal1()); }
				 */
				float x = p2_verticies[(i + 1) % p2_verticies_count].x - p2_verticies[i].x;
				float y = p2_verticies[(i + 1) % p2_verticies_count].y - p2_verticies[i].y;
				normals.add(new Vector2(x, y).getNormal1());
			}

			// project points of p1 and p2 on each normal vector until a gap is
			// found

			for (int n = 0; n < normals.size(); n++) {
				ArrayList<Float> projectedPoints1 = new ArrayList<Float>();
				ArrayList<Float> projectedPoints2 = new ArrayList<Float>();
				Vector2 curr_normal = normals.get(n);
				for (int i = 0; i < p1_verticies_count; i++)
					projectedPoints1.add(new Vector2(p1_verticies[i].x, p1_verticies[i].y).dot(curr_normal));

				for (int i = 0; i < p2_verticies_count; i++)
					projectedPoints2.add(new Vector2(p2_verticies[i].x, p2_verticies[i].y).dot(curr_normal));

				float min1 = getMin(projectedPoints1);
				float max1 = getMax(projectedPoints1);
				float min2 = getMin(projectedPoints2);
				float max2 = getMax(projectedPoints2);
				if (max1 < min2 || max2 < min1)
					return false;
			}
		}
		return true;
	}

	private static float getMin(ArrayList<Float> list) {
		float min = list.get(0);
		for (float f : list)
			min = f < min ? f : min;
		return min;
	}

	private static float getMax(ArrayList<Float> list) {
		float max = list.get(0);
		for (float f : list)
			max = f > max ? f : max;
		return max;
	}

	public static PolarVector2[] getVerticiesOnCircle(float N) {
		ArrayList<PolarVector2> vertices = new ArrayList<PolarVector2>();
		double theta = 2 * Math.PI / N;
		for (int i = 0; i < N; ++i) {
			vertices.add(new PolarVector2((float) theta * i));
		}
		return vertices.toArray(new PolarVector2[vertices.size()]);
	}

}
