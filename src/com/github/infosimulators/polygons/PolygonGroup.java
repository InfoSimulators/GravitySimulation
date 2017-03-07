package com.github.infosimulators.polygons;

import java.util.ArrayList;

import com.github.infosimulators.physic.Vector2;

/**
 * Class for building more complex polygons.
 * TODO
 */
public class PolygonGroup extends Polygon {
    public enum Faces {
        NORTH, SOUTH, EAST, WEST
    }

    protected ArrayList<Polygon> polygons;

    /**
    * Constructor.
    */
    public PolygonGroup() {
        super();
        polygons = new ArrayList<Polygon>();
    }

    // public void addPolygon(Polygon newPolygon, Faces face) {
    //     Vector2 goal = Vector2.zero();
    //     switch (face) {
    //     case NORTH:
    //         goal = Vector2.up();
    //         break;
    //     case EAST:
    //         goal = Vector2.right();
    //         break;
    //     case SOUTH:
    //         goal = Vector2.down();
    //         break;
    //     case WEST:
    //         goal = Vector2.left();
    //         break;
    //     }
    //     Polygon maxVertex = polygons.get(0);
    //     float minDifferenceFromGoalsqr = Vector2.subtract(Vector2.add(maxVertex.getOffset(), offset).normalize(), goal)
    //             .sqrMagnitude();
    //     for (Polygon poly : polygons) {
    //         float curDifferenceFromGoalsqr = Vector2.subtract(Vector2.add(poly.getOffset(), offset).normalize(), goal)
    //                 .sqrMagnitude();
    //         if (curDifferenceFromGoalsqr < minDifferenceFromGoalsqr) {
    //             minDifferenceFromGoalsqr = curDifferenceFromGoalsqr;
    //             maxVertex = poly;
    //         }
    //     }
    //     polygons.add(polygons.indexOf(maxVertex), newPolygon);
    // }

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
     * Sets the mass of the overall polygon new.
     * The new mass of every subPolygon is calculated as.
     * sPC.massNew = sPC.massNew * (this.massOld/this.massNew)
     *
     * @param newMass The new mass of the PolygonGroupCollider.
     */
    @Override
    public void setMass(float newMass) {
        float oldMass = getMass();
        for (Polygon poly : polygons) {
            poly.setMass(poly.getMass() * (oldMass / newMass));
        }
    }

    /**
     * @return the outer verticies
     */
    @Override
    public Vector2[] getVerticies() {
        ArrayList<Vector2> temp = new ArrayList<Vector2>();
        for (Polygon poly : polygons) {
            for (Vector2 v : poly.getVerticies()) {
                if (!temp.contains(v))
                    temp.add(v);
            }
        }
        return temp.toArray(new Vector2[temp.size()]);
    }
}
