package com.github.infosimulators.genetictrainer;

/**
 * Trainer class for finding optimal parameters.
 */
public class GeneticTrainer {
	
	public enum ParameterTypes {
		BOOLEAN, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE
	}
	
	private ParameterTypes[] format;
	private int geneCount;

	/**
	 * constructor
	 */
	public GeneticTrainer(ParameterTypes[] format) {
		setFormat(format);
	}

	/**
	 * Trains
	 */
	public void train() {
		// train parameters
	}
	
	public void setFormat(ParameterTypes[] format) {
		updateGeneCount(format);
		this.format = format;
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
	
	public ParameterTypes[] getFormat() {
		return format;
	}
	
	public int getGeneCount() {
		return geneCount;
	}

}
