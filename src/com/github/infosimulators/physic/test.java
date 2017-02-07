package com.github.infosimulators.physic;

import processing.core.PApplet;


public class test extends PApplet
{
    public Space s;
    public test(){
        s = new Space(400f,1f);
        PhysicsObject b = new PhysicsObject(Vector2.zero(), Vector2.zero(), 2E20f,30f);
        s.registerPhysicsObject(b);
        Vector2 x = new Vector2(0, 100);
        PhysicsObject a = new PhysicsObject(x, new Vector2(-6f,0) ,1E7f, 10f);
        s.registerPhysicsObject(a);
        System.out.println("END");
    }
        public void settings(){
        size(600,600);
    }
    public void setup(){
        frameRate(15);

    }

    public void draw(){
        background(255);
        s.tick();
        translate(width/2,height/2);
        for (PhysicsObject var : s.getSpaceRegister()) {
            //System.out.println(var.mass + " : " + var.position +" : " + var.velocity);
            stroke(0);
            strokeWeight(0);
            fill(127);
            ellipse(var.position.x,var.position.y,var.size,var.size);
        }

    }

    public static void main(String[] args)
    {
        PApplet.main("com.github.infosimulators.physic.test");

        //System.out.println(Space.gravitation());
    }
}
