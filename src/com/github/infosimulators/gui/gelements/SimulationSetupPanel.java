package com.github.infosimulators.gui.gelements;

import java.util.ArrayList;
import java.util.List;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;

import processing.core.PApplet;

public class SimulationSetupPanel extends GElement{

	private List<SetupSpaceObject> objects;
	
	private Panel panel;
	
	private boolean pressedLast;
	
	private float STANDARD_MASS = 2e3f, STANDARD_VELOCITY = 0f, STANDARD_ANGLEVEL = 0f, STANDARD_RADIUS = 20f;
	
	public SimulationSetupPanel(String ID, float x, float y, float xSize, float ySize) {
		super(ID, x, y, xSize, ySize);
		objects = new ArrayList<SetupSpaceObject>();
		panel = new Panel("InfoPanel", 200, 0, 0, 150, 200);
		panelSetup();
	}

	@Override
	public void update(PApplet p) {
		if(pressedLast && !p.mousePressed){
			boolean objectPressed = false;
			for(SetupSpaceObject object : objects){
				if(object.getActive() && panel.hovered(p)){
					objectPressed = true;
				}
				if(object.getActive() && PApplet.dist(p.mouseX, p.mouseY, object.getX(), object.getY()) <= object.getRadius() + 10){
					object.notActive();
					objectPressed = true;
				}
				if(!objectPressed && !object.getActive() && PApplet.dist(p.mouseX, p.mouseY, object.getX(), object.getY()) <= object.getRadius() + 10){
					unactivateObjects();
					object.setActive();
					objectPressed = true;
					if(object.getX() + object.getRadius() + 160 > p.width){
						panel.setX(object.getX() - object.getRadius() - 160);
					}else{
						panel.setX(object.getX() + object.getRadius() + 10);
					}
					if(object.getY() + panel.getySize() > p.height){
						panel.setY(object.getY() - object.getRadius() - 210);
					}else{
						panel.setY(object.getY());
					}
				}
			}
			if(!objectPressed){
				unactivateObjects();
				SetupSpaceObject newObject = new SetupSpaceObject(p.mouseX, p.mouseY, STANDARD_MASS, STANDARD_VELOCITY, STANDARD_ANGLEVEL, STANDARD_RADIUS);
				objects.add(newObject);
			}
			pressedLast = false;
		}
		if(hovered(p) && p.mousePressed){
			pressedLast = true;
		}
		
		for(Event event : EventRegistry.getEventsOfType(EventType.GUI_BUTTON_PRESSED)){
			if(event.getArgs()[0] == "DeleteObjectButton"){
				for(SetupSpaceObject object : objects){
					if (object.getActive()){
						System.out.println(objects.size());
						objects.remove(object);
						System.out.println(objects.size());
					}
				}
				event.setHandled();
			}
		}
		
		for(SetupSpaceObject object: objects){
			p.noStroke();
			p.fill(color2);
			if (object.getActive()){
				panel.update(p);
				p.stroke(color3);
			}
			p.ellipse(object.getX(), object.getY(), object.getRadius(), object.getRadius());
		}
		p.noFill();
		p.stroke(color3);
		p.rect(x, y, xSize, ySize);
	}

	private void panelSetup(){
		panel.addElement(new RectButton("DeleteObjectButton", "Delete", 10, 150, 130, 40));
	}
	
	private void unactivateObjects(){
		for(SetupSpaceObject object : objects){
			object.notActive();
		}
	}
}
