package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
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
	    			ClassLoader classLoader = getClass().getClassLoader();
	    			File file = new File(classLoader.getResource("Maps/" + i + "," + j + ".png").getFile());
	    			map[i][j] = ImageIO.read(file);
	    		} catch (IOException e) {
	    			System.out.println("ERROR: Could not read file" + i + "," + j);
	    		}
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

	public boolean getWall(double x, double y, double playerWidth, double playerHeight) {
		if (x + playerWidth >= 1 || y + playerHeight >= 1 || x < 0 || y < 0) {
			return false;
		}
		if (mapSquares[(int) (x * GLOBAL.MAP_PIXEL_SIZE)]
				[(int) (y * GLOBAL.MAP_PIXEL_SIZE)].getWall()) {
			return true;
		}
		else if (mapSquares[(int) ((x + playerWidth) * GLOBAL.MAP_PIXEL_SIZE)]
				[(int) (y * GLOBAL.MAP_PIXEL_SIZE)].getWall()) {
			return true;
		}
		else if (mapSquares[(int) ((x + playerWidth) * GLOBAL.MAP_PIXEL_SIZE)]
				[(int) ((y + playerHeight) * GLOBAL.MAP_PIXEL_SIZE)].getWall()) {
			return true;
		}
		else if (mapSquares[(int) (x * GLOBAL.MAP_PIXEL_SIZE)]
				[(int) ((y + playerHeight) * GLOBAL.MAP_PIXEL_SIZE)].getWall()) {
			return true;
		}
		return false;
		
	}
}
