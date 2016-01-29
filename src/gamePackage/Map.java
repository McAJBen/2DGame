package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Map {
	
	private BufferedImage[][] map = new BufferedImage[GLOBAL.MAP_SIZE.width][GLOBAL.MAP_SIZE.height];
	private MapSquare[][] mapSquares = new MapSquare[GLOBAL.MAP_PIXEL_SIZE][GLOBAL.MAP_PIXEL_SIZE];
	private Point currentMap;
	private ArrayList<Coin> coins;
		
	public Map() {
		currentMap = new Point(0, 0);
		
		for (int i = 0; i < GLOBAL.MAP_SIZE.width; i++) {
			for (int j = 0; j < GLOBAL.MAP_SIZE.height; j++) {
	    		try {
	    			map[i][j] = ImageIO.read(getClass().getResource("/Maps/" + i + "," + j + ".png"));
	    		} catch (IOException e) {}
			}
		}
		loadMap();
	}
	
	public Map(Point currentMap, BufferedImage[][] map) {
		this.currentMap = currentMap;
		this.map = map;
		loadMap();
	}

	public void paint(Graphics g, Dimension screenSize) {
		//g.drawImage(map[currentMap.x][currentMap.y], 0, 0, screenSize.width, screenSize.height, null);
	
		MapSquare.paint(mapSquares, g, screenSize);
		
		Coin.paint(coins, g, screenSize);
	}

	public Point getCurrentMapPoint() {
		return currentMap;
	}
	private BufferedImage getCurrentMap() {
		return map[currentMap.x][currentMap.y];
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
		Map copyOfMap;
		copyOfMap = (Map) this.clone();
		return copyOfMap.getCurrentMap(screenSize);
	}

	public boolean checkValidMap(Point mapChangeTo) {
		return 
			0 <= currentMap.x + mapChangeTo.x && currentMap.x + mapChangeTo.x < GLOBAL.MAP_SIZE.width &&
			0 <= currentMap.y + mapChangeTo.y && currentMap.y + mapChangeTo.y < GLOBAL.MAP_SIZE.height;
	}

	public void loadMap() {
		coins = new ArrayList<>();
		for (int i = 0; i < GLOBAL.MAP_PIXEL_SIZE; i++) {
			for (int j = 0; j < GLOBAL.MAP_PIXEL_SIZE; j++) {
				mapSquares[i][j] = new MapSquare(getCurrentMap().getRGB(i, j));
				
				switch (mapSquares[i][j].getType()) {
				case COIN:
					spawnCoin(i, j);
					mapSquares[i][j].setFloor();
					
					
					break;
				case FLOOR:
				case WALL:
				default:
					break;
				
				}
			}
		}
	}

	protected void spawnCoin(int i, int j) {
		coins.add(new Coin(i, j));
		
	}

	public MapSquare[][] getMapSquares() {
		return mapSquares;
	}
	
	@Override
	protected Object clone() {
		return new Map(this.currentMap, this.map);
	}
}
