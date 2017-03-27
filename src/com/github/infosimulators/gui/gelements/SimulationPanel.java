package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.Simulation;
import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;
import com.github.infosimulators.physic.PhysicsObject;
import com.github.infosimulators.physic.Vector2;

import processing.core.PApplet;

public class SimulationPanel extends GElement {

	private Simulation simulation;
	
	private boolean showRedDots;

	public SimulationPanel(String ID, Simulation simulation, float x, float y, float xSize, float ySize) {
		super(ID, x, y, xSize, ySize);
		this.simulation = simulation;
		simulation.setDeltaTime(0.001f);

		setShowRedDots(false);
	}

	@Override
	public void update(PApplet p) {
		p.noFill();
		p.stroke(color3);
		p.rect(x, y, xSize, ySize);

		displaySimulation(p, this.simulation, showRedDots, x, y, xSize, ySize);
		simulation.update();
		
		for(Event event : EventRegistry.getEventsOfType(EventType.GUI_SIMULATION_SETDOTS)){
			if(event.getArgs()[0] == ID){
				showRedDots = !showRedDots;
				event.setHandled();
			}
		}
	}

	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}

	public static void displaySimulation(PApplet p, Simulation simulation, boolean showDots, float x, float y, float xSize, float ySize) {

		float xMin = 0;
		float xMax = 0;
		float yMin = 0;
		float yMax = 0;

		for (PhysicsObject object : simulation.getContent()) {
			for (Vector2 vec : object.getVertices()) {
				if (vec.x < xMin || xMin == 0) {
					xMin = vec.x;
				} else if (vec.x > xMax || xMax == 0) {
					xMax = vec.x;
				}
				if (vec.y < yMin || yMin == 0) {
					yMin = vec.y;
				} else if (vec.y > yMax || yMax == 0) {
					yMax = vec.y;
				}
			}
		}

		float xDelta = xMax - xMin;
		float yDelta = yMax - yMin;

		if (xDelta > yDelta) {
			yMin -= (xDelta - yDelta) / 2;
			yMax += (xDelta - yDelta) / 2;
		} else if (xDelta < yDelta) {
			xMin -= (yDelta - xDelta) / 2;
			xMax += (yDelta - xDelta) / 2;
		}

		for (PhysicsObject object : simulation.getContent()) {
			p.beginShape();
			for (Vector2 vec : object.getVertices()) {
				p.vertex(PApplet.map(vec.x, xMin, xMax, x, x + xSize), PApplet.map(vec.y, yMin, yMax, y, y + ySize));
				if(vec.x > xMax || vec.x < xMin || vec.y > yMax || vec.y < yMin){
					System.out.println("Calc off");
				}
			}
			p.endShape();
		}

		if (showDots) {
			for (PhysicsObject object : simulation.getContent()) {
				p.stroke(255, 0, 0);
				p.fill(255, 0, 0);
				p.ellipse(PApplet.map(object.getPosition().x, xMin, xMax, x, x + xSize),
						PApplet.map(object.getPosition().y, yMin, yMax, y, y + ySize), 8, 8);
			}
		}
	}

	/**
	 * @return the showRedDots
	 */
	public boolean isShowRedDots() {
		return showRedDots;
	}

	/**
	 * @param showRedDots the showRedDots to set
	 */
	public void setShowRedDots(boolean showRedDots) {
		this.showRedDots = showRedDots;
	}

}
