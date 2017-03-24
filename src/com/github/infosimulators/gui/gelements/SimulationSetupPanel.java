package com.github.infosimulators.gui.gelements;

import java.util.ArrayList;
import java.util.List;

import com.github.infosimulators.Main;
import com.github.infosimulators.Simulation;
import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;
import com.github.infosimulators.gui.Listener;
import com.github.infosimulators.physic.Vector2;

import processing.core.PApplet;

public class SimulationSetupPanel extends GElement {

	private List<SetupSpaceObject> objects;

	private Panel panel;

	private boolean pressedLast, setup, setVelocity;

	private float STANDARD_MASS = 2e3f, STANDARD_VELOCITY = 0f, STANDARD_ANGLEVEL = 0f, STANDARD_RADIUS = 20f;

	public SimulationSetupPanel(String ID, float x, float y, float xSize, float ySize) {
		super(ID, x, y, xSize, ySize);
		objects = new ArrayList<SetupSpaceObject>();
		panel = new Panel("InfoPanel", 200, 0, 0, 150, 200);
		panelSetup();
		setup = false;
	}

	@Override
	public void update(PApplet p) {
		if (!setVelocity) {
			if (EventRegistry.getEventsOfType(EventType.GUI_SIMULATION_START).size() > 0) {
				for (Event event : EventRegistry.getEventsOfType(EventType.GUI_SIMULATION_START)) {
					event.setHandled();
				}
				simulationStart();
			}

			if (!setup) {
				setupListeners();
				setup = true;
			}

			if (pressedLast && !p.mousePressed) {
				boolean objectPressed = false;
				for (SetupSpaceObject object : objects) {
					if (object.getActive() && panel.hovered(p)) {
						objectPressed = true;
					}
					if (object.getActive() && PApplet.dist(p.mouseX, p.mouseY, object.getX(),
							object.getY()) <= object.getRadius() + 10) {
						object.notActive();
						objectPressed = true;
					}
					if (!objectPressed && !object.getActive() && PApplet.dist(p.mouseX, p.mouseY, object.getX(),
							object.getY()) <= object.getRadius() + 10) {
						unactivateObjects();
						object.setActive();
						objectPressed = true;
						if (object.getX() + object.getRadius() + 160 > p.width) {
							panel.setX(object.getX() - object.getRadius() - 160);
						} else {
							panel.setX(object.getX() + object.getRadius() + 10);
						}
						if (object.getY() + panel.getySize() > p.height) {
							panel.setY(object.getY() - object.getRadius() - 210);
						} else {
							panel.setY(object.getY());
						}
					}
				}
				if (!objectPressed) {
					unactivateObjects();
					SetupSpaceObject newObject = new SetupSpaceObject(p.mouseX, p.mouseY, STANDARD_MASS,
							STANDARD_VELOCITY, STANDARD_ANGLEVEL, STANDARD_RADIUS);
					objects.add(newObject);
				}
				pressedLast = false;
			}
		} else {
			p.line(getActive().getX(), getActive().getY(), p.mouseX, p.mouseY);
			p.textSize(16);
			p.stroke(255, 0, 0);
			p.fill(255, 0, 0);
			p.textAlign(PApplet.CENTER);
			p.text(Integer.toString(
					(int) (PApplet.dist(getActive().getX(), getActive().getY(), p.mouseX, p.mouseY) / 10)) + "m/s",
					(getActive().getX() + p.mouseX) / 2, (getActive().getY() + p.mouseY) / 2);
			if (pressedLast && !p.mousePressed) {
				getActive().setVelocity(PApplet.dist(getActive().getX(), getActive().getY(), p.mouseX, p.mouseY) / 10);
				getActive().setAngleVel((float) Math.atan2(p.mouseX, p.mouseY));
				setVelocity = false;
				pressedLast = false;
			}
			if (p.mousePressed) {
				pressedLast = true;
			}
		}

		if (hovered(p) && p.mousePressed) {
			pressedLast = true;
		}

		for (SetupSpaceObject object : objects) {
			if (object.getActive() && !setVelocity) {
				EventRegistry.fire(new Event(EventType.GUI_SLIDER_VALUE_SET,
						new String[] { "MassSlider", Float.toString(100 * object.getMass() / 2e4f) }));
				EventRegistry.fire(new Event(EventType.GUI_SLIDER_VALUE_SET,
						new String[] { "RadiusSlider", Float.toString(100 * object.getRadius() / 60f) }));
				panel.update(p);
				p.noFill();
				p.stroke(color3);
				p.rect(object.getX() - object.getRadius(), object.getY() - object.getRadius(), 2 * object.getRadius(),
						2 * object.getRadius());
			}
			p.noStroke();
			p.fill(object.getColor());
			p.ellipse(object.getX(), object.getY(), object.getRadius(), object.getRadius());
		}
		p.noFill();
		p.stroke(color3);
		p.rect(x, y, xSize, ySize);

		Main.getGUI().getState().addListener(new Listener("DeleteObjectButton", new Runnable() {

			@Override
			public void run() {
				objects.remove(getActive());
			}

		}));
	}

