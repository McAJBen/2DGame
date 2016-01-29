package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game {
	
	private Dimension screenSize;
	private GameKeyboardListener keyListener = new GameKeyboardListener();
	
	private MapChangeAnimation mapChangeAnimation;
	private Player player;
	private Map map;
	private State state;
	
	private static enum State {
		NORMAL, CHANGING_MAP
	}
	
	public Game() {
		player = new Player();
		map = new Map();
		state = State.NORMAL;
	}
	
	private void move() {
		switch (state) {
		case NORMAL:
			byte x = 0;
			byte y = 0;
			if (keyListener.getKey(KeyEvent.VK_LEFT) || keyListener.getKey(KeyEvent.VK_A)) {
				x--;
			}
			if (keyListener.getKey(KeyEvent.VK_RIGHT) || keyListener.getKey(KeyEvent.VK_D)) {
				x++;
			}
			if (keyListener.getKey(KeyEvent.VK_DOWN) || keyListener.getKey(KeyEvent.VK_S)) {
				y++;
			}
			if (keyListener.getKey(KeyEvent.VK_UP) || keyListener.getKey(KeyEvent.VK_W)) {
				y--;
			}
			
			if (player.move(x, y, map, screenSize)) {
				
				Point mapChangeTo = player.getMapChangeTo();
				if (map.checkValidMap(mapChangeTo)) {
					System.out.println(mapChangeTo);
					mapChangeAnimation = new MapChangeAnimation(
							mapChangeTo,
							map.getCurrentMap(screenSize),
							map.getNextMap(mapChangeTo, screenSize),
							player.getX(), player.getY());
					state = State.CHANGING_MAP;
					player.changeMap(true);
				}
				else {
					player.changeMap(false);
				}
			}
			break;
		case CHANGING_MAP:
			if (mapChangeAnimation.move()) {
				map.loadMap();
				state = State.NORMAL;
			}
			break;
		}
	}
	
	
	public void paint(Graphics g) {
		switch (state) {
		case NORMAL:
			map.paint(g, screenSize);
			player.paint(g, screenSize);
			
			break;
		case CHANGING_MAP:
			mapChangeAnimation.paint(g, screenSize);
			break;
		
		}
	}
	
	public void start() {
		Thread gameThread = new Thread("moveThread") {
		 	@Override
			public void run() {
	 			long startTime = System.currentTimeMillis() + GLOBAL.MOVE_WAIT_MS;
	 			while (!isInterrupted()) {
	 				move();
	 				while (System.currentTimeMillis() < startTime) {
	 					try {
	 						sleep(1);
	 					} catch (InterruptedException e) {}
	 				}
	 				startTime += GLOBAL.MOVE_WAIT_MS;
				}
			}
		};
		gameThread.start();
	}
	
	public KeyListener getKeyListener() {
		return keyListener;
	}

	public void changeWindowSize(Dimension size) {
		this.screenSize = size;
	}
}
