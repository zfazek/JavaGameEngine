package com.ongroa.rectGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Rect {
	int id;
	float x;
	float y;
	float dx;
	float dy;
	float ax;
	float ay;
	float size;
	float speed;
	Color color;

	public Rect(int id, float x, float y, float dx, float dy,
			float ax, float ay, float size, Color color) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.size = size;
		this.color = color;
		this.ax = ax;
		this.ay = ay;
		speed = 0.1f;
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
		if (y <= 0) {
			dy = Math.abs(dy);
		} else if (y > height - size) {
			dy = -Math.abs(dy);
		}
	}

	public void draw(Graphics dbg) {
		Graphics2D g2d = (Graphics2D)dbg;
		g2d.setColor(Color.BLACK);
		BasicStroke stroke = new BasicStroke(4);
		g2d.setStroke(stroke);
		g2d.drawRect((int)x, (int)y, (int)size, (int)size);
		g2d.setColor(color);
		g2d.fillRect((int)x, (int)y, (int)size, (int)size);
	}

	public boolean isInside(int mouseX, int mouseY) {
		if (mouseX > x && mouseX < x + size &&
				mouseY > y && mouseY < y + size) {
			return true;
		} else {
			return false;
		}
	}

}
