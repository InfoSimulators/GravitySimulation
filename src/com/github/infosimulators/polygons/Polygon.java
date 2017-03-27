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
		this.vertices =vertices;
		solveIssues();
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
		solveIssues();
	}

	/**
	 * Constructor. Generates a new regular N-Polygon
	 *
	 * @param N
	 *            The number vertices of this polygon.
	 */
	public Polygon(float N) {
		super();
		this.vertices = getVerticesOnCircle(N);
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
		this.vertices = getVerticesOnCircle(N);
	}

	/**
	 * @return The center of mass of this polygon.
	 */
	public Vector2 center() {
		float x = 0f;
		float y = 0f;
		int pointCount = vertices.length;
		for (int i = 0; i == pointCount - 1; i++) {
			final Vector2 point = vertices[i].toCartesian();
			x += point.x;
			y += point.y;
		}
		return new Vector2(x / pointCount, y / pointCount);
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
	 * Solves differences between the position and the center.
	 */
	protected void solveIssues(){
		this.vertices = PolarVector2.order(this.vertices);
		setOffset(center().scale(-1f));
	}

	/**
	 * @return A list of all points relative to world space (with offset
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
	public PolarVector2[] getLocalVertices() {
		return vertices;
	}

	/**
	 * Sets the vertices new. <b>Use with caution</b>.
	 *
	 * @param vertices
	 *            The new vertices relative to local space as
	 *            {@link PolarVector2}.
	 */
	public void setVertices(PolarVector2[] vertices) {
		this.vertices = vertices;
		solveIssues();
	}

	/**
	 * Adds a vertex. May not work on inheriting classes.
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
		this.vertices = temp;
		solveIssues();
	}

	/**
	 * @return The number of vertices.
	 */
	public int getVerticesCount() {
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
		for (Vector2 vertex1 : one.getVertices()) {
			for (Vector2 vertex2 : two.getVertices()) {
				Ray line = Ray.fromTwoPoints(vertex1, vertex2);
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
	 * Checks if two polygons intersect with the separating-axis-theorem.
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
			Vector2[] polygonVertices = polygon.getVertices();
			Vector2 sphereOffset = sphere.getOffset();
			Vector2 closest = null;
			float min_distance = Float.POSITIVE_INFINITY;
			for (int i = 0; i < polygon.getVerticesCount(); i++)
				if (Vector2.sqrDistance(polygonVertices[i], sphereOffset) < (min_distance * min_distance)) {
					min_distance = Vector2.distance(polygonVertices[i], sphereOffset);
					closest = polygonVertices[i];
				}

			Vector2 axis = Vector2.subtract(sphereOffset, closest);
			ArrayList<Float> projectedPoints = new ArrayList<Float>();
			for (int i = 0; i < polygon.getVerticesCount(); i++)
				projectedPoints.add(Vector2.dot(polygonVertices[i], axis));

			float min1 = getMin(projectedPoints);
			float max1 = getMax(projectedPoints);
			float min2 = sphereOffset.dot(axis);
			float max2 = sphereOffset.dot(axis);
			if (max1 < min2 || max2 < min1)
				return false;

		} else {
			ArrayList<Vector2> normals = new ArrayList<Vector2>();
			Vector2[] p1_Vertices = p1.getVertices();
			Vector2[] p2_Vertices = p2.getVertices();
			int p1_Vertices_count = p1.getVerticesCount();
			int p2_Vertices_count = p2.getVerticesCount();

			for (int i = 0; i < p1_Vertices_count; i++) {
				float x = p1_Vertices[(i + 1) % p1_Vertices_count].x - p1_Vertices[i].x;
				float y = p1_Vertices[(i + 1) % p1_Vertices_count].y - p1_Vertices[i].y;
				normals.add(new Vector2(x, y).getNormal1());
			}

			for (int i = 0; i < p2_Vertices_count; i++) {
				float x = p2_Vertices[(i + 1) % p2_Vertices_count].x - p2_Vertices[i].x;
				float y = p2_Vertices[(i + 1) % p2_Vertices_count].y - p2_Vertices[i].y;
				normals.add(new Vector2(x, y).getNormal1());
			}

			// project points of p1 and p2 on each normal vector until a gap is
			// found

			for (int n = 0; n < normals.size(); n++) {
				ArrayList<Float> projectedPoints1 = new ArrayList<Float>();
				ArrayList<Float> projectedPoints2 = new ArrayList<Float>();
				Vector2 curr_normal = normals.get(n);
				for (int i = 0; i < p1_Vertices_count; i++)
					projectedPoints1.add(new Vector2(p1_Vertices[i].x, p1_Vertices[i].y).dot(curr_normal));

				for (int i = 0; i < p2_Vertices_count; i++)
					projectedPoints2.add(new Vector2(p2_Vertices[i].x, p2_Vertices[i].y).dot(curr_normal));

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

	public static PolarVector2[] getVerticesOnCircle(float N) {
		ArrayList<PolarVector2> vertices = new ArrayList<PolarVector2>();
		double theta = 2 * Math.PI / N;
		for (int i = 0; i < N; ++i) {
			vertices.add(new PolarVector2((float) theta * i));
		}
		return vertices.toArray(new PolarVector2[vertices.size()]);
	}
	public static Polygon unite(Polygon one,Polygon two,float angle){
		Polygon united = new Polygon();
		float angel = angle;
		for (PolarVector2 v : one.getLocalVertices()) {
			if (Math.abs(angel - v.theta) <= Math.PI)
				united.addVertex(v);
		}
		for (PolarVector2 v : two.getLocalVertices()) {
			if (Math.abs(angel - v.theta) > Math.PI)
				united.addVertex(v);
		}
		return united;
	}

}
