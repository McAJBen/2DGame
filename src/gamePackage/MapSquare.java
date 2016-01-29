package gamePackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

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
		
		
		
		Dimension squareSize = new Dimension(
				screenSize.width / GLOBAL.MAP_PIXEL_SIZE,
				screenSize.height / GLOBAL.MAP_PIXEL_SIZE);
		
		for (int i = 0; i < GLOBAL.MAP_PIXEL_SIZE; i++) {
			for (int j = 0; j < GLOBAL.MAP_PIXEL_SIZE; j++) {
				
				g.setColor(mapSquares[i][j].getColor());
				g.fillRect(
						i * squareSize.width, j * squareSize.height,
						squareSize.width, squareSize.height);
				g.drawString(
						mapSquares[i][j].getChar(),
						i * squareSize.width,
						j * squareSize.height + 10);
			}
		}
	}

	private Color getColor() {
		switch (squareType) {
		case FLOOR:
		case COIN:
		default:
			return FLOOR_COLOR;
		case WALL:
			return WALL_COLOR;
		}
	}

	private String getChar() {
		switch (squareType) {
		case FLOOR:
			return "F";
		case WALL:
			return "W";
		case COIN:
			return "C";
		default:
			return "";		
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
	
	
	
	
}
