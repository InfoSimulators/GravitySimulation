package com.github.infosimulators.gui.gelements;

import java.util.ArrayList;
import java.util.List;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;

import processing.core.PApplet;

public class Panel extends GElement{
	
	private List<GElement> elements;
	
	private boolean pressedLast;
	
	private float opacity;

	public Panel(String ID, float opacity, float x, float y, float xSize, float ySize) {
		super(ID, x, y, xSize, ySize);
		elements = new ArrayList<GElement>();
		pressedLast = false;
		this.opacity = opacity;
	}

	@Override
	public void update(PApplet p) {
		p.noStroke();
		p.fill(color2, opacity);
		p.rect(x, y, xSize, ySize);
		for (GElement element : elements){
			element.update(p);
		}
		
		if(pressedLast && p.mousePressed){
			//modifyCoords(p.mouseX - p.pmouseX, p.mouseY - p.pmouseY);
			pressedLast = false;
		}
		
		if(hovered(p) && EventRegistry.getEventsOfType(EventType.GUI_ELEMENT_HOVERED).size() == 0){
			EventRegistry.fire(new Event(EventType.GUI_ELEMENT_HOVERED, new String[]{ID + " - hovered"}));
			if(p.mousePressed){
				pressedLast = true;
			}
		}else{
			for (Event event : EventRegistry.getEventsOfType(EventType.GUI_ELEMENT_HOVERED)) {
                if(event.getArgs()[0] == ID + " - hovered"){
                	event.setHandled();
                }
            }
		}
	}
	
	/**
     * Used to get a specific GElement
     * @param ID the ID of the GElement to get
     * @return the GElement asked for, else null
     */
    public GElement getElementByID(String ID){
    	for(GElement element : elements){
    		if(element.getID() == ID){
    			return element;
    		}
    	}
    	return null;
    }
	
	public void modifyCoords(float deltaX, float deltaY){
		this.x += deltaX;
		this.y += deltaY;
		for(GElement element: elements){
			element.modifyX(deltaX);
			element.modifyY(deltaY);
		}
	}
	
	public void setX(float x){
		modifyCoords(x - this.x, 0);
	}
	
	public void setY(float y){
		modifyCoords(0, y - this.y);
	}
	
	public void setCoords(float x, float y){
		modifyCoords(x - this.x, y - this.y);
	}

	public void addElement(GElement element){
		element.modifyX(x);
		element.modifyY(y);
		elements.add(element);
	}
}
