package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.Simulation;
import com.github.infosimulators.physic.PhysicsObject;
import com.github.infosimulators.physic.Vector2;

import processing.core.PApplet;

public class SimulationPanel extends GElement {

	private Simulation simulation;

	private int ticks;

	public SimulationPanel(String ID, Simulation simulation, float x, float y, float xSize, float ySize) {
		super(ID, x, y, xSize, ySize);
		this.simulation = simulation;
		ticks = 0;
	}

	@Override
	public void update(PApplet p) {
		p.noFill();
		p.stroke(color3);
		p.rect(x, y, xSize, ySize);

		if (ticks > 10) {
			simulation.update();
			ticks -= 10;
		}
		ticks++;

		displaySimulation(p, this.simulation, x, y, xSize, ySize);
	}

	public void setSimulation(Simulation simulation){
		this.simulation = simulation;
	}
	
	public static void displaySimulation(PApplet p, Simulation simulation, float x, float y, float xSize, float ySize){
		
		float xMin = 0;
		float xMax = 0;
		float yMin = 0;
		float yMax = 0;
		
		for (PhysicsObject object : simulation.getContent()) {
			if(object.getPosition().x < xMin || xMin == 0){
				xMin = object.getPosition().x ;
			}else if(object.getPosition().x > xMax || xMax == 0){
				xMax = object.getPosition().x ;
			}
			if(object.getPosition().y < yMin || yMin == 0){
				xMin = object.getPosition().y;
			}else if(object.getPosition().y > yMax || yMax == 0){
				xMax = object.getPosition().y;
			}
		}

		p.stroke(120);
		p.fill(255);
		for (PhysicsObject object : simulation.getContent()) {
			p.beginShape();
			for (Vector2 vec : object.getVertices()) {
				//p.vertex(PApplet.map(vec.x, -necessaryDistance - 10, necessaryDistance + 10, x, xSize), PApplet.map(vec.y, -necessaryDistance - 10, necessaryDistance + 10, y, ySize));
				p.vertex(PApplet.norm(vec.x, xMin - 50, xMax + 50)*xSize + x, PApplet.norm(vec.y, yMin - 50, yMax + 50)*ySize + y);
				if(vec.x > xMax + 100){
				}
			}
			p.endShape();
		}
	}
	
}
