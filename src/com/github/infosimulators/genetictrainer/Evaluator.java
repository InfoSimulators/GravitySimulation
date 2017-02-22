package com.github.infosimulators.genetictrainer;

import com.github.infosimulators.genetictrainer.ParameterTypes;

/**
 * Evaluator class
 */
public abstract class Evaluator {

	private ParameterTypes[] format;

	public Evaluator(ParameterTypes[] format) {
		setFormat(format);
	}

	private void setFormat(ParameterTypes[] format) {
		this.format = format;
	}

	public ParameterTypes[] getFormat() {
		return format;
	}

}
