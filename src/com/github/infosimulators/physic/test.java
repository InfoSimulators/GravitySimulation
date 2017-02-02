package com.github.infosimulators.physic;

import java.util.Vector;


public class test
{
    /**
     * Constructor for objects of class test
     */
    public test()
    {

    }

    public static void main()
    {
        PhysicsObject b = new PhysicsObject(new Vector2(200,100), new Vector2(),50f);
        PhysicsObject a = new PhysicsObject(new Vector2(100,100), new Vector2(),50f);
        System.out.println(Vector2.subtract(a.position, b.position));
        System.out.println(Vector2.add(a.position, b.position));
        System.out.println(Space.gravitation(a,b));
        System.out.println("END");
        //System.out.println(Space.gravitation());
    }
}
