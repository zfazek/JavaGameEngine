package com.ongroa.circleGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ongroa.jge.Engine;
import com.ongroa.jge.GameInterface;
import com.ongroa.jge.Key;

public class CircleGame implements GameInterface {

	private static final int width = 1024;
	private static final int height = 720;

	private Engine engine;

	ArrayList<Circle> circles;
	Rect rect;
	Random random;
	int n;

	public static void main(String[] args) throws IOException{
		GameInterface game = new CircleGame();
		Engine engine = new Engine(game, "Circle Game", width, height);
		game.setup(engine);
		engine.setFps(30);
		game.run(engine);
	}

	public void setup(Engine engine) {
		float x, y, dx, dy, ax, ay;
		this.engine = engine;
		random = new Random();
		n = 300;
		engine.setBackgroundColor(Color.darkGray);
		circles = new ArrayList<Circle>();
		for (int i = 0; i < n; i++) {
			int size = random.nextInt(20) + 20;
			if (i == 0) {
				x = width / 2;
				y = 10;
				dx = 0;
				dy = 0.1f;
				ax = 0;
				ay = 0.1f;
			} else {
				x = random.nextFloat() * (width - 2 * size) + size;
				y = random.nextFloat() * (height - 2 * size) + size;
				dx = random.nextFloat() * 2 - 1;
				dy = random.nextFloat() * 2 - 1;
				ax = 0;
				ay = 0;
			}
			Color color;
			if ( i == 0) {
				color = Color.red;
			} else if (i == 1) {
				color = Color.yellow;
			} else if ( i == 2) {
				color = Color.green;
			} else if ( i == 3) {
				color = Color.blue;
			} else {
				int gray = random.nextInt(256);
				color = new Color(gray, gray, gray);
			}
			Circle circle = new Circle(i, x, y, dx, dy, ax, ay, size, color);
			circles.add(circle);
		}
		rect = new Rect(0f, 0f, 0.0f, 0.0f, 50f, Color.cyan);
	}
	public void update(long elapsedTime) {
		for (Circle circle : circles) {
			circle.update(width, height, elapsedTime);
		}
		rect.update(width, height, elapsedTime);
	}

	public void draw(Graphics dbg) {
		rect.draw(dbg);
		dbg.setColor(Color.magenta);
		int size = 20;
		int x = MouseInfo.getPointerInfo().getLocation().x - engine.getLocationOnScreen().x - size / 2;
		int y = MouseInfo.getPointerInfo().getLocation().y - engine.getLocationOnScreen().y - size / 2;
		dbg.fillOval(x, y, size, size);
		drawFps(dbg);
		for (Circle circle : circles) {
			circle.draw(dbg);
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
		Font font = new Font("Dialog", Font.PLAIN, 12);
		dbg.setFont(font);
		dbg.setColor(Color.white);
		String fps = String.format("FPS:%3d", engine.getFps());
		dbg.drawString(fps, 0, 10);
	}

	public void mousePressed(MouseEvent e) {
		System.out.format("Mouse pressed at (%d, %d)\n" , e.getX(), e.getY());
	}

	public void keyPressed(List<Key> keys) {
		updateRect(keys);
	}

	private void updateRect(List<Key> keys) {
		float speed = 1.0f * engine.getElapsedTimeInMillis();
		if (keys.get(0).down) {
			if (rect.y > speed) {
				rect.y -= speed;
			} else {
				rect.y = 0;
			}
		}
		if (keys.get(1).down) {
			if (rect.y < height - rect.size - speed) {
				rect.y += speed;
			} else {
				rect.y = height - rect.size;
			}
		}
		if (keys.get(2).down) {
			if (rect.x > speed) {
				rect.x -= speed;
			} else {
				rect.x = 0;
			}
		}
		if (keys.get(3).down) {
			if (rect.x < width - rect.size - speed) {
				rect.x += speed;
			} else {
				rect.x = width - rect.size;
			}
		}
		if (keys.get(4).down) {
			if (circles.size() > 0) {
				circles.get(0).dy -= 1;
				circles.get(0).ay = 0.1f;
			}
		}
	}
}

