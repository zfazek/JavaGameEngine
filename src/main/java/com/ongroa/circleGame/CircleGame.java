package com.ongroa.circleGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.ongroa.jge.Engine;
import com.ongroa.jge.GameInterface;

public class CircleGame implements GameInterface {

	private static final int width = 1024;
	private static final int height = 720;
	
	private Engine engine;

	ArrayList<Circle> circles;
	Random random;
	int n;
	
	public static void main(String[] args) throws IOException{
		GameInterface game = new CircleGame();
		Engine engine = new Engine(game, "Circle Game", width, height);
		game.setup(engine);
		engine.setFps(40);
		game.run(engine);
	}

	public void setup(Engine engine) {
		this.engine = engine;
		random = new Random();
		n = 300;
		engine.setBackgroundColor(Color.darkGray);
		circles = new ArrayList<Circle>();
		for (int i = 0; i < n; i++) {
			int size = random.nextInt(20) + 20;
			float x = random.nextFloat() * (width - 2 * size) + size;
			float y = random.nextFloat() * (height - 2 * size) + size;
			float dx = random.nextFloat() * 2 - 1;
			float dy = random.nextFloat() * 2 - 1;
			Color color;
			if ( i == 99) {
				color = Color.red;
			} else if (i == 98) {
				color = Color.yellow;
			} else if ( i == 97) {
				color = Color.green;
			} else if ( i == 96) {
				color = Color.blue;
			} else {
				int gray = random.nextInt(256);
				color = new Color(gray, gray, gray);
			}
			Circle circle = new Circle(x, y, dx, dy, size, color);
			circles.add(circle);
		}
	}   
	public void update(long elapsedTime) {
		for (Circle circle : circles) {
			circle.update(width, height, elapsedTime);
		}
	}

	public void draw(Graphics dbg) {
		drawFps(dbg);
		for (Circle circle : circles) {
			circle.draw(dbg);
		}
	}
	
	public void run(Engine engine) {
		while(true) {
			engine.clearBackground();
			engine.draw();
			update(engine.getElapsedTime());
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
}
