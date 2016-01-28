package gamePackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import gamePackage.GLOBAL.SquareType;

public class MapSquare {
	
	private SquareType squareType;
	
	public MapSquare(int rgb) {
		Color c = new Color(rgb);
		if (c.getRed() == 239) {
			squareType = SquareType.WALL;
		}
		else {
			squareType = SquareType.FLOOR;
		}
	}

	public static void paint(MapSquare[][] mapSquares, Graphics g, Dimension screenSize) {
		
		Dimension squareSize = new Dimension(
				screenSize.width / mapSquares.length,
				screenSize.height / mapSquares[0].length);
		
		
		
		
		for (int i = 0; i < mapSquares.length; i++) {
			for (int j = 0; j < mapSquares[i].length; j++) {
				g.drawRect(i * squareSize.width, j * squareSize.height,
						squareSize.width, squareSize.height);
				g.drawString(mapSquares[i][j].getChar(), i * squareSize.width, j * squareSize.height + 10);
				// TODO mapSquares[i][j]
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
