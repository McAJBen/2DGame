package settingsPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SettingsHandler {

	private static final String
	STRING_NAMES = "/names.j",
	SETTINGS_NAME = "/settings.j",
	IDENTIFIER_SYMBOL = ":",
	COMMENT_SYMBOL = "#";
	
	private Setting[] settings;
	
	SettingsHandler() {
		InputStream in = getClass().getResourceAsStream(STRING_NAMES);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		ArrayList<Setting> endSettings = new ArrayList<>();
		
		String settingsList = null;
		do {
			try {
				settingsList = br.readLine();
			} catch (IOException e) {break;}
			if (settingsList != null && !settingsList.startsWith(COMMENT_SYMBOL)) {
				
				settingsList = settingsList.replaceAll("\\s", "");
				endSettings.add(new Setting(settingsList.split(IDENTIFIER_SYMBOL)));
			}
		} while (settingsList != null);
		settings = new Setting[endSettings.size()];
		endSettings.toArray(settings);
		getSettings();
	}
	
	private void getSettings() {
		String settingsString = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(SETTINGS_NAME)));
		
		do {
			try {
				settingsString = br.readLine();
			} catch (IOException e) {}
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
		} while (settingsString != null);
	}
	
	public double getDouble(String id) {
		for (int i = 0; i < settings.length; i++) {
			if (settings[i].check(id)) {
				return (double) settings[i].getValue();
			}
		}
		return 0;
	}
	
	public byte getByte(String id) {
		for (int i = 0; i < settings.length; i++) {
			if (settings[i].check(id)) {
				return (byte) settings[i].getValue();
			}
		}
		return 0;
	}
	
	public boolean getBoolean(String id) {
		for (int i = 0; i < settings.length; i++) {
			if (settings[i].check(id)) {
				return (boolean) settings[i].getValue();
			}
		}
		return false;
	}

	public int getInt(String id) {
		for (int i = 0; i < settings.length; i++) {
			if (settings[i].check(id)) {
				return (int) settings[i].getValue();
			}
		}
		return 0;
	}

	public short getShort(String id) {
		for (int i = 0; i < settings.length; i++) {
			if (settings[i].check(id)) {
				return (short) settings[i].getValue();
			}
		}
		return 0;
	}
}
