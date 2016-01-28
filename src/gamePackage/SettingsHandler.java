package gamePackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsHandler {
	
	private Setting[] settings = {
			new Setting("FULLSCREEN", "false", GLOBAL.ValueType.BOOLEAN)
	};
	private boolean hasSettings;
	
	public SettingsHandler() {
		hasSettings = false;
	}
	
	@SuppressWarnings("resource")
	private void getSettings() {
		if (hasSettings) {
			return;
		}
		String settingsString = null;
		BufferedReader br = null;
		try {
			File settingsFile = new File(
					System.getProperty("user.dir") + GLOBAL.FILE_NAME);
			if (settingsFile.exists()) {
				br = new BufferedReader(new FileReader(settingsFile));
			}
			else throw new IOException("Settings File does not exist");
			
		} catch (IOException e1) {
			System.out.println("Default settings have been set");
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
			if (settingsString != null && !settingsString.startsWith(GLOBAL.COMMENT_SYMBOL)) {
				
				settingsString.replaceAll("\\s", "");
				
				int indexOfIdentifier = settingsString.indexOf(GLOBAL.IDENTIFIER_SYMBOL);
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

	private void createSettingsFile() {
		String settingsString = "";
		for (int i = 0; i < settings.length; i++) {
			settingsString = settingsString.concat(settings[i].getID() + GLOBAL.IDENTIFIER_SYMBOL + settings[i].getValue() + "\n");
		}
		try {
			File settingsFile = new File(
					System.getProperty("user.dir") + GLOBAL.FILE_NAME);
			BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile));
			writer.write(settingsString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object getSetting(String id) {
		getSettings();
		for (int i = 0; i < settings.length; i++) {
			if (settings[i].check(id)) {
				return settings[i].getValue();
			}
		}
		System.out.println("no such setting");
		return null;
	}
}
