package gamePackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameKeyboardListener implements KeyListener {
	
	private ArrayList<Key> keyEventBuffer = new ArrayList<>();
	
	@Override
	public void keyPressed(KeyEvent e) {
		keyEventBuffer.add(new Key(e.getKeyCode(), true));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyEventBuffer.add(new Key(e.getKeyCode(), false));
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public String getBuffer() {
		String s = "";
		for (Key k: keyEventBuffer) {
			s = s.concat(k.toString() + ",");
		}
		return s;
	}
}
