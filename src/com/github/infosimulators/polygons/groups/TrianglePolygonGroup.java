package com.github.infosimulators.polygons.groups;

import java.util.ArrayList;

import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.polygons.Polygon;
import com.github.infosimulators.polygons.PolygonGroup;

/**
 * WIP
 * Class for building more complex polygons made out of Triangles.
 */
public class TrianglePolygonGroup extends PolygonGroup {
    /**
    * Constructor.
    */
    public TrianglePolygonGroup(int N) {
        super();
        // ArrayList<Vector2> verticies = new ArrayList<Vector2>();
        // Vector2[] corners = getVerticiesOnCircle(N);
        // for(Vector2 corner : corners){
        //   //  polygons.add(new Polygon(new Vector2[]{corner}, offset))
        // }
    }

    /**
     * @return the outer verticies
     */
    @Override
    public Vector2[] getVerticies() {
        ArrayList<Vector2> temp = new ArrayList<Vector2>();
        for (Polygon poly : polygons) {
            for (Vector2 v : poly.getVerticies()) {
                if (!temp.contains(v) || Vector2.add(v, poly.getOffset()) == Vector2.zero())
                    temp.add(v);
            }
        }
        return temp.toArray(new Vector2[temp.size()]);
    }
}
