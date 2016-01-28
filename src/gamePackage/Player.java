package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

public class Player {
	
	private double x, y;
	private Point mapChangeTo;
	private boolean changedMap;
	
	public Player() {
		x = 0;
		y = 0;
		mapChangeTo = new Point(0, 0);
	}
	
	public void move(int dx, int dy, Map map, Dimension screenSize) {
		
		double
			nx = x + dx * GLOBAL.PLAYER_STEP,
			ny = y + dy * GLOBAL.PLAYER_STEP;
		
		checkWall(map.getMapSquares(), nx, ny, dx, dy);
		
		changedMap = checkBounds();
	}
	
	private void checkWall(MapSquare[][] mapSquares, double nx, double ny, int dx, int dy) {
		if (0 > nx || nx + GLOBAL.PLAYER_SIZE >= GLOBAL.MAP_PIXEL_SIZE || 0 > ny || ny + GLOBAL.PLAYER_SIZE >= GLOBAL.MAP_PIXEL_SIZE) {
			if (0 > nx || nx + GLOBAL.PLAYER_SIZE >= GLOBAL.MAP_PIXEL_SIZE) {
				x = nx;
			}
			else if (0 > ny || ny + GLOBAL.PLAYER_SIZE >= GLOBAL.MAP_PIXEL_SIZE) {
				y = ny;
			}
		}
		else if (dx < 0) {
			if (dy < 0) {
				if(!(mapSquares[(int) (nx)][(int) (ny)].getWall() ||
					mapSquares[(int) (nx)][(int) (ny + GLOBAL.PLAYER_SIZE)].getWall() ||
					mapSquares[(int) (nx + GLOBAL.PLAYER_SIZE)][(int) (ny)].getWall())) {
						x = nx;
						y = ny;
				}
			}
			else if (dy > 0) {
				if (!(mapSquares[(int) (nx)][(int) (ny)].getWall() ||
					mapSquares[(int) (nx)][(int) (ny + GLOBAL.PLAYER_SIZE)].getWall() ||
					mapSquares[(int) (nx + GLOBAL.PLAYER_SIZE)][(int) (ny + GLOBAL.PLAYER_SIZE)].getWall())) {
						x = nx;
						y = ny;
				}
			}
			else {
				if (!(mapSquares[(int) (nx)][(int) (ny)].getWall() ||
					mapSquares[(int) (nx)][(int) (ny + GLOBAL.PLAYER_SIZE)].getWall())) {
						x = nx;
						y = ny;
				}
			}
		}
		else if (dx > 0) {
			if (dy < 0) {
				if (!( mapSquares[(int) (nx)][(int) (ny)].getWall() ||
					mapSquares[(int) (nx + GLOBAL.PLAYER_SIZE)][(int) (ny)].getWall() ||
					mapSquares[(int) (nx + GLOBAL.PLAYER_SIZE)][(int) (ny + GLOBAL.PLAYER_SIZE)].getWall())) {
						x = nx;
						y = ny;
				}
			}
			else if (dy > 0) {
				if (!(mapSquares[(int) (nx)][(int) (ny + GLOBAL.PLAYER_SIZE)].getWall() ||
					mapSquares[(int) (nx + GLOBAL.PLAYER_SIZE)][(int) (ny)].getWall() ||
					mapSquares[(int) (nx + GLOBAL.PLAYER_SIZE)][(int) (ny + GLOBAL.PLAYER_SIZE)].getWall())) {
						x = nx;
						y = ny;
				}
			}
			else {
				if (!(mapSquares[(int) (nx + GLOBAL.PLAYER_SIZE)][(int) (ny)].getWall() ||
					mapSquares[(int) (nx + GLOBAL.PLAYER_SIZE)][(int) (ny + GLOBAL.PLAYER_SIZE)].getWall())) {
						x = nx;
						y = ny;
				}
			}
		}
		else {
			if (dy < 0) {
				if (!(mapSquares[(int) (nx)][(int) (ny)].getWall() ||
					mapSquares[(int) (nx + GLOBAL.PLAYER_SIZE)][(int) (ny)].getWall())) {
						x = nx;
						y = ny;
				}
			}
			else if (dy > 0) {
				if (!(mapSquares[(int) (nx)][(int) (ny + GLOBAL.PLAYER_SIZE)].getWall() ||
					mapSquares[(int) (nx + GLOBAL.PLAYER_SIZE)][(int) (ny + GLOBAL.PLAYER_SIZE)].getWall())) {
						x = nx;
						y = ny;
				}
			}
		}
	}

