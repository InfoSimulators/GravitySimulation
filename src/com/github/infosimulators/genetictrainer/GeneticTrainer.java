package com.github.infosimulators.genetictrainer;

/**
 * Trainer class for finding optimal parameters.
 */
public class GeneticTrainer {

	private Evaluator evaluator;
	private int geneCount;
	private int genomesPerGeneration;

	/**
	 * constructor
	 */
	public GeneticTrainer(Evaluator evaluator, int genomesPerGeneration) {
		setEvaluator(evaluator);
		setGenomesPerGeneration(genomesPerGeneration);
	}

	public void evaluateCurrent() {

	}

	public void generateNextGeneration() {

	}

	private void updateGeneCount(ParameterTypes[] format) {
		this.geneCount = 0;
		for (ParameterTypes p : format) {
			switch (p) {
			case BOOLEAN:
				geneCount++;
				break;
			case BYTE:
				geneCount += 8;
				break;
			case SHORT:
				geneCount += 16;
				break;
			case INT:
				geneCount += 32;
				break;
			case LONG:
				geneCount += 64;
				break;
			case FLOAT:
				geneCount += 32;
				break;
			case DOUBLE:
				geneCount += 64;
				break;
			}
		}
	}

	public int getGeneCount() {
		return geneCount;
	}

	public void setEvaluator(Evaluator evaluator) {
		this.evaluator = evaluator;
		this.updateGeneCount(evaluator.getFormat());
	}

	public Evaluator getEvaluator() {
		return evaluator;
	}

	public int getGenomesPerGeneration() {
		return genomesPerGeneration;
	}

	public void setGenomesPerGeneration(int genomesPerGeneration) {
		this.genomesPerGeneration = genomesPerGeneration;
	}

}
