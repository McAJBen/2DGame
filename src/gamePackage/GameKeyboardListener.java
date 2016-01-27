package gamePackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameKeyboardListener implements KeyListener {
	
	private ArrayList<Key> keyEventBuffer = new ArrayList<>();
	private ArrayList<Integer> keysDown = new ArrayList<>();
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		addToBuffer(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		addToBuffer(e.getKeyCode(), false);
	}

	private void addToBuffer(int keyCode, boolean event) {
		keyEventBuffer.add(new Key(keyCode, event));
		if (event) {
			boolean hasKey = false;
			for (int i = 0; i < keysDown.size(); i++) {
				if (keysDown.get(i) == keyCode) {
					hasKey = true;
					break;
				}
			}
			if (!hasKey) {
				keysDown.add(keyCode);
			}
			
		}
		else {
			for (int i = 0; i < keysDown.size(); i++) {
				if (keysDown.get(i) == keyCode) {
					keysDown.remove(i);
					i--;
				}
			}
		}
	}
	
	
	public String getBuffer() {
		String s = "";
		for (int i: keysDown) {
			s = s.concat(KeyEvent.getKeyText(i) + ",");
		}
		return s;
	}
	
	public boolean getKey(int keyCode) {
		for (int k: keysDown) {
			if (k == keyCode) {
				return true;
			}
		}
		return false;
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
