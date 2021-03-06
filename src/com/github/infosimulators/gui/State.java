package com.github.infosimulators.gui;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import com.github.infosimulators.Main;
import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;
import com.github.infosimulators.gui.gelements.GElement;
import com.github.infosimulators.gui.gelements.SimulationSetupPanel;

/**
 * Saves currently needed GElements and Listeners. Update function updates all
 * saves Elements.
 */
public class State {

	private List<GElement> elements;
	private List<Listener> listeners;
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

		for (GElement element : new ArrayList<GElement>(elements)) {
			element.update(p);
		}

		Collection<String> events = new ArrayList<String>();
		for (Event event : EventRegistry.getEventsOfType(EventType.GUI_BUTTON_PRESSED)) {
			events.add(event.getArgs()[0]);
			event.setHandled();
		}

		for (Event event : EventRegistry.getEventsOfType(EventType.GUI_ELEMENT_HOVERED)) {
			events.add(event.getArgs()[0]);
			event.setHandled();
		}
		
		for(Event event : EventRegistry.getEventsOfType(EventType.GUI_CHECKBOX_VALUE_CHANGE)){
			events.add(event.getArgs()[0]);
			event.setHandled();
		}

		for (Listener listener : new ArrayList<Listener>(listeners)) {
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

	public void removeElementByID(String ID) {
		for (GElement element : new ArrayList<GElement>(elements)) {
			if (element.getID() == ID) {
				elements.remove(element);
			}
		}
	}

	/**
	 * Used to get a specific GElement
	 * 
	 * @param ID
	 *            the ID of the GElement to get
	 * @return the GElement asked for, else null
	 */
	public GElement getElementByID(String ID) {
		for (GElement element : elements) {
			if (element.getID() == ID) {
				return element;
			}
		}
		return null;
	}

	/**
	 * @return the elements
	 */
	public List<GElement> getElements() {
		return elements;
	}

	/**
	 * @param elements
	 *            the elements to set
	 */
	public void setElements(List<GElement> elements) {
		this.elements = elements;
	}

	public List<SimulationSetupPanel> getSimulationSetupPanels() {
		List<SimulationSetupPanel> setupPanels = new ArrayList<SimulationSetupPanel>();
		for (GElement element : elements) {
			if (element.getClass() == SimulationSetupPanel.class) {
				setupPanels.add((SimulationSetupPanel) element);
			}
		}
		return setupPanels;
	}
}