package com.ongroa.circleGame;

import java.awt.Color;
import java.awt.Graphics;

public class Circle {
	float x;
	float y;
	float dx;
	float dy;
	float size;
	float speed = 0.1f;
	Color color;
	
	public Circle(float x, float y, float dx, float dy, float size, Color color) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.size = size;
		this.color = color;
	}
	
	public void update(float width, float height, long elapsedTime) {
		x += dx * elapsedTime * speed;
		y += dy * elapsedTime * speed;
		if (x < 0) {
			dx = Math.abs(dx);
		} else if (x > width - size) {
			dx = -Math.abs(dx);
		}
		if (y < 0) {
			dy = Math.abs(dy);
		} else if (y > height - size) {
			dy = -Math.abs(dy);
		}
	}
	
	public void draw(Graphics dbg) {
		dbg.setColor(color);
		dbg.fillOval((int)x, (int)y, (int)size, (int)size);
	}

}
