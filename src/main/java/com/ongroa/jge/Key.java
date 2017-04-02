package com.ongroa.jge;

public class Key {
	private boolean down;

	Key() {
	}

	public void toggle(boolean pressed) {
		if (pressed != down) {
			down = pressed;
		}
	}

	public void reset() {
		down = false;
	}

	public boolean isDown() {
		return down;
	}
}
