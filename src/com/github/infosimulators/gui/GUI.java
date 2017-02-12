package com.github.infosimulators.gui;

import java.util.ArrayList;

import com.github.infosimulators.gui.GElement.Button;
import com.github.infosimulators.gui.GElement.GElement;

import processing.core.PApplet;

/**
 * GUI class. For interaction with GUI.
 * 
 * Single-instance class!
 * Use {@code GUI.getInstance()} instead of constructor!
 */
public class GUI extends PApplet {
	
	private int backgroundColor;

	/**
	 * Static instance of GUI for public access.
	 */
	private static GUI instance;
	
	/**
	 * tabs
	 */
	private ArrayList<Tab> tabs;
	private int activeTab;
	
	/**
	 * Constructor, to be called automatically.
	 * Do not call manually. Use {@code GUI.getInstance()} instead.
	 */
	public GUI() {
		GUI.instance = this;
		tabs = new ArrayList<Tab>(0);
		backgroundColor = 0;
	}
	
	/**
	 * Called once on startup
	 */
	public void settings() {
		size(1000, 1000); // has to be here instead setup()
	}
	
	/**
	 * Called once after startup
	 */
	public void setup() {
		ArrayList<GElement> tes = new ArrayList<GElement>();
		
		Runnable backgroundColorChangeUp = new Runnable(){

			@Override
			public void run() {
				setBackgroundColor(getBackgroundColor() + 1);
				
			}
			
		};
		
		Runnable backgroundColorChangeDown = new Runnable(){

			@Override
			public void run() {
				setBackgroundColor(getBackgroundColor() - 1);
				
			}
			
		};
		
		tes.add(new Button(backgroundColorChangeUp, 40, 80, 80, 80));
		tes.add(new Button(backgroundColorChangeDown, 160, 80, 80, 80));
		
		tabs.add(new Tab(instance, "Main", tes));
		tabs.add(new Tab(instance, "1", new ArrayList<GElement>()));
		activeTab = 0;
	}
	
	/**
	 * Called each frame
	 */
	public void draw() {
		// Phase 1: Basic Necessities
		background(backgroundColor);
		if (mousePressed){
			mouseActions();
		}
		
		//Phase 2: Objects in the Simulation
		
		//Phase 3: GUI
		displayTabs();
		displayContent();
	}
	
	private void displayTabs(){
		stroke(255);
		textSize(height/20 - 20);
		for (int i = 0; i < tabs.size(); i++){
			noFill();
			rect(i*width/tabs.size(), 0, (i+1)*width/tabs.size(), height/20);
			fill(255);
			text(tabs.get(i).getHeader(), i*width/tabs.size() + 10, 10, (i+1)*width/tabs.size() - 10, height/20 - 10);
		}
	}
	
	private void displayContent(){
		tabs.get(activeTab).update();
	}
	
	private void mouseActions(){
		if(mouseY <= height/20){
			activeTab = mouseX*tabs.size()/width;
		}
	}
	

	/**
	 * Configures this class as main processing class.
	 * Use instead of constructor.
	 * 
	 * @return Single-instance of this class.
	 */
	public static GUI getInstance() {
		if (instance == null)
			main("com.github.infosimulators.gui.GUI");
		return instance;
	}
	
	public int getBackgroundColor(){
		return backgroundColor;
	}
	
	public void setBackgroundColor(int color){
		backgroundColor = color;
	}
}
