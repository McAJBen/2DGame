package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyListener;

public class Game {
	
	private Dimension screenSize;
	private double width;
	private double height;
	
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
			
			map.move();
			if (player.move(keyListener.getX(), keyListener.getY(), map.getMapSquares())) { // if changing map
				Point mapChangeTo = player.getMapChangeTo();
				if (map.checkValidMap(mapChangeTo)) {
					
					mapChangeAnimation = new MapChangeAnimation(
							mapChangeTo,
							map.getCurrentMap(screenSize, width, height),
							map.getNextMap(mapChangeTo, screenSize),
							player.getX(), player.getY());
					
					state = State.CHANGING_MAP;
					try {
						Thread.sleep(GLOBAL.FRAME_WAIT_MS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					map.changeMap(mapChangeTo);
					player.changeMap(true);
				}
				else {
					player.changeMap(false);
				}
			}
			player.addCoins(map.checkCoins(player.getX(), player.getY()));
			if (map.checkEnemy(player.getX(), player.getY())) {
				player.kill();
				keyListener.reset();
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
			map.paint(g, screenSize, width, height);
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
		width = (double)screenSize.width / GLOBAL.MAP_PIXEL_SIZE;
		height = (double)screenSize.height / GLOBAL.MAP_PIXEL_SIZE;
	}
}
