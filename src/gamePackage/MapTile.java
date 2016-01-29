package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MapTile {
	
	private MapSquare[][] mapSquares = new MapSquare[GLOBAL.MAP_PIXEL_SIZE][GLOBAL.MAP_PIXEL_SIZE];
	
	
	public MapTile(BufferedImage map) {
		for (int i = 0; i < GLOBAL.MAP_PIXEL_SIZE; i++) {
			for (int j = 0; j < GLOBAL.MAP_PIXEL_SIZE; j++) {
				mapSquares[i][j] = new MapSquare(map.getRGB(i, j));
			}
		}
	}
	
	BufferedImage getImage(Dimension screenSize) {
		BufferedImage image = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
		Graphics imageG = image.getGraphics();
		MapSquare.paint(mapSquares, imageG, screenSize);
		imageG.dispose();
		return image;
	}
	
	public int checkCoins(double x, double y) {
		int numberOfCoins = 0;
		if (mapSquares[(int) (x)][(int) (y)].getCoin()) {
			mapSquares[(int) (x)][(int) (y)].setFloor();
			numberOfCoins++;
		}
		if (mapSquares[(int) (x + GLOBAL.PLAYER_SIZE)][(int) (y)].getCoin()) {
			mapSquares[(int) (x + GLOBAL.PLAYER_SIZE)][(int) (y)].setFloor();
			numberOfCoins++;
		}
		if (mapSquares[(int) (x)][(int) (y + GLOBAL.PLAYER_SIZE)].getCoin()) {
			mapSquares[(int) (x)][(int) (y + GLOBAL.PLAYER_SIZE)].setFloor();
			numberOfCoins++;
		}
		if (mapSquares[(int) (x + GLOBAL.PLAYER_SIZE)][(int) (y + GLOBAL.PLAYER_SIZE)].getCoin()) {
			mapSquares[(int) (x + GLOBAL.PLAYER_SIZE)][(int) (y + GLOBAL.PLAYER_SIZE)].setFloor();
			numberOfCoins++;
		}
		return numberOfCoins;
	}

	public MapSquare[][] getMapSquares() {
		return mapSquares;
	}

	public void paint(Graphics g, Dimension screenSize) {
		g.drawImage(getImage(screenSize), 0, 0, null);
	}
	
	
}
