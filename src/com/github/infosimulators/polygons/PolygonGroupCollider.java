package com.github.infosimulators.polygons;

import java.util.ArrayList;

import com.github.infosimulators.physic.Vector2;

/**
 * Class for building more complex polygons.
 * TODO
 */
public class PolygonGroupCollider extends PolygonCollider {
    public enum faces{
        NORTH,SOUTH,EAST,WEST
    }
    protected ArrayList<PolygonCollider> polygons;

    /**
    * Constructor.
    */
    public PolygonGroupCollider() {
        super();
    }

    /**
     * @return The summed mass of all subPolygonCollider.
     */
    @Override
    public float getMass() {
        float temp = 0f;
        for (PolygonCollider poly : polygons) {
            temp += poly.getMass();
        }
        return temp;
    }

    /**
     * Sets the mass of the overall polygon new.
     * The new mass of every subPolygonCollider is calculated as.
     * sPC.massNew = sPC.massNew * (this.massOld/this.massNew)
     *
     * @param newMass The new mass of the PolygonGroupCollider.
     */
    @Override
    public void setMass(float newMass) {
        float oldMass = getMass();
        for (PolygonCollider poly : polygons) {
            poly.setMass(poly.getMass() * (oldMass / newMass));
        }
    }

    /**
     * @return the outer verticies
     */
    @Override
    public Vector2[] getVerticies() {
        ArrayList<Vector2> temp = new ArrayList<Vector2>();
        for (PolygonCollider poly : polygons) {
            for (Vector2 v : poly.getVerticies()) {
                if (!temp.contains(v))
                    temp.add(v);
            }
        }
        return temp.toArray(new Vector2[temp.size()]);
    }
}
