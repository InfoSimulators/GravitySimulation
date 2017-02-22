package com.github.infosimulators.gui.GElement;

import processing.core.PApplet;

//TODO: Button reactivation not by timer, but activation if mouse released.

/*
 * GElement running its method once when pressed.
 */

public class Button extends GElement {

	protected Runnable r;
	private String title;
	private int timer;

	public Button(Runnable r, String title, float x, float y, float xSize, float ySize) {
		super(x, y, xSize, ySize);
		this.r = r;
		this.title = title;
		timer = 0;
	}

	@Override
	public void update(PApplet p) {
		// Checks if mouse is over Button and acts accordingly.
		if (p.mouseX > x && p.mouseY > y && p.mouseX < x + xSize && p.mouseY < y + ySize) {
			p.fill(255, 0, 0);
			if (p.mousePressed && timer == 0) {
				p.fill(200, 0, 0);
				r.run();
				timer = 30;
			}
		} else {
			p.fill(255);
		}

		p.stroke(200);
		p.rect(x, y, xSize, ySize);

		p.fill(255, 0, 0);
		p.textSize(ySize - 20);
		p.text(title, x + 10, y + 10, x + xSize - 10, y + ySize - 10);

		if (timer > 0) {
			timer--;
		}
	}
}