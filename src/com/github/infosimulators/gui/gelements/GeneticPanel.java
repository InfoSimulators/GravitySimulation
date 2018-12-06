package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.events.EventRegistry;
import com.github.infosimulators.events.EventType;
import com.github.infosimulators.genetictrainer.Evaluator;
import com.github.infosimulators.genetictrainer.GeneticTrainer;
import com.github.infosimulators.genetictrainer.SimplestEvaluator;

import processing.core.PApplet;

public class GeneticPanel extends GElement {

	private Evaluator evaluator;

	private GeneticTrainer trainer;

	private int MAX_TICKS, ticks, generation;

	private boolean showSimulation;

	public GeneticPanel(String ID, int numPlanets, int genomesPerGeneration, boolean showSimulation, float x, float y,
			float xSize, float ySize) {
		super(ID, x, y, xSize, ySize);

		evaluator = new SimplestEvaluator();

		trainer = new GeneticTrainer(numPlanets, 6, genomesPerGeneration);
		trainer.generateFirstGeneration();
		trainer.startSimulations();

		this.showSimulation = showSimulation;

		generation = 1;
		MAX_TICKS = 60;
		ticks = 0;
	}

	@Override
	public void update(PApplet p) {

		for (Event event : EventRegistry.getEventsOfType(EventType.GUI_AUTO_SHOWSIMULATION)) {
			if (event.getArgs()[0] == ID) {
				showSimulation = !showSimulation;
				event.setHandled();
			}
		}

		if (showSimulation) {
			SimulationPanel.displaySimulation(p, trainer.getSimulations().get(0), false, x, y, xSize, ySize);
		}

		trainer.step();

		if (!trainer.isRunningSimulations() || ticks > MAX_TICKS) {
			float[] results = evaluator.eval(trainer.getEvalEvents());
			try {
				trainer.generateNextGeneration(results, evaluator.isCostFunction());
			} catch (Exception e) {
				e.printStackTrace();
			}
			trainer.startSimulations();
			generation++;
			ticks = 0;
		}
		p.textSize(40);
		p.text(generation, 100, 100);
		ticks++;
	}

}
