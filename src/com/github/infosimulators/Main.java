package com.github.infosimulators;

import com.github.infosimulators.genetictrainer.Evaluator;
import com.github.infosimulators.genetictrainer.GeneticTrainer;
import com.github.infosimulators.genetictrainer.ParameterTypes;
import com.github.infosimulators.gui.GUI;

//TODO: Add States to main

/**
 * Main class, handling program in general.
 */
public class Main {

	public float x;
	public float y;

	/**
	 * Main method, called on startup.
	 * 
	 * @param args
	 *            Arguments passed.
	 */
	public static void main(String[] args) {
		// creates and runs processing sketch
		GUI gui = GUI.getInstance();

		/*
		 * Some ideas for GUI-Objects:
		 *
		 * Panel panel = gui.createPanel(); Graph graph = panel.createGraph();
		 * graph.setType(GRAPH_TYPE); graph.setData(DATA); Slider slider =
		 * panel.createSlider(); Listener listener = new Listener();
		 * slider.addListener(listener); World world = gui.getWorld(); // the
		 * world objects are in WorldObject object = world.addObject(x, y,
		 * radius, color); obj.move(deltaX, deltaY);
		 *
		 */

		ParameterTypes[] format = new ParameterTypes[] { ParameterTypes.BOOLEAN, ParameterTypes.INT,
				ParameterTypes.DOUBLE };
		Evaluator evaluator = new Evaluator(format) {
		};
		GeneticTrainer trainer = new GeneticTrainer(evaluator, 50);

		for (int i = 0; i < 10; i++) {
			trainer.evaluateCurrent();
			trainer.generateNextGeneration();
		}

	}
}