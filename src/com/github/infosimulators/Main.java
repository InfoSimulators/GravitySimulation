package com.github.infosimulators;

import com.github.infosimulators.genetictrainer.GeneticTrainer;
import com.github.infosimulators.genetictrainer.GeneticTrainer.Parameter;
import com.github.infosimulators.gui.GUI;

/**
 * Main class, handling program in general.
 */
public class Main {

	public float x;
	public float y;

	/**
	 * Main method, called on startup.
	 * @param args Arguments passed.
	 */
	public static void main(String[] args) {
		// creates and runs processing sketch
		gui = GUI.getInstance();

		/*
		 * Some ideas for GUI-Objects:
		 *
		 * Panel panel = gui.createPanel();
		 * Graph graph = panel.createGraph();
		 * graph.setType(GRAPH_TYPE);
		 * graph.setData(DATA);
		 * Slider slider = panel.createSlider();
		 * Listener listener = new Listener();
		 * slider.addListener(listener);
		 * World world = gui.getWorld(); // the world objects are in
		 * WorldObject object = world.addObject(x, y, radius, color);
		 * obj.move(deltaX, deltaY);
		 *
		 */

		GeneticTrainer trainer = new GeneticTrainer();
		Parameter[] parameters = new Parameter[5];
		trainer.train(parameters);
	}
}