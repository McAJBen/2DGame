package gamePackage;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class MapSquare {
	
	private static Random rand = new Random();
	
	static final Color 
				FLOOR_COLOR = new Color(195, 195, 195),
				WALL_COLOR = new Color(239, 228, 176),
				COIN_COLOR = new Color(255, 242, 0),
				ENEMY_COLOR = new Color(237, 28, 36),
				ENEMY_COLOR_DOWN = new Color(136, 0, 21),
				ENEMY_WALL_COLOR = new Color(127, 127, 127),
				PLAYER_WALL_COLOR = new Color(255, 127, 39),
				JUMP_SQUARE_COLOR = new Color(255, 174, 201),
				ELECTRIC_WALL_COLOR = new Color(153, 217, 234),
				ELECTRICITY_COLOR = new Color(0, 162, 232);
				
	private static enum SquareType {
		FLOOR, WALL, COIN, ENEMY, ENEMY_WALL, PLAYER_WALL, JUMP_SQUARE, ELECTRIC_WALL
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
		else if (pixelColor.equals(PLAYER_WALL_COLOR)) {
			squareType = SquareType.PLAYER_WALL;
		}
		else if (pixelColor.equals(JUMP_SQUARE_COLOR)) {
			squareType = SquareType.JUMP_SQUARE;
		}
		else if (pixelColor.equals(ELECTRIC_WALL_COLOR)) {
			squareType = SquareType.ELECTRIC_WALL;
		}
		else {
			squareType = SquareType.FLOOR;
		}
	}

	public static void paint(MapSquare[][] mapSquares, Graphics g) {
		g.setColor(FLOOR_COLOR);
		g.fillRect(0, 0, GLOBAL.screenSize.width, GLOBAL.screenSize.height);
		for (int i = 0; i < GLOBAL.MAP_PIXEL_SIZE; i++) {
			int px = (int)(GLOBAL.pixelWidth * i);
			int width = (int)(GLOBAL.pixelWidth * (i + 1) - px);
			for (int j = 0; j < GLOBAL.MAP_PIXEL_SIZE; j++) {
				int py = (int)(GLOBAL.pixelHeight * j);
				int height = (int)(GLOBAL.pixelHeight * (j + 1) - py);
				mapSquares[i][j].paint(g, px, py, width, height);
			}
		}
	}

	private void paint(Graphics g, int x, int y, int width, int height) {
		g.setColor(getColor());
		switch (getType()) {
		case COIN:
		case ENEMY:
			g.fillOval(x, y,
				width, height);
			break;
		case WALL:
		case ENEMY_WALL:
		case PLAYER_WALL:
		case JUMP_SQUARE:
			g.fillRect(x, y,
				width, height);
			break;
		case ELECTRIC_WALL:
			g.fillRect(
					x, y,
				width, height);
			paintElectricity(g, x, y, width, height);
		case FLOOR:
		}
	}

	private void paintElectricity(Graphics g, int x, int y, int width, int height) {
		g.setColor(ELECTRICITY_COLOR);
		int lastX = x + (int)(width * rand.nextFloat());
		int lastY = y + (int)(height * rand.nextFloat());
		for (int i = 0; i < 2; i++) {
			int thisX = x + (int)(width * rand.nextFloat());
			int thisY = y + (int)(height * rand.nextFloat());
			g.drawLine( lastX, lastY, thisX, thisY);
			lastX = thisX;
			lastY = thisY;
		}
	}

	private Color getColor() {
		switch (squareType) {
		default:
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
		case PLAYER_WALL:
			return PLAYER_WALL_COLOR;
		case JUMP_SQUARE:
			return JUMP_SQUARE_COLOR;
		case ELECTRIC_WALL:
			return ELECTRIC_WALL_COLOR;
		}
	}

	public boolean getWallPlayer() {
		switch (squareType) {
		case FLOOR:
		case COIN:
		case ENEMY:
		case PLAYER_WALL:
		case JUMP_SQUARE:
		case ELECTRIC_WALL:
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
		case ELECTRIC_WALL:
			return false;
		default:
		case WALL:
		case PLAYER_WALL:
		case JUMP_SQUARE:
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
	
	public boolean isElectric() {
		return squareType == SquareType.ELECTRIC_WALL;
	}
	
	public boolean isJumpSquare() {
		return squareType == SquareType.JUMP_SQUARE;
	}

	
}
