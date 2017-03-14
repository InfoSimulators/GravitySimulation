package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.Eventtype;

import processing.core.PApplet;

public class RectButton extends GElement{

	private boolean pressedLast, hovered;
	
	public RectButton(String ID, float x, float y, float xSize, float ySize){
		super(ID, x, y, xSize, ySize);
		
		pressedLast = false;
		setHovered(false);
	}
	
	public void update(PApplet p){
		
		if(pressedLast && !p.mousePressed){
			EventRegistry.fire(new Event(Eventtype.GUI_BUTTON_PRESSED, new String[]{ID}));
			pressedLast = false;
		}
		
		if(x <= p.mouseX && x+xSize >= p.mouseX && y <= p.mouseY && y+ySize >= p.mouseY){
			if(p.mousePressed){
				pressedLast = true;
				p.fill(color3);
			}else{
				EventRegistry.fire(new Event(Eventtype.GUI_BUTTON_HOVERED, new String[]{ID + " - hovered"}));
				setHovered(true);
				p.fill(color2);
			}
		}else{
			pressedLast = false;
			setHovered(false);
			for(Event event: EventRegistry.getEventsOfType(Eventtype.GUI_BUTTON_HOVERED)){
				event.setHandled();
			}
			p.noFill();
		}
	
		p.stroke(color2);
		p.rect(x, y, xSize, ySize);
	}

	/**
	 * @return the hovered
	 */
	public boolean isHovered() {
		return hovered;
	}

	/**
	 * @param hovered the hovered to set
	 */
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
}
