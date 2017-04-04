package com.ongroa.jge;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Keyboard {
	public List<Key> keys;
	public Key up;
	public Key down;
	public Key left;
	public Key right;
	public Key space;

	Keyboard() {
		addKeys();
	}

	public void releaseAll() {
		for (Key key : keys) {
			key.reset();
		}
	}

	public void toggle(KeyEvent e, boolean pressed) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up.toggle(pressed);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down.toggle(pressed);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left.toggle(pressed);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right.toggle(pressed);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			space.toggle(pressed);
		}
	}

	private void addKeys() {
		keys = new ArrayList<Key>();
		up = new Key();
		keys.add(up);
		down = new Key();
		keys.add(down);
		left = new Key();
		keys.add(left);
		right = new Key();
		keys.add(right);
		space = new Key();
		keys.add(space);
	}

}
