package gamePackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MapTile {
	
	private MapSquare[][] mapSquares;
	ArrayList<Enemy> enemys;
	
	public MapTile(BufferedImage map) {
		mapSquares = new MapSquare[GLOBAL.MAP_PIXEL_SIZE][GLOBAL.MAP_PIXEL_SIZE];
		enemys = new ArrayList<>();
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
	
	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(GLOBAL.screenSize.width, GLOBAL.screenSize.height, BufferedImage.TYPE_INT_ARGB);
		Graphics imageG = image.getGraphics();
		MapSquare.paint(mapSquares, imageG);
		for (Enemy e: enemys) {
			e.paint(imageG);
		}
		imageG.dispose();
		return image;
	}
	
	public boolean checkDeath(Position position) {
		for (Enemy e: enemys) {
			if (e.checkEnemy(position)) {
				return true;
			}
		}
		return 	checkDeath(position.getX(), 		 position.getY()) ||
				checkDeath(position.getXMaxPlayer(), position.getY()) ||
				checkDeath(position.getX(), 		 position.getYMaxPlayer()) ||
				checkDeath(position.getXMaxPlayer(), position.getYMaxPlayer());
	}
	
	private boolean checkDeath(int x, int y) {
		return (mapSquares[x][y].isElectric());
	}

	public int checkCoins(Position position) {
		return 	checkCoins(position.getX(), 		 position.getY()) +
				checkCoins(position.getXMaxPlayer(), position.getY()) +
				checkCoins(position.getX(), 		 position.getYMaxPlayer()) +
				checkCoins(position.getXMaxPlayer(), position.getYMaxPlayer());
	}
	
	private int checkCoins(int x, int y) {
		if (mapSquares[x][y].isCoin()) {
			mapSquares[x][y].setFloor();
			return 1;
		}
		return 0;
	}

	public MapSquare[][] getMapSquares() {
		return mapSquares;
	}

	public void paint(Graphics g) {
		g.drawImage(getImage(), 0, 0, null);
	}

	public void move(MapSquare[][] mapSquares) {
		for (Enemy e: enemys) {
			e.move(mapSquares);
		}
	}
	
	public boolean checkJumpWall(Position position) {
		return 	checkJumpWall(position.getX(), 		 position.getY()) ||
				checkJumpWall(position.getXMaxPlayer(), position.getY()) ||
				checkJumpWall(position.getX(), 		 position.getYMaxPlayer()) ||
				checkJumpWall(position.getXMaxPlayer(), position.getYMaxPlayer());
	}
	
	private boolean checkJumpWall(int x, int y) {
		return mapSquares[x][y].isJumpWall();
	}
}
