package gamePackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

public class MapSquare {
	
	private static final Color 
				FLOOR_COLOR = new Color(195, 195, 195),
				WALL_COLOR = new Color(239, 228, 176),
				COIN_COLOR = new Color(255, 255, 0);
				
	
	static enum SquareType {
		FLOOR, WALL, COIN
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
		case FLOOR:
			g.fillRect(
					position.x, position.y,
				thisSquareSize.width + 1, thisSquareSize.height + 1);
			break;
		}
	}

	private Color getColor() {
		switch (squareType) {
		case FLOOR:
		default:
			return FLOOR_COLOR;
		case WALL:
			return WALL_COLOR;
		case COIN:
			return COIN_COLOR;
		}
	}

	public boolean getWall() {
		switch (squareType) {
		case FLOOR:
		default:
				
			return false;
		case WALL:
			
			return true;
		}
	}

	public SquareType getType() {
		return squareType;
	}

	public void setFloor() {
		squareType = SquareType.FLOOR;
	}

	public boolean getCoin() {
		return squareType == SquareType.COIN;
	}
	
	
	
	
}
