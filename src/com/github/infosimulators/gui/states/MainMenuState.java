package com.github.infosimulators.gui.states;

import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.Eventtype;
import com.github.infosimulators.gui.State;
import com.github.infosimulators.gui.gelements.GElement;
import com.github.infosimulators.gui.gelements.RectButton;

import processing.core.PApplet;

public class MainMenuState extends State{
	
	private GElement[] elements;

	public MainMenuState(PApplet p) {
		super(p);
		elements = new GElement[1];
		elements[0] = new RectButton("Test", p, 10, 10, 100, 100);
	}

	
	/**
	 * Called every active frame
	 * Updates all GElements and processes possible inputs
	 */
	@Override
	public void update() {
		p.background(0);

		for (GElement e : elements){
			e.update();
		}
		
		//Takes care of button presses
		for (int i = 0; i < EventRegistry.getEventsOfType(Eventtype.GUI_BUTTON_PRESSED).size(); i++){
			buttonAction(EventRegistry.getEventsOfType(Eventtype.GUI_BUTTON_PRESSED).get(i).getArgs()[0]);
			EventRegistry.getEventsOfType(Eventtype.GUI_BUTTON_PRESSED).get(i).setHandled();
		}
	}
	
	/**
	 * Called when a button is pressed to determine the action
	 * @param arg: The information given by the button, in most cases the button's ID
	 */
	private void buttonAction(String arg){
		switch (arg){
		case "Test": System.out.println("test");
		}
	}

}
