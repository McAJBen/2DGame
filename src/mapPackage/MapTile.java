package mapPackage;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import animationPackage.DeathAnimation.Death;
import gamePackage.Position;
import settingsPackage.GLOBAL;

public class MapTile {
	
	private MapSquare[][] mapSquares;
	Enemy[] enemys;
	JumpSquare[] jumpSquares;
	ElectricShot[] electricShots;
	Coin[] coins;
	Death deathCause;
	
	public MapTile(BufferedImage map) {
		mapSquares = new MapSquare[GLOBAL.MAP_PIXEL_SIZE][GLOBAL.MAP_PIXEL_SIZE];
		ArrayList<Enemy> enemyPoints = new ArrayList<>();
		ArrayList<Point> jumpSquarePoints = new ArrayList<>();
		ArrayList<Coin> coins = new ArrayList<>();
		ArrayList<Point> electricShotBasePoints = new ArrayList<>();
		
		for (int i = 0; i < GLOBAL.MAP_PIXEL_SIZE; i++) {
			for (int j = 0; j < GLOBAL.MAP_PIXEL_SIZE; j++) {
				mapSquares[i][j] = new MapSquare(map.getRGB(i, j));
				if (mapSquares[i][j].isEnemy()) {
					enemyPoints.add(new Enemy(i, j, map.getRGB(i, j)));
					mapSquares[i][j].setFloor();
				}
				else if (mapSquares[i][j].isJumpSquare()) {
					jumpSquarePoints.add(new Point(i, j));
					mapSquares[i][j].setFloor();
				}
				else if (mapSquares[i][j].isCoin()) {
					coins.add(new Coin(i, j));
					mapSquares[i][j].setFloor();
				}
				else if (mapSquares[i][j].isElectricShot()) {
					electricShotBasePoints.add(new Point(i, j));
				}
			}
		}
		enemys = new Enemy[enemyPoints.size()];
		for (int i = 0; i < enemys.length; i++) {
			enemys[i] = enemyPoints.get(i);
			
		}
		jumpSquares = new JumpSquare[jumpSquarePoints.size()];
		for (int i = 0; i < jumpSquares.length; i++) {
			jumpSquares[i] = new JumpSquare(jumpSquarePoints.get(i));
		}
		electricShots = ElectricShot.setup(electricShotBasePoints, mapSquares);
		this.coins = Coin.setup(coins);
	}
	
	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(GLOBAL.screenSize.width, GLOBAL.screenSize.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D ig2d = image.createGraphics();
		
		MapSquare.paint(mapSquares, ig2d);
		JumpSquare.paint(ig2d, jumpSquares);
		Coin.paint(ig2d, coins);
		Enemy.paint(ig2d, enemys);
		ElectricShot.paint(ig2d, electricShots);
		
		ig2d.dispose();
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
		if (ElectricShot.checkDeath(position, electricShots)) {
			deathCause = Death.ELECTRIC;
			return true;
		}
		if (Enemy.checkDeath(position, enemys)) {
			deathCause = Death.ENEMY;
			return true;
		}
		return false;
	}
	
	private boolean checkDeath(int x, int y) {
		return (mapSquares[x][y].isElectric());
	}

	public int checkCoins(Position position) {
		return Coin.checkCoins(position, coins);
	}

	public MapSquare[][] getMapSquares() {
		return mapSquares;
	}

	public void paint(Graphics2D g2d) {
		g2d.drawImage(getImage(), 0, 0, null);
	}

	public void move() {
		Enemy.move();
		ElectricShot.move();
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

	public int getNumberOfCoins() {
		return coins.length;
	}
}
