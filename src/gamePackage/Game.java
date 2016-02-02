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
	private DeathAnimation deathAnimation;
	private EndAnimation endAnimation;
	private Player player;
	private Map map;
	private State state;
	
	private static enum State {
		NORMAL, CHANGING_MAP, DEATH, END
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
			if (player.move(keyListener.getX(), keyListener.getY(), keyListener.getJump(), map.getMapSquares())) { // if changing map
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
			else {
				player.addCoins(map.checkCoins(player.getX(), player.getY()));
			}
			
			if (map.checkEnemy(player.getPosition())) {
				player.kill();
				keyListener.reset();
				
				deathAnimation = new DeathAnimation(
						map.getCurrentMap(screenSize, width, height));
				state = State.DEATH;
			}
			if (player.getCoins() >= 50) {
				endAnimation = new EndAnimation();
				state = State.END;
				player.completeReset();
				map.completeReset();
			}
			
			break;
		case CHANGING_MAP:
			if (mapChangeAnimation.move()) {
				state = State.NORMAL;
			}
			break;
		case DEATH:
			if (deathAnimation.move()) {
				state = State.NORMAL;
			}
			break;
		case END:
			if (endAnimation.move()) {
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
		case DEATH:
			deathAnimation.paint(g, screenSize);
			break;
		case END:
			endAnimation.paint(g, screenSize);
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
