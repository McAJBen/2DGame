package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MapTile {
	
	private MapSquare[][] mapSquares = new MapSquare[GLOBAL.MAP_PIXEL_SIZE][GLOBAL.MAP_PIXEL_SIZE];
	ArrayList<Enemy> enemys = new ArrayList<>();
	
	
	public MapTile(BufferedImage map) {
		for (int i = 0; i < GLOBAL.MAP_PIXEL_SIZE; i++) {
			for (int j = 0; j < GLOBAL.MAP_PIXEL_SIZE; j++) {
				mapSquares[i][j] = new MapSquare(map.getRGB(i, j));
				if (mapSquares[i][j].isEnemy()) {
					enemys.add(new Enemy(i, j, map.getRGB(i, j)));
					mapSquares[i][j].setFloor();
				}
			}
		}
	}
	
	BufferedImage getImage(Dimension screenSize, double width, double height) {
		BufferedImage image = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
		Graphics imageG = image.getGraphics();
		MapSquare.paint(mapSquares, imageG, screenSize, width, height);
		imageG.dispose();
		return image;
	}
	
	public boolean checkEnemy(Position position) {
		
		for (Enemy e: enemys) {
			if (e.checkEnemy(position)) {
				return true;
			}
		}
		return false;
	}
	
	public int checkCoins(double x, double y) {
		int numberOfCoins = 0;
		if (mapSquares[(int) (x)][(int) (y)].isCoin()) {
			mapSquares[(int) (x)][(int) (y)].setFloor();
			numberOfCoins++;
		}
		if (mapSquares[(int) (x + GLOBAL.PLAYER_SIZE_DOUBLE)][(int) (y)].isCoin()) {
			mapSquares[(int) (x + GLOBAL.PLAYER_SIZE_DOUBLE)][(int) (y)].setFloor();
			numberOfCoins++;
		}
		if (mapSquares[(int) (x)][(int) (y + GLOBAL.PLAYER_SIZE_DOUBLE)].isCoin()) {
			mapSquares[(int) (x)][(int) (y + GLOBAL.PLAYER_SIZE_DOUBLE)].setFloor();
			numberOfCoins++;
		}
		if (mapSquares[(int) (x + GLOBAL.PLAYER_SIZE_DOUBLE)][(int) (y + GLOBAL.PLAYER_SIZE_DOUBLE)].isCoin()) {
			mapSquares[(int) (x + GLOBAL.PLAYER_SIZE_DOUBLE)][(int) (y + GLOBAL.PLAYER_SIZE_DOUBLE)].setFloor();
			numberOfCoins++;
		}
		return numberOfCoins;
	}

	public MapSquare[][] getMapSquares() {
		return mapSquares;
	}

	public void paint(Graphics g, Dimension screenSize, double width, double height) {
		g.drawImage(getImage(screenSize, width, height), 0, 0, null);
		for (Enemy e: enemys) {
			e.paint(g, width, height, screenSize);
		}
	}

	public void move(MapSquare[][] mapSquares) {
		for (Enemy e: enemys) {
			e.move(mapSquares);
		}
	}
}
