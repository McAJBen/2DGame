package MapPackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import animationPackage.DeathAnimation.Death;
import gamePackage.GLOBAL;
import gamePackage.Position;

public class MapTile {
	
	private MapSquare[][] mapSquares;
	ArrayList<Enemy> enemys;
	ArrayList<JumpSquare> jumpSquares;
	Death deathCause;
	
	public MapTile(BufferedImage map) {
		mapSquares = new MapSquare[GLOBAL.MAP_PIXEL_SIZE][GLOBAL.MAP_PIXEL_SIZE];
		enemys = new ArrayList<>();
		jumpSquares = new ArrayList<>();
		for (int i = 0; i < GLOBAL.MAP_PIXEL_SIZE; i++) {
			for (int j = 0; j < GLOBAL.MAP_PIXEL_SIZE; j++) {
				mapSquares[i][j] = new MapSquare(map.getRGB(i, j));
				if (mapSquares[i][j].isEnemy()) {
					enemys.add(new Enemy(i, j, map.getRGB(i, j)));
					mapSquares[i][j].setFloor();
				}
				else if (mapSquares[i][j].isJumpSquare()) {
					jumpSquares.add(new JumpSquare(i, j));
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
		for (JumpSquare js: jumpSquares) {
			js.paint(imageG);
		}
		imageG.dispose();
		return image;
	}
	
	public boolean checkDeath(Position position) {
		if (checkDeath(position.getX(), 		 position.getY()) ||
			checkDeath(position.getXMaxPlayer(), position.getY()) ||
			checkDeath(position.getX(), 		 position.getYMaxPlayer()) ||
			checkDeath(position.getXMaxPlayer(), position.getYMaxPlayer())) {
				deathCause = Death.ELECTRIC;
				return true;
				
		}
		for (Enemy e: enemys) {
			if (e.checkEnemy(position)) {
				deathCause = Death.ENEMY;
				return true;
			}
		}
		return false;
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
		Enemy.move();
		for (Enemy e: enemys) {
			e.move(mapSquares);
		}
		for (JumpSquare js: jumpSquares) {
			js.move();
		}
	}
	
	public boolean checkJumpSquare(Position position) {
		for (JumpSquare js: jumpSquares) {
			if (js.checkJumpSquare(position)) {
				return true;
			}
		}
		return false;
	}

	public Death getDeathCause() {
		return deathCause;
	}
}
