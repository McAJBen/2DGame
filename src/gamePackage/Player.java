package gamePackage;

import java.awt.Dimension;
import java.awt.Point;

public class Player {
	
	private Point 
				position,
				mapChangeTo;
	private Dimension
				bounds,
				playerSize;
	private boolean changedMap;
	
	public Player(Dimension bounds) {
		playerSize = new Dimension(10, 10);
		position = new Point(0, 0);
		mapChangeTo = new Point(0, 0);
		this.bounds = bounds;
	}
	
	public void move(int x, int y) {
		position.translate(x, y);
		
		// tell if bounds are changed;
		changedMap = checkBounds();
		
	}
	
	private boolean checkBounds() {
		boolean offBounds = false;
		if (position.x > bounds.width - playerSize.width) {
			position.x = 0;
			offBounds = true;
			mapChangeTo.x = 1;
		}
		else if (position.x < 0) {
			position.x = bounds.width - playerSize.width;
			offBounds = true;
			mapChangeTo.x = -1;
		}
		if (position.y > bounds.height - playerSize.height) {
			position.y = 0;
			offBounds = true;
			mapChangeTo.y = 1;
		}
		else if (position.y < 0) {
			position.y = bounds.height - playerSize.height;
			offBounds = true;
			mapChangeTo.y = -1;
		}
		return offBounds;
	}
	
	public boolean changedMap() {
		return changedMap;
	}
	
	public Point getPosition() {
		return position;
	}

	public void setBounds(Dimension size) {
		bounds = size;
	}

	public Point getMapChangeTo() {
		Point mct = (Point) mapChangeTo.clone();
		mapChangeTo = new Point(0, 0);
		return mct;
	}
}
