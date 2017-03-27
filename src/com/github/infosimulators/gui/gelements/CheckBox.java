package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;

import processing.core.PApplet;

public class CheckBox extends GElement {

	private boolean pressedLast, active;

	public CheckBox(String ID, boolean active, float x, float y, float xSize, float ySize) {
		super(ID, x, y, xSize, ySize);
		this.active = active;
	}

	@Override
	public void update(PApplet p) {
		if (pressedLast && !p.mousePressed) {
			active = !active;
			pressedLast = false;
			for(Event event : EventRegistry.getEventsOfType(EventType.GUI_CHECKBOX_VALUE_CHANGE)){
				if(event.getArgs()[0] == ID){
					event.setHandled();
				}
			}
			EventRegistry.fire(
					new Event(EventType.GUI_CHECKBOX_VALUE_CHANGE, new String[] { ID, Boolean.toString(active) }));
		}
		if (hovered(p) && p.mousePressed) {
			pressedLast = true;
		}

		p.stroke(color2);
		if (active) {
			p.fill(0, 255, 0);
		} else {
			p.fill(255, 0, 0);
		}
		p.rect(x, y, xSize, ySize);
	}

}
