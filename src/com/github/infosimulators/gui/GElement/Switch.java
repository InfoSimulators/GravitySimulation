package com.github.infosimulators.gui.GElement;

import processing.core.PApplet;

/**
 * GElement to change between two states and run one of its two methods depending on the state.
 */
public class Switch extends GElement {

	private boolean on;
	private int timer;
	private Runnable r1, r2;

	public Switch(boolean on, Runnable r1, Runnable r2, float x, float y, float xSize, float ySize) {
		super(x, y, xSize, ySize);
		this.on = on;
		this.r1 = r1;
		this.r2 = r2;
		timer = 0;
	}

	@Override
	public void update(PApplet p) {

		/*
		 * Checks if Switch is clicked If clicked it changes the state of the
		 * switch and sets a timer for when it can next be activated
		 */
		if (p.mousePressed && timer == 0 && p.mouseX > x && p.mouseY > y && p.mouseX < x + xSize
				&& p.mouseY < y + ySize) {
			on = !on;
			timer = 30;
		}

		// Runs the switches actions
		if (on) {
			r1.run();
		} else {
			r2.run();
		}

		// Draws the switch using 2 rectangles
		p.stroke(255);
		if (on) {
			p.fill(255);
		} else {
			p.noFill();
		}
		p.rect(x, y, xSize / 2, ySize);
		if (!on) {
			p.fill(255);
		} else {
			p.noFill();
		}
		p.rect(x + xSize / 2, y, xSize / 2, ySize);

		/*
		 * Lowers the timer each frame after the switch was changed Once it is
		 * back to zero the switch can be swapped again
		 */
		if (timer > 0) {
			timer--;
		}
	}
}
