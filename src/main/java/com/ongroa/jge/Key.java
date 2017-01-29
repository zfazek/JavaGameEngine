package com.ongroa.jge;

public class Key {
	public boolean down;

	Key() {
	}

	void toggle(boolean pressed) {
		if (pressed != down) {
			down = pressed;
		}
	}
}
