package gamePackage;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {
	
	private MapTile[][] mapTile = new MapTile[GLOBAL.MAP_SIZE.width][GLOBAL.MAP_SIZE.height];
	private Point currentMap;
		
	public Map() {
		completeReset();
	}

	public void paint(Graphics g) {
		getCurrentMap().paint(g);
	}

	public Point getCurrentMapPoint() {
		return currentMap;
	}
	
	public BufferedImage getCurrentMapImage() {
		return getCurrentMap().getImage();
	}
	
	public BufferedImage getNextMap(Point mapChangeTo) {
		return mapTile[mapChangeTo.x + currentMap.x][mapChangeTo.y + currentMap.y].getImage();
	}

	public boolean checkValidMap(Point mapChangeTo) {
		return 
			0 <= currentMap.x + mapChangeTo.x && currentMap.x + mapChangeTo.x < GLOBAL.MAP_SIZE.width &&
			0 <= currentMap.y + mapChangeTo.y && currentMap.y + mapChangeTo.y < GLOBAL.MAP_SIZE.height;
	}

	public MapSquare[][] getMapSquares() {
		return getCurrentMap().getMapSquares();
	}

	public int checkCoins(Position position) {
		return getCurrentMap().checkCoins(position);
	}

	public void changeMap(Point mapChangeTo) {
		currentMap.translate(mapChangeTo.x, mapChangeTo.y);
	}

	public boolean checkDeath(Position position) {
		return getCurrentMap().checkDeath(position);
	}

	public void move() {
		getCurrentMap().move(getMapSquares());
	}

	public void completeReset() {
		BufferedImage map = null;
		try {
			map = ImageIO.read(getClass().getResource("/Map.png"));
		} catch (IOException e) {}
		
		currentMap = GLOBAL.MAP_START.getLocation();
		for (int i = 0; i < GLOBAL.MAP_SIZE.width; i++) {
			for (int j = 0; j < GLOBAL.MAP_SIZE.height; j++) {
				
				BufferedImage mapBlock = new BufferedImage(GLOBAL.MAP_PIXEL_SIZE, GLOBAL.MAP_PIXEL_SIZE, BufferedImage.TYPE_INT_ARGB);
				
				mapBlock.getGraphics().drawImage(map, -i * GLOBAL.MAP_PIXEL_SIZE, -j * GLOBAL.MAP_PIXEL_SIZE, null);
				
	    		mapTile[i][j] = new MapTile(mapBlock);
			}
		}
	}
	
	private MapTile getCurrentMap() {
		return mapTile[currentMap.x][currentMap.y];
	}

	public boolean checkJumpWall(Position position) {
		return getCurrentMap().checkJumpWall(position);
	}
}
