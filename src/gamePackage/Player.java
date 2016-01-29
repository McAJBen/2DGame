package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

public class Player {
	
	private double x, y;
	private Point mapChangeTo;
	
	public Player() {
		x = GLOBAL.PLAYER_ORIGINAL_X;
		y = GLOBAL.PLAYER_ORIGINAL_Y;
		mapChangeTo = new Point(0, 0);
	}
	
	public boolean move(int dx, int dy, Map map, Dimension screenSize) {
		
		checkWall(map.getMapSquares(), dx, dy);
		
		return checkBounds();
	}
	
	private void checkWall(MapSquare[][] mapSquares, int dx, int dy) {
		double nx = x + dx * GLOBAL.PLAYER_STEP;
		double ny = y + dy * GLOBAL.PLAYER_STEP;
		if (nx < 0) {
			mapChangeTo.x = -1;
			x = nx;
			return;
		}
		if (GLOBAL.PLAYER_MAX_PIXEL <= nx) {
			mapChangeTo.x = 1;
			x = nx;
			return;
		}
		if (ny < 0) {
			mapChangeTo.y = -1;
			y = ny;
			return;
		}
		if (GLOBAL.PLAYER_MAX_PIXEL <= ny) {
			mapChangeTo.y = 1;
			y = ny;
			return;
		}
		if (dx < 0 || dy < 0) {
			if (mapSquares[(int) (nx)][(int) (ny)].getWall()) {
				if (mapSquares[(int) (x)][(int) (ny)].getWall()) {
					ny = y;
				}
				if (mapSquares[(int) (nx)][(int) (y)].getWall()) {
					nx = x;
				}
			}
		}
		if (dx < 0 || dy > 0) {
			if (mapSquares[(int) (nx)][(int) (ny + GLOBAL.PLAYER_SIZE)].getWall()) {
				if (mapSquares[(int) (x)][(int) (ny + GLOBAL.PLAYER_SIZE)].getWall()) {
					ny = y;
				}
				if (mapSquares[(int) (nx)][(int) (y + GLOBAL.PLAYER_SIZE)].getWall()) {
					nx = x;
				}
			}
		}
		if (dx > 0 || dy < 0) {
			if (mapSquares[(int) (nx + GLOBAL.PLAYER_SIZE)][(int) (ny)].getWall()) {
				if (mapSquares[(int) (x + GLOBAL.PLAYER_SIZE)][(int) (ny)].getWall()) {
					ny = y;
				}
				if (mapSquares[(int) (nx + GLOBAL.PLAYER_SIZE)][(int) (y)].getWall()) {
					nx = x;
				}
			}
		}
		if (dx > 0 || dy > 0) {
			if (mapSquares[(int) (nx + GLOBAL.PLAYER_SIZE)][(int) (ny + GLOBAL.PLAYER_SIZE)].getWall()) {
				if (mapSquares[(int) (x + GLOBAL.PLAYER_SIZE)][(int) (ny + GLOBAL.PLAYER_SIZE)].getWall()) {
					ny = y;
				}
				if (mapSquares[(int) (nx + GLOBAL.PLAYER_SIZE)][(int) (y + GLOBAL.PLAYER_SIZE)].getWall()) {
					nx = x;
				}
			}
		}
		x = nx;
		y = ny;
	}
	
	private boolean checkBounds() {
		boolean offBounds = false;
		if (x > GLOBAL.PLAYER_MAX_PIXEL) {
			offBounds = true;
			mapChangeTo.x = 1;
		}
		else if (x < 0) {
			offBounds = true;
			mapChangeTo.x = -1;
		}
		if (y > GLOBAL.PLAYER_MAX_PIXEL) {
			offBounds = true;
			mapChangeTo.y = 1;
		}
		else if (y < 0) {
			offBounds = true;
			mapChangeTo.y = -1;
		}
		return offBounds;
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
				(int)(x / GLOBAL.MAP_PIXEL_SIZE * screenSize.width),
				(int)(y / GLOBAL.MAP_PIXEL_SIZE * screenSize.height),
				(int)(GLOBAL.PLAYER_SIZE * screenSize.width / GLOBAL.MAP_PIXEL_SIZE),
				(int)(GLOBAL.PLAYER_SIZE * screenSize.height / GLOBAL.MAP_PIXEL_SIZE));
		
		/*g.drawString(x + "",
				(int)(x / GLOBAL.MAP_PIXEL_SIZE * screenSize.width),
				(int)(y / GLOBAL.MAP_PIXEL_SIZE * screenSize.height) - 12);
		
		g.drawString(y + "",
				(int)(x / GLOBAL.MAP_PIXEL_SIZE * screenSize.width),
				(int)(y / GLOBAL.MAP_PIXEL_SIZE * screenSize.height) - 2);*/
	}

	public void changeMap(boolean isChanging) {
		if (isChanging) {
			if (x > GLOBAL.PLAYER_MAX_PIXEL) {
				x = 0;
			}
			else if (x < 0) {
				x = GLOBAL.PLAYER_MAX_PIXEL;
			}
			if (y > GLOBAL.PLAYER_MAX_PIXEL) {
				y = 0;
			}
			else if (y < 0) {
				y = GLOBAL.PLAYER_MAX_PIXEL;
			}
		}
		else {
			if (x > GLOBAL.PLAYER_MAX_PIXEL) {
				x = GLOBAL.PLAYER_MAX_PIXEL;
			}
			else if (x < 0) {
				x = 0;
			}
			if (y > GLOBAL.PLAYER_MAX_PIXEL) {
				y = GLOBAL.PLAYER_MAX_PIXEL;
			}
			else if (y < 0) {
				y = 0;
			}
		}
		
	}
	
}
