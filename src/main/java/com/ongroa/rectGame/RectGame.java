package com.ongroa.rectGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.ongroa.jge.Engine;
import com.ongroa.jge.GameInterface;
import com.ongroa.jge.Keyboard;

public class RectGame implements GameInterface {
	private static final int width = 1600;
	private static final int height = 800;
	private Engine engine;
	private Keyboard keyboard;
	ArrayList<Rect> rects;
	Random random;
	int n;
	int click;

	public static void main(String[] args) {
		GameInterface game = new RectGame();
		Engine engine = new Engine(game, "Rect Game", width, height);
		game.setup(engine);
		engine.setFps(30);
		game.run(engine);
	}

	public void setup(Engine engine) {
		this.engine = engine;
		keyboard = engine.getKeyboard();
		engine.setBackgroundColor(Color.BLUE);
		rects = new ArrayList<Rect>();
		random = new Random();
		n = 100;
		init();
	}

	public void update(long elapsedTime) {
		synchronized (rects) {
			for (Rect rect : rects) {
				rect.update(width, height, elapsedTime);
			}
		}
	}

	public void draw(Graphics dbg) {
		if (rects.size() == 0) {
			drawEnd(dbg);
		} else {
			synchronized (rects) {
				for (Rect rect : rects) {
					rect.draw(dbg);
				}
			}
			drawFps(dbg);
			drawStats(dbg);
		}
	}

	public void run(Engine engine) {
		while(true) {
			engine.clearBackground();
			engine.draw();
			update(engine.getElapsedTimeInMillis());
		}
	}

	public void mousePressed(MouseEvent e) {
		if (rects.size() == 0) {
			init();
		} else {
			Point mouseCoordinate = engine.getMouseCoordinate();
			int counter = 0;
			click++;
			synchronized (rects) {
				Iterator<Rect> it = rects.iterator();
				while (it.hasNext()) {
					Rect rect = it.next();
					if (rect.isInside(mouseCoordinate.x, mouseCoordinate.y)) {
						it.remove();
						counter++;
					}
				}
				if (counter == 0) {
					addRects(4);
				}
			}
		}
	}

	public void keyPressed() {
		if (keyboard.space.isDown()) {
			init();
		}
	}

	private void init() {
		click = 0;
		rects.clear();
		addRects(n);
	}

	private void addRects(int n) {
		float x, y, dx, dy, ax, ay;
		float maxSpeed = 1.5f;
		for (int i = 0; i < n; i++) {
			int size = random.nextInt(50) + 150;
			x = random.nextFloat() * (width - size);
			y = random.nextFloat() * (height - size);
			dx = 2 * random.nextFloat() * maxSpeed - maxSpeed;
			dy = 2 * random.nextFloat() * maxSpeed - maxSpeed;
			ax = 0;
			ay = 0;
			Color color;
			int red = random.nextInt(256);
			int green = random.nextInt(256);
			int blue = random.nextInt(256);
			color = new Color(red, green, blue);
			Rect rect = new Rect(i, x, y, dx, dy, ax, ay, size, color);
			rects.add(rect);
		}
	}

	private void drawFps(Graphics dbg) {
		String fps = String.format("FPS:%3d", engine.getFps());
		engine.drawText(fps, 0, 20, Color.WHITE, 20);
	}

	private void drawStats(Graphics dbg) {
		String stat = String.format("%3d", rects.size());
		engine.drawText(stat, 0, 50, Color.GREEN, 20);
	}

	private void drawEnd(Graphics dbg) {
		String str = String.format("You clicked %d.", click);
		engine.drawText(str, 0, 80, Color.GREEN, 20);
	}

}