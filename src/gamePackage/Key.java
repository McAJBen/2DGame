package gamePackage;

import java.awt.event.KeyEvent;

public class Key {

	private int keyCode;
	private boolean eventType; // true for pressed, false for Released;
	
	public Key(int keyCode, boolean eventType) {
		this.keyCode = keyCode;
		this.eventType = eventType;
	}
	
	public int getKey() {
		return keyCode;
	}
	
	public boolean getEvent() {
		return eventType;
	}
	
	private String getKeyChar() {
		return KeyEvent.getKeyText(keyCode);
	}
	
	private String getEventChar() {
		return eventType ? "-" : "_";
	}
	
	@Override
	public String toString() {
		return getKeyChar() + getEventChar();
	}
}
