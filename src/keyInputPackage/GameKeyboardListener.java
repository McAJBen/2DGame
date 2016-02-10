package keyInputPackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameKeyboardListener implements KeyListener {
	
	private ArrayList<Integer> keysDown = new ArrayList<>();
	
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
		if (!getKey(keyCode)) {
			keysDown.add(keyCode);
		}
	}
	
	private void removeKey(int keyCode) {
		for (int i = 0; i < keysDown.size(); i++) {
			if (keyCode == keysDown.get(i)) {
				keysDown.remove(i);
				i--;
			}
		}		
	}
	
	private boolean getKey(int keyCode) {
		for (int k: keysDown) {
			if (k == keyCode) {
				return true;
			}
		}
		return false;
	}

	private boolean getKey(int keyCode1, int keyCode2) {
		for (int k: keysDown) {
			if (k == keyCode1 || k == keyCode2) {
				return true;
			}
		}
		return false;
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
		keysDown.clear();
	}

	public boolean getJump() {
		return getKey(KeyEvent.VK_SPACE);
	}
	
	@Override
	public String toString() {
		String s = "";
		for (int k: keysDown) {
			s = s.concat(KeyEvent.getKeyText(k));
		}
		return s;
	}
}