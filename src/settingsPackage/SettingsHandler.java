package settingsPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SettingsHandler {

	private static final String
	FILE_NAME = "\\2DGame.settings",
	IDENTIFIER_SYMBOL = ":",
	COMMENT_SYMBOL = "#";
	
	private Setting[] settings;
	private File settingsFile = new File(System.getProperty("user.dir") + FILE_NAME);
	
	SettingsHandler() {
		InputStream in = getClass().getResourceAsStream("/stngNms.txt");
		
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
	
	
	@SuppressWarnings("resource")
	private void getSettings() {
		String settingsString = null;
		BufferedReader br = null;
		try {
			if (settingsFile.exists()) {
				br = new BufferedReader(new FileReader(settingsFile));
			}
			else throw new IOException("Settings File does not exist");
			
		} catch (IOException e1) {
			createSettingsFile();
			return;
		}
		
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

	private void createSettingsFile() {
		String settingsString = "";
		for (int i = 0; i < settings.length; i++) {
			settingsString = settingsString.concat(settings[i].getID() + IDENTIFIER_SYMBOL + settings[i].getValue() + "\n");
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile));
			writer.write(settingsString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
