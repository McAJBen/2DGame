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
		BufferedImage map = null;
		try {
			map = ImageIO.read(getClass().getResource("/Map.png"));
		} catch (IOException e) {}
		
		
		
		
		currentMap = GLOBAL.MAP_START;
		for (int i = 0; i < GLOBAL.MAP_SIZE.width; i++) {
			for (int j = 0; j < GLOBAL.MAP_SIZE.height; j++) {
				
				BufferedImage mapBlock = new BufferedImage(GLOBAL.MAP_PIXEL_SIZE, GLOBAL.MAP_PIXEL_SIZE, BufferedImage.TYPE_INT_ARGB);
				
				mapBlock.getGraphics().drawImage(map, -i * GLOBAL.MAP_PIXEL_SIZE, -j * GLOBAL.MAP_PIXEL_SIZE, null);
				
	    		mapTile[i][j] = new MapTile(mapBlock);
			}
		}
	}

	public void paint(Graphics g, Dimension screenSize, double width, double height) {
		mapTile[currentMap.x][currentMap.y].paint(g, screenSize, width, height);
	}

	public Point getCurrentMapPoint() {
		return currentMap;
	}
	
	public BufferedImage getCurrentMap(Dimension screenSize, double width, double height) {
		BufferedImage curMap = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
		Graphics mapG = curMap.getGraphics();
		paint(mapG, screenSize, width, height);
		mapG.dispose();
		return curMap;
	}
	
	public BufferedImage getNextMap(Point mapChangeTo, Dimension screenSize, double width, double height) {
		return mapTile[mapChangeTo.x + currentMap.x][mapChangeTo.y + currentMap.y].getImage(screenSize, width, height);
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

	public void changeMap(Point mapChangeTo) {
		currentMap.translate(mapChangeTo.x, mapChangeTo.y);
	}

	public boolean checkEnemy(Position position) {
		return mapTile[currentMap.x][currentMap.y].checkEnemy(position);
	}

	public void move() {
		mapTile[currentMap.x][currentMap.y].move(getMapSquares());
	}

	public void completeReset() {
		currentMap = GLOBAL.MAP_START;
		for (int i = 0; i < GLOBAL.MAP_SIZE.width; i++) {
			for (int j = 0; j < GLOBAL.MAP_SIZE.height; j++) {
	    		try {
	    			BufferedImage map = ImageIO.read(getClass().getResource("/Maps/" + i + "," + j + ".png"));
	    			mapTile[i][j] = new MapTile(map);
	    		} catch (IOException e) {}
			}
		}
	}
}
