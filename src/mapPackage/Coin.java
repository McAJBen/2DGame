package mapPackage;

import java.awt.Graphics;
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
	
	public Coin(int x, int y) {
		point = new Point(x, y);
	}
	
	public static int checkCoins(Position pos, ArrayList<Coin> coins) {
		int coinCounter = 0;
		for (int i = 0; i < coins.size(); i++) {
			if (coins.get(i).checkCoin(pos)) {
				coinCounter++;
				coins.remove(i);
				i--;
			}
		}
		return coinCounter;
	}

	public boolean checkCoin(Position pos) {
		return 	within(pos.getX(), pos.getY()) ||
				within(pos.getXMaxPlayer(), pos.getY()) ||
				within(pos.getX(), pos.getYMaxPlayer()) ||
				within(pos.getXMaxPlayer(), pos.getYMaxPlayer());
	}
	
	private boolean within(int px, int py) {
		return 	point.x == px && point.y == py;
	}
	
	public void paint(Graphics g) {
		g.drawImage(coin,
				(int)(point.x * GLOBAL.screenPixelWidth),
				(int)(point.y * GLOBAL.screenPixelHeight),
				(int)GLOBAL.screenPixelWidth,
				(int)GLOBAL.screenPixelHeight,
				null);
	}
	
	public static void load() {
		try {
			coin = ImageIO.read(DeathAnimation.class.getResource("/Coin.png"));
		} catch (IOException e) {}
	}

	public static void paintCoin(Graphics g, int x, int y) {
		g.drawImage(coin,
				(int)(x * GLOBAL.screenPixelWidth),
				(int)(y * GLOBAL.screenPixelHeight),
				(int)GLOBAL.screenPixelWidth,
				(int)GLOBAL.screenPixelHeight,
				null);
		
	}

	public static void paint(Graphics imageG, ArrayList<Coin> coins) {
		for (Coin c: coins) {
			c.paint(imageG);
		}
	}
}
