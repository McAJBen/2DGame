package mapPackage;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import animationPackage.DeathAnimation;
import gamePackage.Position;
import settingsPackage.GLOBAL;

public class Coin {
	
	private static BufferedImage coin;
	private Point point;
	private boolean enabled;
	
	public Coin(int x, int y) {
		enabled = true;
		point = new Point(x, y);
	}
	
	public static int checkCoins(Position pos, Coin[] coins) {
		int coinCounter = 0;
		for (int i = 0; i < coins.length; i++) {
			if (coins[i].checkCoin(pos)) {
				coinCounter++;
				coins[i].disable();
			}
		}
		return coinCounter;
	}

	private void disable() {
		enabled = false;
		
	}

	public boolean checkCoin(Position pos) {
		return 	within(pos.getX(), pos.getY()) ||
				within(pos.getXMaxPlayer(), pos.getY()) ||
				within(pos.getX(), pos.getYMaxPlayer()) ||
				within(pos.getXMaxPlayer(), pos.getYMaxPlayer());
	}
	
	private boolean within(int px, int py) {
		if (enabled) {
			return 	point.x == px && point.y == py;
		}
		else {
			return false;
		}
	}
	
	public void paint(Graphics2D g2d) {
		if (enabled) {
			g2d.drawImage(coin,
				(int)(point.x * GLOBAL.screenPixelWidth),
				(int)(point.y * GLOBAL.screenPixelHeight),
				(int)GLOBAL.screenPixelWidth,
				(int)GLOBAL.screenPixelHeight,
				null);
		}
		
	}
	
	public static void load() {
		try {
			coin = ImageIO.read(DeathAnimation.class.getResource("/Coin.png"));
		} catch (IOException e) {}
	}

	public static void paintCoin(Graphics2D g2d, int x, int y) {
		g2d.drawImage(coin,
				(int)(x * GLOBAL.screenPixelWidth),
				(int)(y * GLOBAL.screenPixelHeight),
				(int)GLOBAL.screenPixelWidth,
				(int)GLOBAL.screenPixelHeight,
				null);
		
	}

	public static void paint(Graphics2D g2d, Coin[] coins) {
		for (Coin c: coins) {
			c.paint(g2d);
		}
	}

	public static Coin[] setup(ArrayList<Coin> coins) {
		Coin[] c = new Coin[coins.size()];
		for (int i = 0; i < c.length; i++) {
			c[i] = coins.get(i);
		}
		return c;
	}
}
