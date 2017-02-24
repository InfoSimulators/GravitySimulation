package com.github.infosimulators;

import java.util.List;

import com.github.infosimulators.events.Event;
import com.github.infosimulators.genetictrainer.Evaluator;

public class ExampleEvaluator extends Evaluator {

	@Override
	protected float eval(List<Event> events) {
		return events.size();
	}

	@Override
	public boolean isCostFunction() {
		return true;
	}

}
