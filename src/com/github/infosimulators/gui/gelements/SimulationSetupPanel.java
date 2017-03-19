package com.github.infosimulators.gui.gelements;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class SimulationSetupPanel extends GElement{

	private List<SetupSpaceObject> objects;
	
	private boolean pressedLast;
	
	private float STANDARD_MASS = 2e3f, STANDARD_VELOCITY = 0f, STANDARD_ANGLEVEL = 0f, STANDARD_RADIUS = 20f;
	
	public SimulationSetupPanel(String ID, float x, float y, float xSize, float ySize) {
		super(ID, x, y, xSize, ySize);
		objects = new ArrayList<SetupSpaceObject>();
	}

	@Override
	public void update(PApplet p) {
		if(pressedLast && !p.mousePressed){
			boolean objectPressed = false;
			for(SetupSpaceObject object : objects){
				if(PApplet.dist(p.mouseX, p.mouseY, object.getX(), object.getY()) <= object.getRadius()){
					object.swapActive();
					objectPressed = true;
				}
			}
			if(!objectPressed){
				objects.add(new SetupSpaceObject(p.mouseX, p.mouseY, STANDARD_MASS, STANDARD_VELOCITY, STANDARD_ANGLEVEL, STANDARD_RADIUS));
			}
			pressedLast = false;
		}
		if(hovered(p) && p.mousePressed){
			pressedLast = true;
		}
		for(SetupSpaceObject object: objects){
			p.ellipse(object.getX(), object.getY(), object.getRadius(), object.getRadius());
		}
	}

}
