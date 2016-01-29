package gamePackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

public class MapSquare {
	
	static final Color 
				FLOOR_COLOR = new Color(195, 195, 195),
				WALL_COLOR = new Color(239, 228, 176),
				COIN_COLOR = new Color(255, 255, 0),
				ENEMY_COLOR = new Color(237, 28, 36),
				ENEMY_COLOR_DOWN = new Color(255, 127, 39),
				ENEMY_WALL_COLOR = new Color(185, 122, 87);
				
	
	static enum SquareType {
		FLOOR, WALL, COIN, ENEMY, ENEMY_WALL
	}
	
	private SquareType squareType;
	
	public MapSquare(int rgb) {
		Color pixelColor = new Color(rgb);
		
		if (pixelColor.equals(WALL_COLOR)) {
			squareType = SquareType.WALL;
		}
		else if (pixelColor.equals(COIN_COLOR)) {
			squareType = SquareType.COIN;
		}
		else if (pixelColor.equals(ENEMY_COLOR)) {
			squareType = SquareType.ENEMY;
		}
		else if (pixelColor.equals(ENEMY_COLOR_DOWN)) {
			squareType = SquareType.ENEMY;
		}
		else if (pixelColor.equals(ENEMY_WALL_COLOR)) {
			squareType = SquareType.ENEMY_WALL;
		}
		else {
			squareType = SquareType.FLOOR;
		}
	}

	public static void paint(MapSquare[][] mapSquares, Graphics g, Dimension screenSize) {
		
		double squareX = (double)screenSize.width / GLOBAL.MAP_PIXEL_SIZE;
		double squareY = (double)screenSize.height / GLOBAL.MAP_PIXEL_SIZE;
		g.setColor(FLOOR_COLOR);
		g.fillRect(0, 0, screenSize.width, screenSize.height);
		
		for (int i = 0; i < GLOBAL.MAP_PIXEL_SIZE; i++) {
			for (int j = 0; j < GLOBAL.MAP_PIXEL_SIZE; j++) {
				mapSquares[i][j].paint(g, new Point((int)(squareX * i), (int)(squareY * j)), new Dimension((int)(squareX), (int)(squareY)));
			}
		}
	}

	private void paint(Graphics g, Point position, Dimension thisSquareSize) {
		g.setColor(getColor());
		switch (getType()) {
		case COIN:
			g.fillOval(
					position.x, position.y,
				thisSquareSize.width, thisSquareSize.height);
			break;
		case WALL:
		case ENEMY:
		case ENEMY_WALL:
			g.fillRect(
					position.x, position.y,
				thisSquareSize.width + 1, thisSquareSize.height + 1);
			break;
		case FLOOR:
		}
	}

	private Color getColor() {
		switch (squareType) {
		case FLOOR:
			return FLOOR_COLOR;
		case WALL:
			return WALL_COLOR;
		case COIN:
			return COIN_COLOR;
		case ENEMY:
			return ENEMY_COLOR;
		case ENEMY_WALL:
			return ENEMY_WALL_COLOR;
		default:
			return null;
		}
	}

	public boolean getWall() {
		switch (squareType) {
		case FLOOR:
		case COIN:
		case ENEMY:	
			return false;
			
		case ENEMY_WALL:
		case WALL:
		default:
			return true;
		}
	}
	
	public boolean getWallEnemy() {
		switch (squareType) {
		case FLOOR:
		case COIN:
		case ENEMY:	
		case ENEMY_WALL:
			return false;
		
		case WALL:
		default:
			return true;
		}
	}

	public SquareType getType() {
		return squareType;
	}

	public void setFloor() {
		squareType = SquareType.FLOOR;
	}

	public boolean isCoin() {
		return squareType == SquareType.COIN;
	}

	public boolean isEnemy() {
		return squareType == SquareType.ENEMY;
	}
}
