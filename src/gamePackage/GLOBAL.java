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
		PLAYER_MAX_PIXEL = GLOBAL.MAP_PIXEL_SIZE - GLOBAL.PLAYER_SIZE - 0.00001,
		PLAYER_ANIMATION_STEP = ANIMATION_STEP * (MAP_PIXEL_SIZE - PLAYER_SIZE);
	static final long
		FRAME_WAIT_MS = 20, // FPS = 50
		MOVE_WAIT_MS = 10; // 100 moves per second
	static final Dimension 
		SCREEN_SIZE = new Dimension(500, 500),
		SCREEN_OFFSET = new Dimension(17, 40),
		MAP_SIZE = new Dimension(2, 3);
	
	static enum ValueType {
		BOOLEAN, BYTE, INT, DOUBLE;
	}
	
	
	
	
}
