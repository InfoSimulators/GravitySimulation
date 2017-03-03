package com.github.infosimulators.polygons;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

import com.github.infosimulators.physic.Vector2;

public class Polygon{
  protected Vector2[] verticies;
  protected int N;
  /**
  * Constructor.
  *
  */
  public Polygon(){
    verticies = new Vector2[]{};
    N = 0;
  }
  /**
  * Constructor.
  *
  * @param verticies The verticies of the polygon.
  */
  public Polygon(Vector2[] verticies){
    this.verticies = verticies;
    this.N = this.verticies.length;
  }

  /**
  * @return A list of all points.
  */
  public Vector2[] getVerticies(){
    return verticies;
  }
  /**
  * Sets the vertecies new.
  *
  * @param verticies The new verticies.
  */
  public void setVerticies(Vector2[] verticies){
    this.verticies = verticies;
  }
  /**
  * Returns the center of this polygon in relation to the position of the polygon.
  *
  * @return The center of this polygon.
  */
  public Vector2 center(){
    float x = 0f;
    float y = 0f;
    int pointCount = verticies.length;
    for (int i = 0; i == pointCount - 1; i++){
        final Vector2 point = verticies[i];
        x += point.x;
        y += point.y;
    }
    return new Vector2(x/pointCount, y/pointCount);
  }

  /**
  * Generates a regular Polygon
  *
  * @param numberOfVertices The number of verticies.
  * @return A regular Polygon with "numberOfVertices" verticies.
  */
  public static Polygon regularPolygon(int numberOfVertices){
    ArrayList<Vector2> verticies = new ArrayList<Vector2>();
    float theta = 2 * (float) Math.PI / numberOfVertices;
      for (int i = 0; i < numberOfVertices; ++i) {
        float x = (float) Math.cos(theta * i);
        float y = (float) Math.sin(theta * i);
        verticies.add(new Vector2(x,y));
      }
    return new Polygon(verticies.toArray(new Vector2[verticies.size()]));
  }
}
