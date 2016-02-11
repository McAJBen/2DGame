package mapPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import animationPackage.DeathAnimation;
import settingsPackage.GLOBAL;

public class MapSquare {
	
	private static BufferedImage coin;
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
				ELECTRIC_SQUARE_COLOR = new Color(153, 217, 234),
				ELECTRICITY_COLOR = new Color(0, 162, 232),
				SPEED_COLOR = new Color(34, 177, 76),
				FALL_SQUARE_COLOR = new Color(185, 122, 87),
				ELECTRIC_SHOT_COLOR = new Color(0, 162, 232);
				
	private static enum SquareType {
		FLOOR, WALL, COIN, ENEMY,
		ENEMY_WALL, PLAYER_WALL, 
		JUMP_SQUARE, ELECTRIC_SQUARE, 
		SPEED_SQUARE, FALL_SQUARE, 
		ELECTRIC_SHOT
	}
	
	private SquareType squareType;
	
	public MapSquare(int rgb) {
		Color pixelColor = new Color(rgb);
		
		if (pixelColor.equals(FLOOR_COLOR)) {
			squareType = SquareType.FLOOR;
		}
		else if (pixelColor.equals(WALL_COLOR)) {
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
		else if (pixelColor.equals(ELECTRIC_SQUARE_COLOR)) {
			squareType = SquareType.ELECTRIC_SQUARE;
		}
		else if (pixelColor.equals(SPEED_COLOR)) {
			squareType = SquareType.SPEED_SQUARE;
		}
		else if (pixelColor.equals(FALL_SQUARE_COLOR)) {
			squareType = SquareType.FALL_SQUARE;
		}
		else if (pixelColor.equals(ELECTRIC_SHOT_COLOR)) {
			squareType = SquareType.ELECTRIC_SHOT;
		}
		else {
			System.out.println("COLOR ERROR:");
			System.out.println(
					pixelColor.getRed() + " " + 
					pixelColor.getGreen() + " " +
					pixelColor.getBlue());
			squareType = SquareType.FLOOR;
		}
	}

	public static void paint(MapSquare[][] mapSquares, Graphics g) {
		g.setColor(FLOOR_COLOR);
		g.fillRect(0, 0, GLOBAL.screenSize.width, GLOBAL.screenSize.height);
		for (int i = 0; i < GLOBAL.MAP_PIXEL_SIZE; i++) {
			int px = (int)(GLOBAL.screenPixelWidth * i);
			int width = (int)(GLOBAL.screenPixelWidth * (i + 1) - px);
			for (int j = 0; j < GLOBAL.MAP_PIXEL_SIZE; j++) {
				int py = (int)(GLOBAL.screenPixelHeight * j);
				int height = (int)(GLOBAL.screenPixelHeight * (j + 1) - py);
				mapSquares[i][j].paint(g, px, py, width, height);
			}
		}
	}

	private void paint(Graphics g, int x, int y, int width, int height) {
		g.setColor(getColor());
		switch (getType()) {
		case COIN:
			paintCoin(g, x, y, width, height);
			break;
		case WALL:
		case ENEMY_WALL:
		case PLAYER_WALL:
		case JUMP_SQUARE:
			g.fillRect(x, y, width, height);
			break;
		case ELECTRIC_SQUARE:
			g.fillRect(x, y, width, height);
			paintElectricity(g, x, y, width, height, 2);
			break;
		case SPEED_SQUARE:
			paintSpeed(g, x, y, width, height, 2);
			break;
		case FALL_SQUARE:
			paintFall(g, x, y, width, height, 1);
			break;
		case ELECTRIC_SHOT:
			g.fillRect(x, y, width, height);
			break;
		case FLOOR:
		case ENEMY:
		}
	}

	private static void paintFall(Graphics g, int x, int y, int width, int height, int times) {
		g.setColor(FALL_SQUARE_COLOR);
		height -= GLOBAL.SPEED_LINE_SIZE;
		for (int i = 0; i < times; i++) {
			int thisX = x + (int)(width * rand.nextFloat());
			int thisY = y + (int)(height * rand.nextFloat());
			int thisY2 = thisY + GLOBAL.SPEED_LINE_SIZE;
			
			g.drawLine(thisX, thisY, thisX, thisY2);
		}
	}

	private static void paintSpeed(Graphics g, int x, int y, int width, int height, int times) {
		g.setColor(SPEED_COLOR);
		width -= GLOBAL.SPEED_LINE_SIZE;
		for (int i = 0; i < times; i++) {
			int thisX = x + (int)(width * rand.nextFloat());
			int thisY = y + (int)(height * rand.nextFloat());
			int thisX2 = thisX + GLOBAL.SPEED_LINE_SIZE;
			
			g.drawLine(thisX, thisY, thisX2, thisY);
		}
	}

	public static void paintCoin(Graphics g, int x, int y, int width, int height) {
		g.drawImage(coin, x, y, width, height, null);
	}

	public static void paintElectricity(Graphics g, int x, int y, int width, int height, int times) {
		g.setColor(ELECTRICITY_COLOR);
		int lastX = x + (int)(width * rand.nextFloat());
		int lastY = y + (int)(height * rand.nextFloat());
		for (int i = 0; i < times; i++) {
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
		case ELECTRIC_SQUARE:
			return ELECTRIC_SQUARE_COLOR;
		case SPEED_SQUARE:
			return SPEED_COLOR;
		case FALL_SQUARE:
			return FALL_SQUARE_COLOR;
		case ELECTRIC_SHOT:
			return ELECTRIC_SHOT_COLOR;
		}
	}

	public boolean getWallPlayer() {
		switch (squareType) {
		case FLOOR:
		case COIN:
		case ENEMY:
		case PLAYER_WALL:
		case JUMP_SQUARE:
		case ELECTRIC_SQUARE:
		case FALL_SQUARE:
		case SPEED_SQUARE:
			return false;
			
		case ENEMY_WALL:
		case WALL:
		case ELECTRIC_SHOT:
			return true;
		}
		return false;
	}
	
	public boolean getWallEnemy() {
		switch (squareType) {
		case FLOOR:
		case COIN:
		case ENEMY:	
		case ENEMY_WALL:
		case ELECTRIC_SQUARE:
		case FALL_SQUARE:
		case SPEED_SQUARE:
			return false;
		
		
		case ELECTRIC_SHOT:
		case WALL:
		case PLAYER_WALL:
		case JUMP_SQUARE:
			return true;
		}
		return false;
	}

	public SquareType getType() {
		return squareType;
	}

	public void setFloor() {
		squareType = SquareType.FLOOR;
	}
	
	public boolean isFloor() {
		return squareType == SquareType.FLOOR;
	}

	public boolean isCoin() {
		return squareType == SquareType.COIN;
	}

	public boolean isEnemy() {
		return squareType == SquareType.ENEMY;
	}
	
	public boolean isElectric() {
		return squareType == SquareType.ELECTRIC_SQUARE;
	}
	
	public boolean isJumpSquare() {
		return squareType == SquareType.JUMP_SQUARE;
	}
	
	public boolean isSpeed() {
		return squareType == SquareType.SPEED_SQUARE;
	}
	
	public boolean isFall() {
		return squareType == SquareType.FALL_SQUARE;
	}

	public boolean isElectricShot() {
		return squareType == SquareType.ELECTRIC_SHOT;
	}
	
	public static void load() {
		try {
			coin = ImageIO.read(DeathAnimation.class.getResource("/Coin.png"));
		} catch (IOException e) {}
	}
	
	@Override
	public String toString() {
		return squareType.name();
	}

}
