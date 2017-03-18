package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;

import processing.core.PApplet;

public class TextField extends GElement{

    private int max;
    
    private String value;

    private boolean active;

    public TextField(String ID, float x, float y, float xSize, float ySize) {
        super(ID, x, y, xSize, ySize);

        value = "";
        max = 8;

        active = false;
    }

    public TextField(String ID, int max, float x, float y, float xSize, float ySize) {
        super(ID, x, y, xSize, ySize);

        value = "";
        this.max = max;

        active = false;
    }

    @Override
    public void update(PApplet p) {
        if (hovered(p)) {
            if (!active) {
                active = p.mousePressed ? true : false;
            }
            EventRegistry.fire(new Event(EventType.GUI_ELEMENT_HOVERED, new String[] { ID + " - hovered" }));
        } else {
            if (active) {
                active = p.mousePressed ? false : true;
            }
            for (Event event : EventRegistry.getEventsOfType(EventType.GUI_ELEMENT_HOVERED)) {
                if(event.getArgs()[0] == ID + " hovered"){
                	event.setHandled();
                }
            }
        }

        if (active) {
            for (Event event : EventRegistry.getEventsOfType(EventType.KEY_RELEASED)) {
                keyPress(event.getArgs()[0]);
                event.setHandled();
            }
        }

        p.stroke(color2);
        p.fill(active ? color1 : color3);
        p.rect(x, y, xSize, ySize);

        p.fill(color2);
        p.textSize(ySize);
        p.textAlign(PApplet.RIGHT, PApplet.TOP);
        p.text(value, x + xSize - 2, y + 5);
        
    }

    private void keyPress(String k) {

        if (k.charAt(0) == PApplet.BACKSPACE && value.length() > 0) {
            value = value.substring(0, value.length() - 1);
        }

        if (value.length() < max) {
            switch (k.charAt(0)) {
            case 'a':case 'b':case 'c':case 'd':case 'e':case 'f':case 'g':case 'h':case 'i':case 'j':
            case 'k':case 'l':case 'm':case 'n':case 'o':case 'p':case 'q':case 'r':case 's':case 't':
            case 'u':case 'v':case 'w':case 'x':case 'y':case 'z': 
            case 'A':case 'B':case 'C':case 'D':case 'E':case 'F':case 'G':case 'H':case 'I':case 'J':
            case 'K':case 'L':case 'M':case 'N':case 'O':case 'P':case 'Q':case 'R':case 'S':case 'T':
            case 'U':case 'V':case 'W':case 'X':case 'Y':case 'Z':
            	value += k; break;
            case PApplet.ENTER:
                setActive(false);

            }
        }

        for (Event event : EventRegistry.getEventsOfType(EventType.GUI_TEXTFIELD_VALUE)) {
            if (event.getArgs()[0] == ID) {
                event.setHandled();
            }
        }
        EventRegistry.fire(new Event(EventType.GUI_TEXTFIELD_VALUE, new String[] { ID, value}));

    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    public void modifyValue(int value) {
        this.value += value;
    }
}