	private SetupSpaceObject getActive() {
		for (SetupSpaceObject object : objects) {
			if (object.getActive()) {
				return object;
			}
		}
		return null;
	}

	private void panelSetup() {

		panel.addElement(new Text("PlanetConfigText", "Configuration", 20, PApplet.LEFT, 10, 20, 130, 40));

		panel.addElement(new RectButton("VelocityButton", "Set Velocity", 10, 50, 130, 30));

		panel.addElement(new Text("MassText", "Mass:", 16, PApplet.LEFT, 10, 100, 65, 30));

		panel.addElement(new Slider("MassSlider", 70, 95, 70, 20));

		panel.addElement(new Text("RadiusText", "Radius:", 16, PApplet.LEFT, 10, 135, 65, 30));

		panel.addElement(new Slider("RadiusSlider", 70, 130, 70, 20));

		panel.addElement(new RectButton("DeleteObjectButton", "Delete", 10, 165, 50, 20));

		panel.addElement(new RectButton("ClosePanelButton", "Close", 70, 160, 70, 30));

	}

	private void setupListeners() {

		Main.getGUI().getState().addListener(new Listener("VelocityButton", new Runnable() {

			@Override
			public void run() {
				setVelocity = true;
				pressedLast = false;
			}

		}));
		Main.getGUI().getState().addListener(new Listener("MassSlider", new Runnable() {

			@Override
			public void run() {
				float value = 0;
				for (Event event : EventRegistry.getEventsOfType(EventType.GUI_SLIDER_VALUE_CHANGE)) {
					if (event.getArgs()[0] == "MassSlider") {
						value = Float.parseFloat(event.getArgs()[1]);
					}
				}
				getActive().setMass(PApplet.map(value, 0, 100, 0, 2e4f));
				EventRegistry.fire(new Event(EventType.GUI_SLIDER_VALUE_SET, new String[] { "MassSlider",
						Float.toString(PApplet.map(getActive().getMass(), 0, 2e4f, 0, 100)) }));
			}

		}));
		Main.getGUI().getState().addListener(new Listener("RadiusSlider", new Runnable() {

			@Override
			public void run() {
				float value = 0;
				for (Event event : EventRegistry.getEventsOfType(EventType.GUI_SLIDER_VALUE_CHANGE)) {
					if (event.getArgs()[0] == "RadiusSlider") {
						value = Float.parseFloat(event.getArgs()[1]);
					}
				}
				getActive().setRadius(PApplet.map(value, 0, 100, 0, 60f));
				EventRegistry.fire(new Event(EventType.GUI_SLIDER_VALUE_SET, new String[] { "RadiusSlider",
						Float.toString(PApplet.map(getActive().getRadius(), 0, 60f, 0, 100)) }));
			}

		}));

		Main.getGUI().getState().addListener(new Listener("DeleteObjectButton", new Runnable() {

			@Override
			public void run() {
				objects.remove(getActive());
			}

		}));

		Main.getGUI().getState().addListener(new Listener("ClosePanelButton", new Runnable() {

			@Override
			public void run() {
				unactivateObjects();
			}

		}));
	}

	/**
	 * @return the objects
	 */
	public List<SetupSpaceObject> getObjects() {
		return objects;
	}

	/**
	 * @param objects
	 *            the objects to set
	 */
	public void setObjects(List<SetupSpaceObject> objects) {
		this.objects = objects;
	}

	private void simulationStart() {
		float[][] asteroids = new float[objects.size()][6];
		int i = 0;
		for (SetupSpaceObject object : objects) {
			asteroids[i][0] = new Vector2(object.getX(), object.getY()).magnitude();
			asteroids[i][1] = new Vector2(object.getX(), object.getY()).angle();
			asteroids[i][2] = object.getMass();
			asteroids[i][3] = object.getVelocity();
			asteroids[i][4] = object.getAngleVel();
			asteroids[i][5] = object.getRadius();
		}

		Main.getGUI().getState().addElement(new SimulationPanel("SimulationPanel", new Simulation(asteroids), x, y, xSize, ySize));
		Main.getGUI().getState().removeElementByID(ID);
	}

	private void unactivateObjects() {
		for (SetupSpaceObject object : objects) {
			object.notActive();
		}
	}
}
