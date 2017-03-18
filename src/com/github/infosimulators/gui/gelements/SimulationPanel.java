package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.Simulation;
import com.github.infosimulators.physic.PhysicsObject;

import processing.core.PApplet;

public class SimulationPanel extends GElement{

	private Simulation simulation;
	
	public SimulationPanel(String ID, Simulation simulation, float x, float y, float xSize, float ySize) {
		super(ID, x, y, xSize, ySize);
		this.simulation = simulation;
	}

	@Override
	public void update(PApplet p) {
		simulation.update();
		for(PhysicsObject asteroid : simulation.getContent()){
			p.ellipse(asteroid.getPosition().x, asteroid.getPosition().y, 8, 8);
		}
	}

}
