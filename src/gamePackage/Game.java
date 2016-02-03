package gamePackage;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyListener;

import animationPackage.DeathAnimation;
import animationPackage.EndAnimation;
import animationPackage.MapChangeAnimation;

public class Game {
	
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
			if (player.move(keyListener.getX(), keyListener.getJump(), map.getMapSquares())) { // if changing map
				Point mapChangeTo = player.getMapChangeTo();
				if (map.checkValidMap(mapChangeTo)) {
					
					mapChangeAnimation = new MapChangeAnimation(
							mapChangeTo,
							map.getCurrentMapImage(),
							map.getNextMap(mapChangeTo),
							player.getPosition());
					
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
				player.addCoins(map.checkCoins(player.getPosition()));
			}
			if (map.checkJumpWall(player.getPosition())) {
				player.jump();
			}
			
			if (map.checkEnemy(player.getPosition())) {
				player.kill();
				keyListener.reset();
				
				deathAnimation = new DeathAnimation(
						map.getCurrentMapImage());
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
			map.paint(g);
			player.paint(g);
			break;
		case CHANGING_MAP:
			mapChangeAnimation.paint(g);
			break;
		case DEATH:
			deathAnimation.paint(g);
			break;
		case END:
			endAnimation.paint(g);
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
}
