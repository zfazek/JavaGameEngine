package com.ongroa.rectGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.ongroa.jge.Engine;
import com.ongroa.jge.GameInterface;
import com.ongroa.jge.Key;

public class RectGame implements GameInterface {
	private static final int width = 1800;
	private static final int height = 900;

	private Engine engine;

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
		engine.setBackgroundColor(Color.blue);
		rects = new ArrayList<Rect>();
		random = new Random();
		n = 100;
		init();
	}

	public void init() {
		click = 0;
		rects.clear();
		addRects(n);
	}

	public void addRects(int n) {
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

	public void update(long elapsedTime) {
		for (Rect rect : rects) {
			rect.update(width, height, elapsedTime);
		}
	}

	public void draw(Graphics dbg) {
		drawFps(dbg);
		drawStats(dbg);
		synchronized (rects) {
			for (Rect rect : rects) {
				rect.draw(dbg);
			}
		}
		if (rects.size() == 0) {
			drawEnd(dbg);
		}
	}

	public void run(Engine engine) {
		while(true) {
			engine.clearBackground();
			engine.draw();
			update(engine.getElapsedTimeInMillis());
		}
	}

	public void drawFps(Graphics dbg) {
		Font font = new Font("Dialog", Font.PLAIN, 20);
		dbg.setFont(font);
		dbg.setColor(Color.white);
		String fps = String.format("FPS:%3d", engine.getFps());
		dbg.drawString(fps, 0, 20);
	}

	public void drawStats(Graphics dbg) {
		Font font = new Font("Dialog", Font.PLAIN, 20);
		dbg.setFont(font);
		dbg.setColor(Color.green);
		String stat = String.format("%3d", rects.size());
		dbg.drawString(stat, 0, 50);
	}

	public void drawEnd(Graphics dbg) {
		Font font = new Font("Dialog", Font.PLAIN, 20);
		dbg.setFont(font);
		dbg.setColor(Color.green);
		String str = String.format("You clicked %d.", click);
		dbg.drawString(str, 0, 80);
	}

	public void mousePressed(MouseEvent e) {
		if (rects.size() == 0) {
			init();
		} else {
			int mouseX = MouseInfo.getPointerInfo().getLocation().x - engine.getLocationOnScreen().x;
			int mouseY = MouseInfo.getPointerInfo().getLocation().y - engine.getLocationOnScreen().y;
			int counter = 0;
			click++;
			synchronized (rects) {
				Iterator<Rect> it = rects.iterator();
				while (it.hasNext()) {
					Rect rect = it.next();
					if (rect.isInside(mouseX, mouseY)) {
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

	public void keyPressed(List<Key> keys) {
		if (keys.get(4).isDown()) {
			init();
		}
	}

}