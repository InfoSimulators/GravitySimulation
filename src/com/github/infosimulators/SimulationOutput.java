package com.github.infosimulators;

import com.github.infosimulators.physic.*;
import com.github.infosimulators.physic.Space;
import com.github.infosimulators.physic.Vector2;
import com.github.infosimulators.physic.PhysicsObject;
import com.github.infosimulators.Simulation;
import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;

import processing.core.PApplet;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class SimulationOutput extends PApplet {
    public Simulation s;
    private float x = 0;
    private float y = 0;
    private boolean shifted = false;
    //Variables for displaying
    public float movementSpeed = 5;
    public boolean drawPastPositions = false;
    public boolean pause = false;
    
    //Variables for simulation
    public float lengthFactor = 10f;
    public float massFactor = 1e12f;
    public float zoomFactor = 1.5f;
    public float deltaTimeFactor = 1f;
    public float deltaRunFactor = 5;

    public static void main(String[] args) {
        PApplet.main("com.github.infosimulators.SimulationOutput");
    }

    public void settings() {
        size(700, 700);
    }

    public void setup() {
        background(255);
        frameRate(60);
        s = new Simulation(new PhysicsObject[] {
                new PhysicsObject(new Vector2(0, 0).scale(lengthFactor), Vector2.zero(), 600 * massFactor,
                        10f * lengthFactor),
                new PhysicsObject(new Vector2(100, 0).scale(lengthFactor), new Vector2(0, 0.6f).scale(lengthFactor),
                        2 * massFactor, 2f * lengthFactor),
                new PhysicsObject(new Vector2(-100, 0).scale(lengthFactor), new Vector2(0, -0.6f).scale(lengthFactor),
                        1f * massFactor, 1f * lengthFactor)} );
        s.space.observedRange = Float.POSITIVE_INFINITY;
        s.setDeltaTime(deltaTimeFactor);
    }

    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == LEFT)
                x += movementSpeed;
            if (keyCode == RIGHT)
                x -= movementSpeed;
            if (keyCode == DOWN)
                y -= movementSpeed;
            if (keyCode == UP)
                y += movementSpeed;
            if (keyCode == SHIFT)
                shifted = true;
        }
    }
    public void keyReleased() {
        if (key == CODED) {
            if(keyCode == SHIFT)
                shifted = false;
        }
    }
    public void draw() {
        if (mousePressed){
            if(shifted){
                if (mouseButton == LEFT)
                    deltaRunFactor += 1;
                else if (mouseButton == RIGHT)
                    deltaRunFactor -= 1;
            }else{
                                if (mouseButton == LEFT)
                    zoomFactor += 0.1f;
                else if (mouseButton == RIGHT)
                    zoomFactor -= 0.1f;
            }
            if (mouseButton == CENTER)
                pause = !pause;  
        }   
        if (!drawPastPositions)            
            background(255);
        translate(x + width / 2, y + height / 2);
        scale(zoomFactor);
        stroke(0);
        fill(150);
        for (PhysicsObject p : s.getContent()) {
            ellipse(p.getPosition().x / lengthFactor, p.getPosition().y / lengthFactor, p.getRadius() / lengthFactor,
                    p.getRadius() / lengthFactor);
            }
        if(!pause)
            for (int i = 0; i < deltaRunFactor; i++)
                s.update();
        for (Event e : EventRegistry.getEvents()) {
            if (!e.isHandled()) {
                //println(e.getType() + " - ");
                e.setHandled();
            }
        }
    }

}