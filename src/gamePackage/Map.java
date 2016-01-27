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
	private Dimension screenSize;
	private Point currentMap;
	
	
	public Map(Dimension screenSize) {
		this.screenSize = screenSize;
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

	public void paint(Graphics g) {
		g.drawImage(map, 0, 0, screenSize.width, screenSize.height, null);
		g.drawString(currentMap.toString(), screenSize.width / 2, screenSize.height / 2);
	}

	public Point getCurrentMap() {
		return currentMap;
	}
	
	public void setScreenSize(Dimension screenSize) {
		this.screenSize = screenSize;
	}
	
}
