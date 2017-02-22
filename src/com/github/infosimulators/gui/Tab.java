package com.github.infosimulators.gui;

import java.util.ArrayList;

import com.github.infosimulators.gui.GElement.GElement;

import processing.core.PApplet;

public class Tab {
	
	private String header;
	private ArrayList<GElement>	elements;
	private PApplet p;

	public Tab(PApplet p, String header, ArrayList<GElement> elements){
		this.p = p;
		this.setHeader(header);
		this.elements = elements;
	}
	
	/*
	 * Updates all elements inside the tab.
	 */
	public void update(){
		for (int i = 0; i < elements.size(); i++){
			elements.get(i).update(p);
		}
	}

	/*
	 * Method to add an element to the tab.
	 */
	public void addElement(GElement e){
		elements.add(e);
	}
	
	/*
	 * Getters and setters.
	 */
	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
}

