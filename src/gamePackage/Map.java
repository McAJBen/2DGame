package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {

	
	private BufferedImage map;
	private Point currentMap;
		
	public Map() {
		currentMap = new Point(0, 0);
		
		map = null;
		do {
    		try {
    			ClassLoader classLoader = getClass().getClassLoader();
    			File file = new File(classLoader.getResource("Maps/map.png").getFile());
    			map = ImageIO.read(file);
    		} catch (IOException e) {
    			System.out.println("ERROR: Could not read file");
    		}
    	} while (map == null);
	}
	
	public void changeMap(Point newMap) {
		currentMap.translate(newMap.x, newMap.y);
	}

	public void paint(Graphics g, Dimension screenSize) {
		g.drawImage(map, 0, 0, screenSize.width, screenSize.height, null);
	}

	public Point getCurrentMapPoint() {
		return currentMap;
	}
	
	public BufferedImage getCurrentMap() {
		return map;
	}
	
	public BufferedImage getNextMap() {
		return map;
	}
}
