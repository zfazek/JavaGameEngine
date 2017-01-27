package com.ongroa.jge;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Engine extends JComponent {
	private static final long serialVersionUID = 1L;
	private GameInterface game;
	private Graphics dbg;
	private Image dbImage = null;
	private Color backgroundColor;
	private int width;
	private int height;
	private long time;
	private long elapsedTime;
	private long sleepTimeInNanos;
	private long fpsTime;
	private int desiredFps;
	private int fps;
	private int fpsCount;

	Engine() {
	}

	public Engine(final GameInterface game, String title, int width, int height) {
		this.game = game;
		this.width = width;
		this.height = height;
		desiredFps = 30;
		fps = 0;
		fpsCount = 0;
		sleepTimeInNanos = 0;
		time = System.nanoTime();
		fpsTime = System.nanoTime();

		JFrame mainFrame = new JFrame(title);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().add(this);
		mainFrame.pack();
		mainFrame.setVisible(true);
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				game.mousePressed(e);
			}
		});
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getFps() {
		return fps;
	}
	
	public void setFps(int fps) {
		this.desiredFps = fps;
	}
	
	private void gameRenderer() {
		if (dbImage == null) {
			dbImage = createImage(width, height);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			}
		} else {
			dbg = dbImage.getGraphics();
		}

		if (dbg != null) {
			game.draw(dbg);
		}
	}

	private void paintScreen() {
		Graphics g;
		g = getGraphics();
		if (g != null && dbImage != null) {
			g.drawImage(dbImage, 0, 0, null);
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
	}

	private void sleep(long sleepTimeInMillis) {
		try {
			Thread.sleep(sleepTimeInMillis);
		} catch (InterruptedException e) {
		}
	}
	private void calculateFps(long time) {
		long timeDiff = time - fpsTime;
		fpsCount++;
		if (timeDiff > 1000000000) {
			fps = fpsCount;
			fpsCount = 0;
			fpsTime = time;
		}
	}

	public void draw() {
		gameRenderer();
		paintScreen();
		long now = System.nanoTime();
		calculateFps(now);
		elapsedTime = now - time + sleepTimeInNanos;
		sleepTimeInNanos = 1000000000L / desiredFps - (elapsedTime - sleepTimeInNanos);
//		System.out.println(elapsedTime + " " + sleepTimeInNanos + " " + fps);
		if (sleepTimeInNanos < 1000000) {
			sleepTimeInNanos = 1000000;
		}
		sleep(sleepTimeInNanos / 1000000);
		time = System.nanoTime();
	}

	public void clearBackground() {
		if (dbg != null) {
			if (backgroundColor != null) {
				dbg.setColor(backgroundColor);
			}
			dbg.fillRect(0, 0, width, height);
		}
	}

	public long getElapsedTime() {
		return elapsedTime / 1000000;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (dbImage != null) {
			g.drawImage(dbImage, 0, 0, null);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

}