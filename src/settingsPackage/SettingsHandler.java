package settingsPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import settingsPackage.GLOBAL.ValueType;

public class SettingsHandler {

	static final String
	FILE_NAME = "\\2DGame.settings",
	IDENTIFIER_SYMBOL = ":",
	COMMENT_SYMBOL = "#";

	private static Setting[] settings = {
			new Setting("FULLSCREEN", "false", ValueType.BOOLEAN),
			new Setting("MAP_START_X", "0", ValueType.SHORT),
			new Setting("MAP_START_Y", "0", ValueType.SHORT),
			new Setting("MAP_PIXEL_SIZE", "50", ValueType.SHORT),
			new Setting("PLAYER_ORIGINAL_POSITION_X", "13", ValueType.SHORT),
			new Setting("PLAYER_ORIGINAL_POSITION_Y", "13", ValueType.SHORT),
			new Setting("MAX_COINS", "200", ValueType.INT),
			new Setting("DEBUG_MODE", "false", ValueType.BOOLEAN)
	};
	private static boolean hasSettings = false;
	
	@SuppressWarnings("resource")
	private static void getSettings() {
		if (hasSettings) {
			return;
		}
		String settingsString = null;
		BufferedReader br = null;
		try {
			File settingsFile = new File(System.getProperty("user.dir") + FILE_NAME);
			if (settingsFile.exists()) {
				br = new BufferedReader(new FileReader(settingsFile));
			}
			else throw new IOException("Settings File does not exist");
			
		} catch (IOException e1) {
			createSettingsFile();
			return;
		}
		
		while (true) {
			try {
				settingsString = br.readLine();
				if (settingsString == null) {
					throw new IOException("No Line To Read");
				}
			} catch (IOException e) {
				break;
			}
			if (settingsString != null && !settingsString.startsWith(COMMENT_SYMBOL)) {
				
				settingsString = settingsString.replaceAll("\\s", "");
				
				int indexOfIdentifier = settingsString.indexOf(IDENTIFIER_SYMBOL);
				if (indexOfIdentifier == -1) {
					continue;
				}
				String stringAfterIDSymbol = settingsString.substring(indexOfIdentifier + 1);
				String stringBeforeIDSymbol = settingsString.substring(0, indexOfIdentifier);
				for (int i = 0; i < settings.length; i++) {
					if (settings[i].check(stringBeforeIDSymbol)) {
						settings[i].setVal(stringAfterIDSymbol);
						break;
					}
				}
			}
		}
		hasSettings = true;
	}

	private static void createSettingsFile() {
		String settingsString = "";
		for (int i = 0; i < settings.length; i++) {
			settingsString = settingsString.concat(settings[i].getID() + IDENTIFIER_SYMBOL + settings[i].getValue() + "\n");
		}
		try {
			
			File settingsFile = new File(
					System.getProperty("user.dir") + FILE_NAME);
			BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile));
			writer.write(settingsString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean getSettingBoolean(String id) {
		getSettings();
		for (int i = 0; i < settings.length; i++) {
			if (settings[i].check(id)) {
				return (boolean) settings[i].getValue();
			}
		}
		System.out.println("no such setting" + id);
		return false;
	}

	public static Integer getSettingInt(String id) {
		getSettings();
		for (int i = 0; i < settings.length; i++) {
			if (settings[i].check(id)) {
				return (int) settings[i].getValue();
			}
		}
		System.out.println("no such setting" + id);
		return 0;
	}

	public static short getSettingShort(String id) {
		getSettings();
		for (int i = 0; i < settings.length; i++) {
			if (settings[i].check(id)) {
				return (short) settings[i].getValue();
			}
		}
		System.out.println("no such setting" + id);
		return 0;
	}
}
