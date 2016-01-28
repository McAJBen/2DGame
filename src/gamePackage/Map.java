package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {
	
	private BufferedImage[][] map = new BufferedImage[GLOBAL.MAP_SIZE.width][GLOBAL.MAP_SIZE.height];
	private MapSquare[][] mapSquares= new MapSquare[GLOBAL.MAP_PIXEL_SIZE][GLOBAL.MAP_PIXEL_SIZE];
	private Point currentMap;
		
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

	public void paint(Graphics g, Dimension screenSize) {
		g.drawImage(map[currentMap.x][currentMap.y], 0, 0, screenSize.width, screenSize.height, null);
	
		//MapSquare.paint(mapSquares, g, screenSize);
	}

	public Point getCurrentMapPoint() {
		return currentMap;
	}
	
	public BufferedImage getCurrentMap() {
		return map[currentMap.x][currentMap.y];
	}
	
	public BufferedImage getNextMap(Point newMap) {
		currentMap.translate(newMap.x, newMap.y);
		return getCurrentMap();
	}

	public boolean checkValidMap(Point mapChangeTo) {
		return 
			0 <= currentMap.x + mapChangeTo.x && currentMap.x + mapChangeTo.x < GLOBAL.MAP_SIZE.width &&
			0 <= currentMap.y + mapChangeTo.y && currentMap.y + mapChangeTo.y < GLOBAL.MAP_SIZE.height;
	}

	public void loadMap() {
		Thread mapLoader = new Thread("MapLoad") {
			@Override
			public void run() {
				for (int i = 0; i < GLOBAL.MAP_PIXEL_SIZE; i++) {
					for (int j = 0; j < GLOBAL.MAP_PIXEL_SIZE; j++) {
						mapSquares[i][j] = new MapSquare(getCurrentMap().getRGB(i, j));
					}
				}
				super.run();
			}
		};
		mapLoader.start();
	}

	public MapSquare[][] getMapSquares() {
		return mapSquares;
	}
}
