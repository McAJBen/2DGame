package gamePackage;

import java.awt.Dimension;
import java.awt.Point;

public final class GLOBAL {
	
	
	static final short 
		MAP_PIXEL_SIZE = 25,
		SHORT_MULTIPLIER = 100,
		MAP_SHORT_SIZE = MAP_PIXEL_SIZE * SHORT_MULTIPLIER,
		PLAYER_ORIGINAL_X = 13,
		PLAYER_ORIGINAL_Y = 13,
		PLAYER_SIZE = 60,
		PLAYER_MAX_PIXEL = MAP_SHORT_SIZE - PLAYER_SIZE - 1,
		ENEMY_STEP = 10;
	static final double
		ANIMATION_STEP = 0.01,
		PLAYER_STEP = 0.02,
		PLAYER_GRAV = 0.1,
		PLAYER_JUMP = 0.03,
		PLAYER_SIZE_DOUBLE = (double)PLAYER_SIZE / SHORT_MULTIPLIER,
		
		PLAYER_ANIMATION_STEP = ANIMATION_STEP * (MAP_PIXEL_SIZE - PLAYER_SIZE_DOUBLE);
	static final long
		FRAME_WAIT_MS = 20, // FPS = 50
		MOVE_WAIT_MS = 10; // 100 moves per second
	static final Dimension 
		SCREEN_SIZE = new Dimension(500, 500),
		SCREEN_OFFSET = new Dimension(17, 40),
		MAP_SIZE = new Dimension(5, 5);
	static final Point
		MAP_START = new Point(2, 2);
	
	static enum ValueType {
		BOOLEAN, BYTE, INT, DOUBLE;
	}
	static enum Direction {
		RIGHT, DOWN, LEFT, 
		UP {
			@Override // sets the last iterator back to the beginning
			public Direction next() {
				return Direction.RIGHT;
			};
		};
		// increases to the next type of TriangleMode
		public Direction next() {
			return values()[ordinal() + 1];
		}
	}
	
	
	
	
}
