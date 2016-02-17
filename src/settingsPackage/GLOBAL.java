package settingsPackage;

import java.awt.Dimension;

import settingsPackage.SettingsHandler;

public final class GLOBAL {
	
	public static boolean DEBUG_MODE;
	
	public static final Dimension SCREEN_SIZE = new Dimension(500, 500);
	public static final Dimension SCREEN_OFFSET = new Dimension(17, 40);
	public static final Dimension MAP_SIZE = new Dimension(7, 5);
	
	public static short MAP_START_X;
	public static short MAP_START_Y;
	
	public static short MAP_PIXEL_SIZE;
	public static final short U_MULTIPLIER = 100;
	public static int MAP_U_SIZE;
	
	public static short PLAYER_ORIGINAL_POSITION_X;
	public static short PLAYER_ORIGINAL_POSITION_Y;
	
	public static final short PLAYER_MOVE_SPEED = 2;
	public static final short PLAYER_GRAV = 1;
	public static final short PLAYER_FALL = 3;
	public static final short PLAYER_SIZE = 80;
	public static int PLAYER_U_MAX;
	public static final byte PLAYER_JUMP = 4;
	public static final byte PLAYER_JUMP_WAIT = 10;
	public static final byte PLAYER_JUMP_LIMIT = 3;
	public static final double PLAYER_FRICTION = 0.90;
	public static final short PLAYER_MAX_VELOCITY = U_MULTIPLIER;
	public static final byte PLAYER_SPEED_MULTI = 3;
	public static final byte PLAYER_MAP_CHANGE_WAIT = 50;
	
	public static final short ENEMY_STEP = 10;
	
	public static final short MAP_CHANGE_ANIMATION_LENGTH = 100;
	public static short MAP_CHANGE_ANIMATION_STEP;
	
	public static short DEATH_ANIMATION_LENGTH;
	public static short DEATH_ANIMATION_STEP;
	
	public static final short END_ANIMATION_LENGTH = 1000;
	public static final short END_ANIMATION_STEP = 1;
	
	public static boolean FULLSCREEN;
	public static final long FRAME_WAIT_MS = 10; // 20ms between frames FPS = 50
	public static final long MOVE_WAIT_MS = 10; // 10ms between moves 100/second
	
	public static final short MAX_BUBBLE_RADIUS = 20;
	public static final short MAX_BUBBLE_DIAM = MAX_BUBBLE_RADIUS * 2;
	public static final short SPEED_LINE_SIZE = 4;
	public static final short ELECTRIC_SHOT_TIME = 200;
	public static final short ELECTRIC_SHOT_DELAY = 200;
	public static final short ELECTRIC_SHOT_TOTAL = ELECTRIC_SHOT_TIME + ELECTRIC_SHOT_DELAY;
	public static final short ELECTRIC_SHOT_WIDTH = 80;
	public static final short ELECTRIC_SHOT_OFFSET = (U_MULTIPLIER - ELECTRIC_SHOT_WIDTH) / 2;
	
	public static int MAX_COINS;
	
	public static int screenOptionsWidth;
	public static Dimension optionsTextPosition;
	private static final Dimension optionsTextSize = new Dimension(163, 31);
	
	public static Dimension screenSize;
	public static Dimension playerScreenSize;
	
	public static float screenPixelWidth;
	public static float screenPixelHeight;
	public static float screenUWidth;
	public static float screenUHeight;
	
	public static void setWindowSize(Dimension screenSize) {
		GLOBAL.screenSize = screenSize;
		screenUWidth = (float)screenSize.width / GLOBAL.MAP_U_SIZE;
		screenUHeight = (float)screenSize.height / GLOBAL.MAP_U_SIZE;
		playerScreenSize = new Dimension(
				screenSize.width * GLOBAL.PLAYER_SIZE / GLOBAL.MAP_U_SIZE + 1,
				screenSize.height * GLOBAL.PLAYER_SIZE / GLOBAL.MAP_U_SIZE + 1);
		screenPixelWidth = (float)screenSize.width / GLOBAL.MAP_PIXEL_SIZE;
		screenPixelHeight = (float)screenSize.height / GLOBAL.MAP_PIXEL_SIZE;
		screenOptionsWidth = screenSize.width - 150;
		optionsTextPosition = new Dimension(
				(screenSize.width - optionsTextSize.width) / 2,
				(screenSize.height + optionsTextSize.height) / 2);
		
		
	}
	
	public static void setSettings() {
		
		SettingsHandler sh = new SettingsHandler();
		
		FULLSCREEN = sh.getBoolean("FULLSCREEN");
		MAP_START_X = sh.getShort("MAP_START_X");
		MAP_START_Y = sh.getShort("MAP_START_Y");
		MAP_PIXEL_SIZE = sh.getShort("MAP_PIXEL_SIZE");
		PLAYER_ORIGINAL_POSITION_X = sh.getShort("PLAYER_ORIGINAL_POSITION_X");
		PLAYER_ORIGINAL_POSITION_Y = sh.getShort("PLAYER_ORIGINAL_POSITION_Y");
		MAX_COINS = sh.getInt("MAX_COINS");
		DEBUG_MODE = sh.getBoolean("DEBUG_MODE");
		
		MAP_U_SIZE = MAP_PIXEL_SIZE * U_MULTIPLIER;
		PLAYER_U_MAX = MAP_U_SIZE - PLAYER_SIZE - 1;
		MAP_CHANGE_ANIMATION_STEP = (short) (MAP_U_SIZE / MAP_CHANGE_ANIMATION_LENGTH);
		DEATH_ANIMATION_LENGTH = (short) (MAP_U_SIZE * 2);
		DEATH_ANIMATION_STEP = (short) (DEATH_ANIMATION_LENGTH / 50);
	}
	
	public static void setFullscreen(boolean selected) {
		SettingsHandler.addUserSetting("FULLSCREEN", selected + "");
		FULLSCREEN = selected;
	}
	
	public static void setDebugMode(boolean selected) {
		SettingsHandler.addUserSetting("DEBUG_MODE", selected + "");
		DEBUG_MODE = selected;
	}
	
	public static void setMapStartX(int value) {
		SettingsHandler.addUserSetting("MAP_START_X", value + "");
		MAP_START_X = (short) value;
	}
	
	public static void setMapStartY(int value) {
		SettingsHandler.addUserSetting("MAP_START_Y", value + "");
		MAP_START_Y = (short) value;
	}
	
	public static void setPlayerOriginalPositionX(int value) {
		SettingsHandler.addUserSetting("PLAYER_ORIGINAL_POSITION_X", value + "");
		PLAYER_ORIGINAL_POSITION_X = (short) value;
	}
	
	public static void setPlayerOriginalPositionY(int value) {
		SettingsHandler.addUserSetting("PLAYER_ORIGINAL_POSITION_Y", value + "");
		PLAYER_ORIGINAL_POSITION_Y = (short) value;
	}
	
	public static void setMaxCoins(int value) {
		SettingsHandler.addUserSetting("MAX_COINS", value + "");
		MAX_COINS = (short) value;
	}
}