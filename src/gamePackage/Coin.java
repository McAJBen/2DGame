package gamePackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Coin {
	Point position;
	
	public Coin(int i, int j) {
		position = new Point(i, j);
	}
	
	public static void paint(ArrayList<Coin> coins, Graphics g, Dimension screenSize) {
		
		Dimension squareSize = new Dimension(
				screenSize.width / GLOBAL.MAP_PIXEL_SIZE,
				screenSize.height / GLOBAL.MAP_PIXEL_SIZE);
		
		g.setColor(Color.YELLOW);
		for (Coin c: coins) {
				g.fillOval(
					c.position.x * squareSize.width,
					c.position.y * squareSize.height,
					squareSize.width,
					squareSize.height);
		}
		
	}
	
	
}
