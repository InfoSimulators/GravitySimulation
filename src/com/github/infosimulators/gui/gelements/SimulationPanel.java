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
			if(object.getPosition().x < xMin){
				xMin = object.getPosition().x - 10;
			}else if(object.getPosition().x > xMax){
				xMax = object.getPosition().x + 10;
			}
			if(object.getPosition().y < yMin){
				xMin = object.getPosition().y - 10;
			}else if(object.getPosition().y > yMax){
				xMax = object.getPosition().y + 10;
			}
		}

		for (PhysicsObject object : simulation.getContent()) {
			p.beginShape();
			for (Vector2 vec : object.collider.getVertices()) {
				//p.vertex(PApplet.map(vec.x, -necessaryDistance - 10, necessaryDistance + 10, x, xSize), PApplet.map(vec.y, -necessaryDistance - 10, necessaryDistance + 10, y, ySize));
				p.vertex(PApplet.norm(vec.x, xMin, xMax)*xSize + x, PApplet.norm(vec.y, yMin, yMax)*ySize + y);
				System.out.println(PApplet.norm(vec.x, xMin, xMax));
			}
			p.endShape();
		}
	}
	
}
