package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game {
	
	private static final long MOVE_WAIT_MS = 10; // 100 mps

	private Dimension screenSize;
	private GameKeyboardListener keyListener = new GameKeyboardListener();
	private Player player;
	private Map map;
	
	public Game(Dimension screenSize) {
		this.screenSize = screenSize;
		player = new Player(screenSize);
		map = new Map(screenSize);
	}
	
	private void move() {
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
		player.move(x, y);
		if (player.changedMap()) {
			map.changeMap(player.getMapChangeTo());
		}
		
		
	}
	
	
	public void paint(Graphics g) {
		map.paint(g);
		g.drawString("KL:" + keyListener.getBuffer(), 10, 10);
		g.fillRect(player.getPosition().x, player.getPosition().y, 10, 10);
		
		g.drawRect(0, 0, screenSize.width, screenSize.height);
	}
	
	public void start() {
		Thread gameThread = new Thread("repaintThread") {
		 	@Override
			public void run() {
	 			long startTime = System.currentTimeMillis() + MOVE_WAIT_MS;
	 			while (!isInterrupted()) {
	 				move();
	 				while (System.currentTimeMillis() < startTime) {
	 					try {
	 						sleep(1);
	 					} catch (InterruptedException e) {}
	 				}
	 				startTime += MOVE_WAIT_MS;
				}
			}
		};
		gameThread.start();
	}
	
	public KeyListener getKeyListener() {
		return keyListener;
	}

	public void changeWindowSize(Dimension size) {
		System.out.println(size);
		
		this.screenSize = size;
		player.setBounds(size);
		map.setScreenSize(size);
	}
}
