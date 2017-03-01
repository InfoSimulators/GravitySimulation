package com.github.infosimulators.gui;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

import com.github.infosimulators.gui.gelements.GElement;

public class State {
	
	private List<GElement> elements;
	private int color1, color2, color3;
		
	public State(int color1, int color2, int color3){
		elements = new ArrayList<GElement>();
		
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
	}
	
	public State(){
		elements = new ArrayList<GElement>();
		
		this.color1 = GUI.getInstance().getGUIColor1();
		this.color2 = GUI.getInstance().getGUIColor2();
		this.color3 = GUI.getInstance().getGUIColor3();
	}
		
	public void update(PApplet p){
		p.background(color1);
		
		for(GElement element: elements){
			element.update(p);
		}
	}
	
	public void addElement(GElement g){
		g.setColor1(color1);
		g.setColor2(color2);
		g.setColor3(color3);
		elements.add(g);
	}
}
