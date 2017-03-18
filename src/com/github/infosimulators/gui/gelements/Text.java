package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;

import processing.core.PApplet;

public class Text extends GElement{
	
	private String text;
	
	private float textSize;
	
	private int textAlign;

	public Text(String ID, String text, float textSize, int textAlign, float x, float y, float xSize, float ySize) {
		super(ID, x, y, xSize, ySize);
		this.text = text;
		this.textSize = textSize;
		this.textAlign = textAlign;
	}

	@Override
	public void update(PApplet p) {
		if(hovered(p)){
			EventRegistry.fire(new Event(EventType.GUI_ELEMENT_HOVERED, new String[] { ID + " - hovered" }));
		}else{
			for (Event event : EventRegistry.getEventsOfType(EventType.GUI_ELEMENT_HOVERED)) {
                if(event.getArgs()[0] == ID + " hovered"){
                	event.setHandled();
                }
            }
		}
		p.stroke(color3);
		p.fill(color3);
		p.textSize(textSize);
		p.textAlign(textAlign);
		p.text(text, x, y, x + xSize, y + ySize);
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
}
