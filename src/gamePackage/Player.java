package gamePackage;

import java.awt.Dimension;
import java.awt.Point;

public class Player {
	
	private Point position;
	private Dimension
				bounds,
				playerSize;
	
	public Player(Dimension bounds) {
		playerSize = new Dimension(10, 10);
		position = new Point(0, 0);
		this.bounds = bounds;
	}
	
	public void move(int x, int y) {
		position.translate(x, y);
		
		// tell if bounds are changed;
		checkBounds();
		
	}
	
	private boolean checkBounds() {
		boolean offBounds = false;
		if (position.x > bounds.width - playerSize.width) {
			position.x = bounds.width - playerSize.width;
			offBounds = true;
		}
		else if (position.x < 0) {
			position.x = 0;
			offBounds = true;
		}
		if (position.y > bounds.height - playerSize.height) {
			position.y = bounds.height - playerSize.height;
			offBounds = true;
		}
		else if (position.y < 0) {
			position.y = 0;
			offBounds = true;
		}
		return offBounds;
	}
	
	public Point getPosition() {
		return position;
	}

	public void setBounds(Dimension size) {
		bounds = size;
	}
}
