package gamePackage;

import java.awt.Dimension;

public final class GLOBAL {
	
	
	static final int 
		MAP_PIXEL_SIZE = 25;
	static final double 
		PLAYER_ORIGINAL_X = 10,
		PLAYER_ORIGINAL_Y = 10,
		ANIMATION_STEP = 0.01,
		PLAYER_STEP = 0.06,
		PLAYER_SIZE = 0.6,
		PLAYER_MAX_PIXEL = GLOBAL.MAP_PIXEL_SIZE - GLOBAL.PLAYER_SIZE - 0.00001;
	static final long
		FRAME_WAIT_MS = 20, // FPS = 50
		MOVE_WAIT_MS = 10; // 100 moves per second
	static final Dimension 
		SCREEN_SIZE = new Dimension(500, 500),
		SCREEN_OFFSET = new Dimension(17, 40),
		MAP_SIZE = new Dimension(2, 2);
	static final String
		FILE_NAME = "\\2DGame.settings",
		IDENTIFIER_SYMBOL = ":",
		COMMENT_SYMBOL = "#";
	
	static enum Direction {
		RIGHT, LEFT, DOWN, UP;
	}
	static enum ValueType {
		BOOLEAN, BYTE, INT, DOUBLE;
	}
	static enum SquareType {
		FLOOR, WALL
	}
	
	
	
}
