package com.github.infosimulators.gui.gelements;

import com.github.infosimulators.genetictrainer.Evaluator;
import com.github.infosimulators.genetictrainer.GeneticTrainer;
import com.github.infosimulators.genetictrainer.SimplestEvaluator;

import processing.core.PApplet;

public class GeneticPanel extends GElement{
	
	private Evaluator evaluator;
	
	private GeneticTrainer trainer;
	
	private int MAX_TICKS, ticks, generation;
	
	public GeneticPanel(String ID, int numPlanets, int genomesPerGeneration, float x, float y, float xSize, float ySize) {
		super(ID, x, y, xSize, ySize);
		
		evaluator = new SimplestEvaluator();
		
		trainer = new GeneticTrainer(numPlanets, 6, genomesPerGeneration);
		trainer.generateFirstGeneration();
		trainer.startSimulations();
		
		generation = 1;
		MAX_TICKS = 600;
		ticks = 0;
	}

	@Override
	public void update(PApplet p) {
		trainer.step();
		if(!trainer.isRunningSimulations() || ticks > MAX_TICKS){
			float[] results = evaluator.eval(trainer.getEvalEvents());
			System.out.println(results);
			try {
				trainer.generateNextGeneration(results, evaluator.isCostFunction());
			} catch (Exception e) {
				e.printStackTrace();
			}
			generation++;
			ticks = 0;
		}
		p.textSize(40);
		p.text(generation, 100, 100);
		ticks++;
	}

}