	/*private void getWallX(MapSquare[][] mapSquares, int dx) {
		if (dx < 0) {
			if (x < 0) {
				changedMap = true;
				mapChangeTo.x = -1;
			}
			else if (mapSquares[(int) x][(int) y].getWall() ||
					mapSquares[(int) x][(int) (y + GLOBAL.PLAYER_SIZE)].getWall()) {
				x = (int)x + 1;
			}
		}
		else if (dx > 0) {
			if (x >= GLOBAL.MAP_PIXEL_SIZE - GLOBAL.PLAYER_SIZE) {
				changedMap = true;
				mapChangeTo.x = 1;
			}
			else if (mapSquares[(int) (x + GLOBAL.PLAYER_SIZE)][(int) y].getWall() ||
					mapSquares[(int) (x + GLOBAL.PLAYER_SIZE)][(int) (y + GLOBAL.PLAYER_SIZE)].getWall()) {
				x = (int)x - GLOBAL.PLAYER_SIZE + 1;
			}
		}
	}
	
	private void getWallY(MapSquare[][] mapSquares, int dy) {
		if (dy < 0) {
			if (y < 0) {
				changedMap = true;
				mapChangeTo.y = -1;
			}
			else if (mapSquares[(int) x][(int) y].getWall() ||
					mapSquares[(int) (x + GLOBAL.PLAYER_SIZE)][(int) y].getWall()) {
				y = (int)y + 1;
			}
		}
		else if (dy > 0) {
			if (y >= GLOBAL.MAP_PIXEL_SIZE - GLOBAL.PLAYER_SIZE) {
				changedMap = true;
				mapChangeTo.y = 1;
			}
			else if (mapSquares[(int) x][(int) (y + GLOBAL.PLAYER_SIZE)].getWall() ||
					mapSquares[(int) (x + GLOBAL.PLAYER_SIZE)][(int) (y + GLOBAL.PLAYER_SIZE)].getWall()) {
				y = (int)y - GLOBAL.PLAYER_SIZE + 1;
			}
		}
		
	}*/

	private boolean checkBounds() {
		boolean offBounds = false;
		if (x + GLOBAL.PLAYER_SIZE > GLOBAL.MAP_PIXEL_SIZE) {
			offBounds = true;
			mapChangeTo.x = 1;
		}
		else if (x < 0) {
			offBounds = true;
			mapChangeTo.x = -1;
		}
		if (y + GLOBAL.PLAYER_SIZE > GLOBAL.MAP_PIXEL_SIZE) {
			offBounds = true;
			mapChangeTo.y = 1;
		}
		else if (y < 0) {
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
				(int)(x / GLOBAL.MAP_PIXEL_SIZE * screenSize.width),
				(int)(y / GLOBAL.MAP_PIXEL_SIZE * screenSize.height),
				(int)(GLOBAL.PLAYER_SIZE * screenSize.width / GLOBAL.MAP_PIXEL_SIZE),
				(int)(GLOBAL.PLAYER_SIZE * screenSize.height / GLOBAL.MAP_PIXEL_SIZE));
		
		g.drawString(x + "",
				(int)(x / GLOBAL.MAP_PIXEL_SIZE * screenSize.width),
				(int)(y / GLOBAL.MAP_PIXEL_SIZE * screenSize.height) - 12);
		
		g.drawString(y + "",
				(int)(x / GLOBAL.MAP_PIXEL_SIZE * screenSize.width),
				(int)(y / GLOBAL.MAP_PIXEL_SIZE * screenSize.height) - 2);
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
