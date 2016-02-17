package keyInputPackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyboardListener implements KeyListener {
	
	private boolean[] keysDown = new boolean[KeyEvent.KEY_LAST];
	
	@Override
	public void keyPressed(KeyEvent e) {
		addKey(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		removeKey(e.getKeyCode());
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {}

	private void addKey(int keyCode) {
		keysDown[keyCode] = true;
	}
	
	private void removeKey(int keyCode) {
		keysDown[keyCode] = false;
	}
	
	private boolean getKey(int keyCode) {
		return keysDown[keyCode];
	}

	private boolean getKey(int keyCode1, int keyCode2) {
		return keysDown[keyCode1] || keysDown[keyCode2];
	}
	
	public byte getX() {
		byte x = 0;
		if (getKey(KeyEvent.VK_LEFT, KeyEvent.VK_A)) {
			x--;
		}
		if (getKey(KeyEvent.VK_RIGHT, KeyEvent.VK_D)) {
			x++;
		}
		return x;
	}

	public void reset() {
		for (int i = 0; i < keysDown.length; i++) {
			keysDown[i] = false;
		}
	}

	public boolean getJump() {
		return getKey(KeyEvent.VK_SPACE);
	}
	
	public boolean getO() {
		if (getKey(KeyEvent.VK_O)) {
			removeKey(KeyEvent.VK_O);
			return true;
		}
		return false;
	}
	
	public boolean getP() {
		if (getKey(KeyEvent.VK_P)) {
			removeKey(KeyEvent.VK_P);
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < keysDown.length; i++) {
			if (keysDown[i]) {
				s = s.concat(KeyEvent.getKeyText(i));
			}
			
		}
		return s;
	}

	public boolean getEscape() {
		return getKey(KeyEvent.VK_ESCAPE);
	}
}