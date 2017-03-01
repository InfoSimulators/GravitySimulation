package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.Eventtype;

import processing.core.PApplet;

public class RectButton extends GElement{

	private boolean pressedLast;//, hovered;
	
	private String ID;
	
	public RectButton(String ID, float x, float y, float xSize, float ySize){
		super(x, y, xSize, ySize);
		this.ID = ID;
		
		pressedLast = false;
		//hovered = false;
	}
	
	public void update(PApplet p){
		p.stroke(color1[0], color1[1], color1[2]);
		p.rect(x, y, xSize, ySize);
		
		if(pressedLast && !p.mousePressed){
			EventRegistry.fire(new Event(Eventtype.GUI_BUTTON_PRESSED, new String[]{ID}));
			pressedLast = false;
		}
		
		if(x <= p.mouseX && x+xSize >= p.mouseX && y <= p.mouseY && y+ySize >= p.mouseY){
			if(p.mousePressed){
				pressedLast = true;
				p.fill(color3[0], color3[1], color3[2]);
			}else{
				//hovered = true;
				p.fill(color2[0], color2[1], color2[2]);
			}
		}else{
			pressedLast = false;
			//hovered = false;
			p.noFill();
		}
	}
}
