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
        PhysicsObject b = new PhysicsObject(new Vector2D(200,100), new Vector2D(),50f);
        PhysicsObject a = new PhysicsObject(new Vector2D(100,100), new Vector2D(),50f);
        System.out.println(Vector2D.subtract(a.position, b.position));
        System.out.println(Vector2D.add(a.position, b.position));
        System.out.println(IntertialSystem.gravitation(a,b));
        System.out.println("END");
        //System.out.println(IntertialSystem.gravitation());
    }
}
