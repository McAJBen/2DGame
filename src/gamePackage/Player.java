package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

public class Player {
	
	private double x, y;
	
	private Point
				mapChangeTo;
	private Dimension
				playerSize;
	private boolean changedMap;
	
	public Player() {
		playerSize = new Dimension(10, 10);
		x = 0;
		y = 0;
		mapChangeTo = new Point(0, 0);
	}
	
	public void move(int dx, int dy) {
		x += dx * 0.005;
		y += dy * 0.005;
				
		changedMap = checkBounds();
	}
	
	private boolean checkBounds() {
		boolean offBounds = false;
		if (x > 1) {
			x = 0;
			offBounds = true;
			mapChangeTo.x = 1;
		}
		else if (x < 0) {
			x = 1;
			offBounds = true;
			mapChangeTo.x = -1;
		}
		if (y > 1) {
			y = 0;
			offBounds = true;
			mapChangeTo.y = 1;
		}
		else if (y < 0) {
			y = 1;
			offBounds = true;
			mapChangeTo.y = -1;
		}
		return offBounds;
	}
	
	public boolean changedMap() {
		return changedMap;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public Point getMapChangeTo() {
		Point mct = (Point) mapChangeTo.clone();
		mapChangeTo = new Point(0, 0);
		return mct;
	}

	public void paint(Graphics g, Dimension screenSize) {
		g.fillRect(
				(int)(x * (screenSize.width - playerSize.width)),
				(int)(y * (screenSize.height - playerSize.height)),
				playerSize.width, playerSize.height);
	}

	public Dimension getPlayerSize() {
		return playerSize;
	}
}
