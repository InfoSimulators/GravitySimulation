package com.github.infosimulators.gui;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import com.github.infosimulators.Main;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;
import com.github.infosimulators.gui.gelements.GElement;

/**
 * Saves currently needed GElements and Listeners.
 * Update function updates all saves Elements.
 */
public class State {

    private List<GElement> elements;
    private Collection<Listener> listeners;
    private int color1, color2, color3;

    public State(int color1, int color2, int color3) {
        elements = new ArrayList<GElement>();

        listeners = new ArrayList<Listener>();

        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
    }

    public State() {
        elements = new ArrayList<GElement>();

        listeners = new ArrayList<Listener>();

        this.color1 = Main.getGUI().getGUIColor1();
        this.color2 = Main.getGUI().getGUIColor2();
        this.color3 = Main.getGUI().getGUIColor3();
    }

    public void update(PApplet p) {
        p.background(color1);

        for (GElement element : elements) {
            element.update(p);
        }

        Collection<String> events = new ArrayList<String>();
        for (com.github.infosimulators.events.Event event : EventRegistry
                .getEventsOfType(EventType.GUI_BUTTON_PRESSED)) {
            events.add(event.getArgs()[0]);
            event.setHandled();
        }

        for (com.github.infosimulators.events.Event event : EventRegistry
                .getEventsOfType(EventType.GUI_ELEMENT_HOVERED)) {
            events.add(event.getArgs()[0]);
            event.setHandled();
        }

        for (Listener listener : listeners) {
            if (events.contains(listener.getID())) {
                listener.getAction().run();
            }
        }
    }

    public void addElement(GElement g) {
        g.setColor1(color1);
        g.setColor2(color2);
        g.setColor3(color3);
        elements.add(g);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    /**
     * @return the elements
     */
    public List<GElement> getElements() {
        return elements;
    }

    /**
     * @param elements the elements to set
     */
    public void setElements(List<GElement> elements) {
        this.elements = elements;
    }
}