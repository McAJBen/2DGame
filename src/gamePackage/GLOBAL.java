package gamePackage;

import java.awt.Dimension;
import java.awt.Point;

public final class GLOBAL {
	
	public static final Dimension SCREEN_SIZE = new Dimension(500, 500);
	public static final Dimension SCREEN_OFFSET = new Dimension(17, 40);
	public static final Dimension MAP_SIZE = new Dimension(5, 5);
	
	public static final Point MAP_START = new Point(2, 2);
	
	public static final short MAP_PIXEL_SIZE = 25;
	public static final short SHORT_MULTIPLIER = 100;
	public static final short MAP_SHORT_SIZE = MAP_PIXEL_SIZE * SHORT_MULTIPLIER;
	
	public static final Point PLAYER_ORIGINAL_POSITION = new Point(13, 13);
	public static final short PLAYER_STEP = 2;
	public static final short PLAYER_GRAV = 1;
	public static final short PLAYER_SIZE = 60;
	public static final short PLAYER_MAX_PIXEL = MAP_SHORT_SIZE - PLAYER_SIZE - 1;
	public static final short PLAYER_JUMP = 4;
	public static final short PLAYER_JUMP_WAIT = 10;
	public static final double PLAYER_FRICTION = 0.90;
	
	public static final short ENEMY_STEP = 10;
	
	public static final short MAP_CHANGE_ANIMATION_LENGTH = 100;
	public static final short MAP_CHANGE_ANIMATION_STEP = MAP_SHORT_SIZE / MAP_CHANGE_ANIMATION_LENGTH;
	public static final short MAP_CHANGE_ANIMATION_PLAYER_STEP = (MAP_SHORT_SIZE - PLAYER_SIZE) / MAP_CHANGE_ANIMATION_LENGTH;
	
	public static final short DEATH_ANIMATION_LENGTH = 100;
	public static final short DEATH_ANIMATION_STEP = 1;
	
	public static final short END_ANIMATION_LENGTH = 1_000;
	public static final short END_ANIMATION_STEP = 1;
	
	public static final long FRAME_WAIT_MS = 20; // 20ms between frames FPS = 50
	public static final long MOVE_WAIT_MS = 10; // 10ms between moves 100/second
	
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
