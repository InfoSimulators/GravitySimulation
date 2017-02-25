package com.github.infosimulators.gui;

import processing.core.PApplet;

public abstract class State {

	//State-manager
	private static State currentState = null;
		
	public static void setState(State state){
		currentState = state;
	}
		
	public static State getState(){
		return currentState;
	}
	
	//actual Class	
	protected PApplet p;
		
	public State(PApplet p){
		this.p = p;
	}
		
	public abstract void update();
}
