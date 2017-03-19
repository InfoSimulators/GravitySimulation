package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.Simulation;
import com.github.infosimulators.physic.PhysicsObject;

import processing.core.PApplet;

public class SimulationPanel extends GElement{

	private Simulation simulation;
	
	private int scale = 10;
	
	public SimulationPanel(String ID, Simulation simulation, float x, float y, float xSize, float ySize) {
		super(ID, x, y, xSize, ySize);
		this.simulation = simulation;
	}

	@Override
	public void update(PApplet p) {
		simulation.update();
		int avgX = 0;
		int avgY = 0;
		for(PhysicsObject asteroid : simulation.getContent()){
			avgX += asteroid.getPosition().x / simulation.getContent().size();
			avgY += asteroid.getPosition().y / simulation.getContent().size();
		}
		p.translate(p.width/2, p.height/2);
		for(PhysicsObject asteroid : simulation.getContent()){
			p.ellipse((asteroid.getPosition().x - avgX)/scale, (asteroid.getPosition().y - avgY)/scale, 8, 8);
		}
	}

}
