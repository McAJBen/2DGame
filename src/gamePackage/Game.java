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
	
	public Game(Dimension screenSize) {
		this.screenSize = screenSize;
		player = new Player();
		map = new Map();
		state = State.NORMAL;
	}
	
	private void move() {
		switch (state) {
		case NORMAL:
			int x = 0;
			int y = 0;
			if (keyListener.getKey(KeyEvent.VK_LEFT)) {
				x--;
			}
			if (keyListener.getKey(KeyEvent.VK_RIGHT)) {
				x++;
			}
			if (keyListener.getKey(KeyEvent.VK_DOWN)) {
				y++;
			}
			if (keyListener.getKey(KeyEvent.VK_UP)) {
				y--;
			}
			player.move(x, y, map, screenSize);
			if (player.changedMap()) {
				
				Point mapChangeTo = player.getMapChangeTo();
				if (map.checkValidMap(mapChangeTo)) {
					
					mapChangeAnimation = new MapChangeAnimation(
							mapChangeTo,
							map.getCurrentMap(),
							map.getNextMap(mapChangeTo),
							player.getX(), player.getY(),
							player.getPlayerSize());
					map.loadMap();
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
		g.drawString("KL:" + keyListener.getBuffer(), 10, 10);
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
