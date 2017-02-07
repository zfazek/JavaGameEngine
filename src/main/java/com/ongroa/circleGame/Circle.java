package com.ongroa.circleGame;

import java.awt.Color;
import java.awt.Graphics;

public class Circle {
	int id;
	float x;
	float y;
	float dx;
	float dy;
	float ax;
	float ay;
	float size;
	float speed = 0.1f;
	Color color;

	public Circle(int id, float x, float y, float dx, float dy, float ax, float ay, float size, Color color) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.size = size;
		this.color = color;
		this.ax = ax;
		this.ay = ay;
	}

	public void update(float width, float height, long elapsedTime) {
		x += dx * elapsedTime * speed;
		y += dy * elapsedTime * speed;
		dx += ax * elapsedTime * speed;
		dy += ay * elapsedTime * speed;
		if (x <= 0) {
			dx = Math.abs(dx);
		} else if (x >= width - size) {
			dx = -Math.abs(dx);
		}
		if (y <= 0 && id != 0) {
			dy = Math.abs(dy);
		} else if (y > height - size) {
			if (id == 0) {
				y = height - size;
				dy = -Math.abs(dy) * 0.6f;
			} else {
				dy = -Math.abs(dy);
			}
		}
	}

	public void draw(Graphics dbg) {
		dbg.setColor(color);
		dbg.fillOval((int)x, (int)y, (int)size, (int)size);
	}

}
