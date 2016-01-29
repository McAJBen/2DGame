package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {
	
	private MapTile[][] mapTile = new MapTile[GLOBAL.MAP_SIZE.width][GLOBAL.MAP_SIZE.height];
	private Point currentMap;
		
	public Map() {
		currentMap = new Point(0, 0);
		for (int i = 0; i < GLOBAL.MAP_SIZE.width; i++) {
			for (int j = 0; j < GLOBAL.MAP_SIZE.height; j++) {
	    		try {
	    			BufferedImage map = ImageIO.read(getClass().getResource("/Maps/" + i + "," + j + ".png"));
	    			mapTile[i][j] = new MapTile(map);
	    		} catch (IOException e) {}
			}
		}
	}

	public void paint(Graphics g, Dimension screenSize) {
		mapTile[currentMap.x][currentMap.y].paint(g, screenSize);
	}

	public Point getCurrentMapPoint() {
		return currentMap;
	}
	
	public BufferedImage getCurrentMap(Dimension screenSize) {
		BufferedImage curMap = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
		Graphics mapG = curMap.getGraphics();
		
		paint(mapG, screenSize);
		mapG.dispose();
		
		return curMap;
	}
	
	public BufferedImage getNextMap(Point newMap, Dimension screenSize) {
		currentMap.translate(newMap.x, newMap.y);
		
		return mapTile[currentMap.x][currentMap.y].getImage(screenSize);
	}

	public boolean checkValidMap(Point mapChangeTo) {
		return 
			0 <= currentMap.x + mapChangeTo.x && currentMap.x + mapChangeTo.x < GLOBAL.MAP_SIZE.width &&
			0 <= currentMap.y + mapChangeTo.y && currentMap.y + mapChangeTo.y < GLOBAL.MAP_SIZE.height;
	}

	public MapSquare[][] getMapSquares() {
		return mapTile[currentMap.x][currentMap.y].getMapSquares();
	}

	public int checkCoins(double x, double y) {
		return mapTile[currentMap.x][currentMap.y].checkCoins(x, y);
	}
}
