package settingsPackage;

enum SettingName {
	FULLSCREEN,
	MAP_START_X,
	MAP_START_Y,
	MAP_PIXEL_SIZE,
	PLAYER_ORIGINAL_POSITION_X,
	PLAYER_ORIGINAL_POSITION_Y,
	MAX_COINS,
	DEBUG_MODE;
	
	static final ValueType[] vt = {
			ValueType.BOOLEAN,
			ValueType.SHORT,
			ValueType.SHORT,
			ValueType.SHORT,
			ValueType.SHORT,
			ValueType.SHORT,
			ValueType.INT,
			ValueType.BOOLEAN
	};
}


