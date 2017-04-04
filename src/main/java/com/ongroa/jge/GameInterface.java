package com.ongroa.jge;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public interface GameInterface {

	void setup(Engine engine);

	void draw(Graphics dbg);

	void run(Engine engine);

	void mousePressed(MouseEvent e);

	void keyPressed();
}
