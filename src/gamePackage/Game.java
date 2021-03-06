package gamePackage;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyListener;

import animationPackage.DeathAnimation;
import animationPackage.EndAnimation;
import animationPackage.MapChangeAnimation;
import keyInputPackage.GameKeyboardListener;
import mapPackage.Map;
import settingsPackage.GLOBAL;
import settingsPackage.OptionsMenu;

public class Game {
	
	private GameKeyboardListener keyListener = new GameKeyboardListener();
	
	private Thread gameThread;
	private MapChangeAnimation mapChangeAnimation;
	private DeathAnimation deathAnimation;
	private EndAnimation endAnimation;
	private Player player;
	private Map map;
	private State state;
	private OptionsMenu optionsMenu;
	private boolean paused;
	private boolean showingOptions;
	
	private static enum State {
		NORMAL, CHANGING_MAP, DEATH, END
	}
	
	public Game() {
		player = new Player();
		map = new Map();
		state = State.NORMAL;
		paused = false;
		showingOptions = false;
		optionsMenu = new OptionsMenu();
	}
	
	private void move() {
		if (keyListener.getEscape()) {
			System.exit(0);
		}
		if (paused) {
			if (showingOptions) {
				if (optionsMenu.wasClosed()) {
					showingOptions = false;
					paused = false;
				}
			}
			else if (keyListener.getP()) {
				paused = false;
			}
			return;
		}
		if (keyListener.getP()) {
			paused = true;
		}
		if (keyListener.getO()) {
			paused = true;
			showingOptions = true;
			optionsMenu.openOptionsMenu();
		}
		
		switch (state) {
		case NORMAL:
			map.move();
			if (map.checkJumpSquare(player.getPosition())) {
				player.tinyJump();
			}
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
			
			if (map.checkDeath(player.getPosition())) {
				
				deathAnimation = new DeathAnimation(
						map.getCurrentMapImage(), map.getDeathCause(), player.getPosition());
				
				player.kill();
				keyListener.reset();
				state = State.DEATH;
			}
			if (player.getCoins() >= GLOBAL.MAX_COINS) {
				endAnimation = new EndAnimation(player.getDeaths());
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
	
	public void paint(Graphics2D g2d) {
		switch (state) {
		case NORMAL:
			map.paint(g2d);
			player.paint(g2d);
			if (GLOBAL.DEBUG_MODE) {
				g2d.drawString(player.getPosition().toString(), 50, 50);
				g2d.drawString(map.getMapSquares()[player.getPosition().getX()][player.getPosition().getY()].toString(), 50, 70);
				g2d.drawString(keyListener.toString(), 50, 30);
			}
			g2d.drawString("O for options: P for Pause", GLOBAL.screenOptionsWidth, 10);
			break;
		case CHANGING_MAP:
			mapChangeAnimation.paint(g2d);
			break;
		case DEATH:
			deathAnimation.paint(g2d);
			break;
		case END:
			endAnimation.paint(g2d);
			break;
		}
		if (paused) {
			if (showingOptions) {
				optionsMenu.paintOptions(g2d);
			}
			else {
				optionsMenu.paintPause(g2d);
			}
		}
		
		
	}
	
	public void start() {
		gameThread = new Thread("moveThread") {
		 	@Override
			public void run() {
	 			long startTime = System.currentTimeMillis() + GLOBAL.MOVE_WAIT_MS;
	 			while (!isInterrupted()) {
	 				move();
	 				while (System.currentTimeMillis() < startTime) {
	 					try {
	 						sleep(1);
	 					} catch (InterruptedException e) {
	 						return;
	 					}
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

	public void stop() {
		gameThread.interrupt();
	}
}
