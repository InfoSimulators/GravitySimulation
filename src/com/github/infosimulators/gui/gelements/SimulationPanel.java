package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.Simulation;
import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;
import com.github.infosimulators.physic.PhysicsObject;

import processing.core.PApplet;

/**
 * GElement to display a Simulation
 *
 */
public class SimulationPanel extends GElement {

	// the Simulation saved by the Panel will get updated every tick
	private Simulation simulation;

	// optional, shows a red dot at each center of mass, if the planets are to
	// small to see
	private boolean showRedDots;

	public SimulationPanel(String ID, Simulation simulation, float x, float y, float xSize, float ySize) {
		super(ID, x, y, xSize, ySize);
		this.simulation = simulation;
		simulation.setDeltaTime(0.00000000001f);

		setShowRedDots(false);
	}

	// called every tick, updates and displays the simulation
	@Override
	public void update(PApplet p) {
		p.noFill();
		p.stroke(color3);
		p.rect(x, y, xSize, ySize);

		// displays the simulation
		displaySimulation(p, this.simulation, showRedDots, x, y, xSize, ySize);

		// updates the simulation
		simulation.update();

		// registers if the red dots should be shown
		for (Event event : EventRegistry.getEventsOfType(EventType.GUI_SIMULATION_SETDOTS)) {
			if (event.getArgs()[0] == ID) {
				showRedDots = !showRedDots;
				event.setHandled();
			}
		}
	}

	// can set simulation after creation
	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}

	// static method to be called without a simulationPanel in place
	public static void displaySimulation(PApplet p, Simulation simulation, boolean showDots, float x, float y,
			float xSize, float ySize) {

		float xMin = -100;
		float xMax = 100;
		float yMin = -100;
		float yMax = 100;

		// calculates the smallest and largest coordinates.
		for (PhysicsObject object : simulation.getContent()) {
			if (object.getPosition().x - object.getRadius() < xMin) {
				xMin = object.getPosition().x;
			}
			if (object.getPosition().x + object.getRadius() > xMax) {
				xMax = object.getPosition().x;
			}
			if (object.getPosition().y - object.getRadius() < yMin) {
				yMin = object.getPosition().y;
			}
			if (object.getPosition().y + object.getRadius() > yMax) {
				yMax = object.getPosition().y;
			}
		}

		// calculates the length of each side
		float xDelta = xMax - xMin;
		float yDelta = yMax - yMin;

		// increases the smaller delta so the display is not stretched in any
		// way
		if (xDelta > yDelta) {
			yMin -= (xDelta - yDelta) / 2;
			yMax += (xDelta - yDelta) / 2;
		} else if (xDelta < yDelta) {
			xMin -= (yDelta - xDelta) / 2;
			xMax += (yDelta - xDelta) / 2;
		}

		// shows the planets
		for (PhysicsObject object : simulation.getContent()) {
			p.ellipse(PApplet.map(object.getPosition().x, xMin, xMax, x, x + xSize),
					PApplet.map(object.getPosition().y, yMin, yMax, y, y + ySize), object.getRadius(), object.getRadius());
		}

		// optional display of red dots at the center of mass
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
	 * @param showRedDots
	 *            the showRedDots to set
	 */
	public void setShowRedDots(boolean showRedDots) {
		this.showRedDots = showRedDots;
	}

}
