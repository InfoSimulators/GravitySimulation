package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;

import processing.core.PApplet;

/**
 * Basic GElement. Rectangular Button.
 * Events are fired, if Button is pressed or hovered.
 */
public class RectButton extends GElement {

    private boolean pressedLast;

    public RectButton(String ID, float x, float y, float xSize, float ySize) {
        super(ID, x, y, xSize, ySize);

        pressedLast = false;
    }

    public void update(PApplet p) {

        if (pressedLast && !p.mousePressed) {
            EventRegistry.fire(new Event(EventType.GUI_BUTTON_PRESSED, new String[] { ID }));
            pressedLast = false;
        }

        if (hovered(p)) {
            if (p.mousePressed) {
                pressedLast = true;
                p.fill(color3);
            } else {
                p.fill(color2);
            }
            EventRegistry.fire(new Event(EventType.GUI_ELEMENT_HOVERED, new String[] { ID + " - hovered" }));
        } else {
            pressedLast = false;
            for (Event event : EventRegistry.getEventsOfType(EventType.GUI_ELEMENT_HOVERED)) {
                if(event.getArgs()[0] == ID + " - hovered"){
                	event.setHandled();
                }
            }
            p.noFill();
        }

        p.stroke(color2);
        p.rect(x, y, xSize, ySize);
    }
}