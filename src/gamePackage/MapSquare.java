package gamePackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import gamePackage.GLOBAL.SquareType;

public class MapSquare {
	
	private SquareType squareType;
	
	public MapSquare(int rgb) {
		Color c = new Color(rgb);
		if (c.equals(new Color(239, 228, 176))) {
			squareType = SquareType.WALL;
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
				g.drawRect(i * squareSize.width, j * squareSize.height,
						squareSize.width, squareSize.height);
				g.drawString(mapSquares[i][j].getChar(), i * squareSize.width, j * squareSize.height + 10);
			}
		}
	}

	private String getChar() {
		switch (squareType) {
		case FLOOR:
			return "F";
		case WALL:
			return "W";
		default:
			return "";		
		}
	}

	public boolean getWall() {
		return squareType == SquareType.WALL;
	}
	
	
	
	
}
