package settingsPackage;


public class Setting {
	
	private String value;
	private ValueType valueType;
	
	
	public Setting(ValueType vt) {
		valueType = vt;
	}
	
	public void setVal(String value) {
		this.value = value;
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
		return value + ":" + valueType;
	}
}
