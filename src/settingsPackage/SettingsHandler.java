package settingsPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SettingsHandler {

	private static final String
		SETTINGS_NAME = "/settings",
		IDENTIFIER_SYMBOL = ":",
		COMMENT_SYMBOL = "#";
	
	private static Setting[] settings;
	
	SettingsHandler() {
		settings = new Setting[SettingName.values().length];
		for (int i = 0; i < settings.length; i++) {
			settings[i] = new Setting(SettingName.vt[i]);
		}
		getSettings();
	}
	
	private void getSettings() {
		String settingsString = null;
		BufferedReader br = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream(SETTINGS_NAME)));
		
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
				settings[SettingName.valueOf(stringBeforeIDSymbol).ordinal()].setVal(stringAfterIDSymbol);
			}
		} while (settingsString != null);
		
		// reads from file next to .jar
		try {
			File f = new File(System.getProperty("user.dir") + SETTINGS_NAME);
			if (!f.exists()) {
				return;
			}
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
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
				settings[SettingName.valueOf(stringBeforeIDSymbol).ordinal()].setVal(stringAfterIDSymbol);
			}
		} while (settingsString != null);
	}
	
	public double getDouble(SettingName sn) {
		return (double) settings[sn.ordinal()].getValue();
	}
	
	public byte getByte(SettingName sn) {
		return (byte) settings[sn.ordinal()].getValue();
	}
	
	public boolean getBoolean(SettingName sn) {
		return (boolean) settings[sn.ordinal()].getValue();
	}

	public int getInt(SettingName sn) {
		return (int) settings[sn.ordinal()].getValue();
	}

	public short getShort(SettingName sn) {
		return (short) settings[sn.ordinal()].getValue();
	}

	public static void addUserSetting(String string, String value) {
		
		File f = new File(System.getProperty("user.dir") + SETTINGS_NAME);
		removeLines(f, string, value);
	}
	
	// TODO split this method into remove and add

	private static void removeLines(File f, String string, String value) {
		
		ArrayList<String> lines = new ArrayList<>();
		String settingsString = "";
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		do {
			try {
				settingsString = br.readLine();
			} catch (IOException e) {}
			if (settingsString != null) {
				settingsString = settingsString.replaceAll("\\s", "");
				int indexOfIdentifier = settingsString.indexOf(IDENTIFIER_SYMBOL);
				if (indexOfIdentifier == -1) {
					lines.add(settingsString);
					continue;
				}
				if (!settingsString.substring(0, indexOfIdentifier).equals(string)) {
					lines.add(settingsString);
				}
			}
		} while (settingsString != null);
		try {
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(f));
			for (String s: lines) {
				writer.println(s);
			}
			writer.println(string + IDENTIFIER_SYMBOL + value);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
