package gamePackage;

import gamePackage.GLOBAL.ValueType;

public class Setting {
	
	
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
		case SHORT:
			return Short.parseShort(value);
		}
		return null;
	}

	public String getID() {
		return settingID;
	}
}
