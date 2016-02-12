package settingsPackage;


public class Setting {
	
	public static enum ValueType {
		BOOLEAN, BYTE, SHORT, INT, DOUBLE;
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
	
	//SETTINGSID:DEFAULTVAL:VALUETYPE
	public Setting(String[] s) {
		this(s[0], s[1], ValueType.valueOf(s[2]));
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
		case SHORT:
			return Short.parseShort(value);
		}
		return null;
	}
	
	@Override
	public String toString() {
		return settingID + ":" + value + ":" + valueType;
	}

	public String getID() {
		return settingID;
	}
}
