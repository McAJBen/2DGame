package gamePackage;

public class Setting {
	
	static enum ValueType {
		BOOLEAN, BYTE, INT, DOUBLE;
	}
	private String defaultVal;
	private String settingID;
	private String value;
	private ValueType valueType;
	
	
	public Setting(String settingID, String defaultVal, ValueType valueType) {
		this.settingID = settingID;
		this.defaultVal = defaultVal;
		this.valueType = valueType;
		this.value = defaultVal;
	}
	
	public boolean check(String stringBeforeIDSymbol) {
		return stringBeforeIDSymbol.equalsIgnoreCase(settingID);
	}
	
	public void setVal(String value) {
		this.value = value;
	}
	
	public void setDefault() {
		value = defaultVal;
	}
	
	public Object getValue() {
		switch (valueType) {
		case BOOLEAN:
			return Boolean.parseBoolean(value);
		case BYTE:
			return Byte.parseByte(value);
		case DOUBLE:
			return Double.parseDouble(value);
		case INT:
			return Integer.parseInt(value);
		}
		return null;
	}

	public String getID() {
		return settingID;
	}
}
