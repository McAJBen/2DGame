package gamePackage;

import java.awt.Dimension;
import java.awt.Point;

public final class GLOBAL {
	
	public static final Dimension SCREEN_SIZE = new Dimension(500, 500);
	public static final Dimension SCREEN_OFFSET = new Dimension(17, 40);
	public static final Dimension MAP_SIZE = new Dimension(7, 5);
	
	public static final Point MAP_START = new Point(0, 0);
	
	public static final short MAP_PIXEL_SIZE = 50;
	public static final short SHORT_MULTIPLIER = 100;
	public static final short MAP_SHORT_SIZE = MAP_PIXEL_SIZE * SHORT_MULTIPLIER;
	
	public static final Point PLAYER_ORIGINAL_POSITION = new Point(13, 13);
	public static final short PLAYER_STEP = 2;
	public static final short PLAYER_GRAV = 1;
	public static final short PLAYER_SIZE = 60;
	public static final short PLAYER_SHORT_MAX = MAP_SHORT_SIZE - PLAYER_SIZE - 1;
	public static final byte PLAYER_JUMP = 4;
	public static final byte PLAYER_JUMP_WAIT = 10;
	public static final byte PLAYER_JUMP_LIMIT = 2;
	public static final double PLAYER_FRICTION = 0.90;
	
	public static final short ENEMY_STEP = 10;
	
	public static final short MAP_CHANGE_ANIMATION_LENGTH = 100;
	public static final short MAP_CHANGE_ANIMATION_STEP = MAP_SHORT_SIZE / MAP_CHANGE_ANIMATION_LENGTH;
	public static final short MAP_CHANGE_ANIMATION_PLAYER_STEP = (MAP_SHORT_SIZE - PLAYER_SIZE) / MAP_CHANGE_ANIMATION_LENGTH;
	
	public static final short DEATH_ANIMATION_LENGTH = MAP_SHORT_SIZE * 2;
	public static final short DEATH_ANIMATION_STEP = DEATH_ANIMATION_LENGTH / 50;
	
	public static final short END_ANIMATION_LENGTH = 1000;
	public static final short END_ANIMATION_STEP = 1;
	
	public static final long FRAME_WAIT_MS = 20; // 20ms between frames FPS = 50
	public static final long MOVE_WAIT_MS = 10; // 10ms between moves 100/second
	
	public static final int MAX_COINS = 50;
	
	public static Dimension screenSize;
	public static Dimension playerScreenSize;
	
	public static float pixelWidth;
	public static float pixelHeight;
	public static float screenShortWidth;
	public static float screenShortHeight;
	
	public static Point screenCoinPosition;
	
	public static void setWindowSize(Dimension screenSize) { // TODO use to prevent more calcs
		GLOBAL.screenSize = screenSize;
		screenShortWidth = (float)screenSize.width / GLOBAL.MAP_SHORT_SIZE;
		screenShortHeight = (float)screenSize.height / GLOBAL.MAP_SHORT_SIZE;
		playerScreenSize = new Dimension(
				screenSize.width * GLOBAL.PLAYER_SIZE / GLOBAL.MAP_SHORT_SIZE + 1,
				screenSize.height * GLOBAL.PLAYER_SIZE / GLOBAL.MAP_SHORT_SIZE + 1);
		pixelWidth = (float)screenSize.width / GLOBAL.MAP_PIXEL_SIZE;
		pixelHeight = (float)screenSize.height / GLOBAL.MAP_PIXEL_SIZE;
		screenCoinPosition = new Point(0, screenSize.height);
		
	}
	
	public static enum ValueType {
		BOOLEAN, BYTE, INT, DOUBLE;
	}
	public static enum Direction {
		RIGHT, DOWN, LEFT, 
		UP {
			@Override
			public Direction next() {
				return Direction.RIGHT;
			};
		};
		public Direction next() {
			return values()[ordinal() + 1];
		}
	}
	
	
	
	
}
