package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;

import processing.core.PApplet;

/**
 * GElement able to store numbers and get Input from the Keyboard.
 *
 */
public class NumberField extends GElement {

    private int value, max;

    private boolean active, hovered;

    public NumberField(String ID, float x, float y, float xSize, float ySize) {
        super(ID, x, y, xSize, ySize);

        value = 0;
        max = 8;

        active = false;
        hovered = false;
    }

    public NumberField(String ID, int max, float x, float y, float xSize, float ySize) {
        super(ID, x, y, xSize, ySize);

        value = 0;
        this.max = max;

        active = false;
        hovered = false;
    }

    @Override
    public void update(PApplet p) {
        if (p.mouseX > x && p.mouseY > y && p.mouseX < x + xSize && p.mouseY < y + ySize) {
            hovered = true;
            if (!active) {
                active = p.mousePressed ? true : false;
            }
        } else {
            hovered = false;
            if (active) {
                active = p.mousePressed ? false : true;
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

        if (value != 0) {
            p.fill(color2);
            p.textSize(ySize);
            p.textAlign(PApplet.RIGHT, PApplet.TOP);
            p.text(Integer.toString(value), x + xSize - 2, y + 5);
        }
    }

    private void keyPress(String k) {

        if (k.charAt(0) == PApplet.BACKSPACE) {
            value = (value - value % 10) / 10;
        }

        if (Integer.toString(value).length() < max) {
            switch (k.charAt(0)) {
            case '0':
                value = Integer.parseInt(Integer.toString(value) + "0");
                break;
            case '1':
                value = Integer.parseInt(Integer.toString(value) + "1");
                break;
            case '2':
                value = Integer.parseInt(Integer.toString(value) + "2");
                break;
            case '3':
                value = Integer.parseInt(Integer.toString(value) + "3");
                break;
            case '4':
                value = Integer.parseInt(Integer.toString(value) + "4");
                break;
            case '5':
                value = Integer.parseInt(Integer.toString(value) + "5");
                break;
            case '6':
                value = Integer.parseInt(Integer.toString(value) + "6");
                break;
            case '7':
                value = Integer.parseInt(Integer.toString(value) + "7");
                break;
            case '8':
                value = Integer.parseInt(Integer.toString(value) + "8");
                break;
            case '9':
                value = Integer.parseInt(Integer.toString(value) + "9");
                break;
            case PApplet.ENTER:
                setActive(false);

            }
        }

        for (Event event : EventRegistry.getEventsOfType(EventType.GUI_NUMBERFIELD_VALUE)) {
            if (event.getArgs()[0] == ID) {
                event.setHandled();
            }
        }
        EventRegistry.fire(new Event(EventType.GUI_NUMBERFIELD_VALUE, new String[] { ID, Integer.toString(value) }));

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

    /**
     * @return the hovered
     */
    public boolean isHovered() {
        return hovered;
    }

    public void modifyValue(int value) {
        this.value += value;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

}