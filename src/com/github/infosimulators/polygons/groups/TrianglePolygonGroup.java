package com.github.infosimulators.polygons.groups;

import java.util.ArrayList;

import com.github.infosimulators.physic.PolarVector2;
import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.polygons.Polygon;
import com.github.infosimulators.polygons.PolygonGroup;
/**
 * TODO
 * Class for building more complex polygons made out of Triangles.
 */
public class TrianglePolygonGroup extends PolygonGroup {
    /**
    * Constructor.
    */
    public TrianglePolygonGroup(int N) {
        super();
        PolarVector2[] x = Polygon.getVerticesOnCircle(N);
        polygons = new ArrayList<Polygon>();
        for (int i = 0; i < x.length; i++)
            polygons.add(new Polygon(new PolarVector2[] { x[i], PolarVector2.zero(), x[(i + 1) % x.length] }));
    }

    /**
     * @return the outer vertices
     */
    @Override
    public Vector2[] getVertices() {
        ArrayList<Vector2> temp = new ArrayList<Vector2>();
        for (Polygon poly : polygons) {
            for (Vector2 v : poly.getVertices()) {
                if (!temp.contains(v) || Vector2.add(v, poly.getOffset()) == Vector2.zero())
                    temp.add(v);
            }
        }
        return temp.toArray(new Vector2[temp.size()]);
    }
}
