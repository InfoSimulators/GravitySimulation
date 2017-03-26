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

		float necessaryDistance = xSize/2;
		for (PhysicsObject object : simulation.getContent()) {
			if(Math.abs(object.getPosition().x) > necessaryDistance){
				necessaryDistance = Math.abs(object.getPosition().x);
			}
			if(Math.abs(object.getPosition().y) > necessaryDistance){
				necessaryDistance = Math.abs(object.getPosition().y);
			}
		}

		for (PhysicsObject object : simulation.getContent()) {
			p.beginShape();
			for (Vector2 vec : object.collider.getVertices()) {
				p.vertex(PApplet.map(vec.x, -necessaryDistance, necessaryDistance, x, xSize), PApplet.map(vec.y, -necessaryDistance, necessaryDistance, y, ySize));
			}
			p.endShape();
		}
	}

}
