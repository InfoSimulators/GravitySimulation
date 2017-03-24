package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;

import processing.core.PApplet;

public class Slider extends GElement {

	private float value;

	public Slider(String ID, float x, float y, float xSize, float ySize) {
		super(ID, x, y, xSize, ySize);
		value = 50;
	}

	@Override
	public void update(PApplet p) {

		for (Event event : EventRegistry.getEventsOfType(EventType.GUI_SLIDER_VALUE_SET)) {
			if (event.getArgs()[0] == ID) {
				value = Float.parseFloat(event.getArgs()[1]);
			}
		}

		for (Event event : EventRegistry.getEventsOfType(EventType.GUI_ELEMENT_HOVERED)) {
			if (event.getArgs()[0] == ID) {
				event.setHandled();
			}
		}

		if (hovered(p)) {
			if (p.mousePressed) {
				value = 100 * (p.mouseX - x) / xSize;
				for (Event event : EventRegistry.getEventsOfType(EventType.GUI_SLIDER_VALUE_CHANGE)) {
					if (event.getArgs()[0] == ID) {
						event.setHandled();
					}
				}
			}
			EventRegistry.fire(new Event(EventType.GUI_ELEMENT_HOVERED, new String[] { ID }));
		}

		EventRegistry.fire(new Event(EventType.GUI_SLIDER_VALUE_CHANGE, new String[] { ID, Float.toString(value) }));

		p.noFill();
		p.stroke(color3);
		p.rect(x, y + ySize / 10, xSize, ySize * 8 / 10);
		p.stroke(255, 0, 0);
		p.strokeWeight(3);
		p.line(x + xSize * value / 100, y, x + xSize * value / 100, y + ySize);
		p.strokeWeight(1);
	}

}
