package gamePackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import mapPackage.Coin;
import mapPackage.MapSquare;
import settingsPackage.GLOBAL;

public class Player {
	
	private static BufferedImage playerImage;
	
	private Position position;
	private Position velocity;
	private Position lastPosition;
	private Point mapChangeTo;
	private int coins;
	private int deaths;
	private byte jumpWait;
	private byte jumpsLeft;
	private byte mapChangeWait;
	
	public Player() {
		completeReset();
	}
	
	public boolean move(int dx, boolean jump, MapSquare[][] mapSquares) {
		if (checkSpeed(mapSquares)) {
			dx *= GLOBAL.PLAYER_SPEED_MULTI;
		}
		
		if (checkFall(mapSquares)) {
			velocity.addYShort(GLOBAL.PLAYER_FALL);
		}
		
		velocity.addXShort(dx * GLOBAL.PLAYER_MOVE_SPEED);
		velocity.addYShort(GLOBAL.PLAYER_GRAV);
		
		if (jump) {
			if (jumpWait == GLOBAL.PLAYER_JUMP_WAIT) {
				jump();
			}
			else if (jumpWait > 0) {
				tinyJump();
			}
			if (jumpWait > 0) {
				jumpWait--;
			}
		}
		else if (jumpsLeft > 1 && jumpWait < GLOBAL.PLAYER_JUMP_WAIT) {
			jumpsLeft--;
			jumpWait = GLOBAL.PLAYER_JUMP_WAIT;
		}
		velocity.PlayerFriction(GLOBAL.PLAYER_FRICTION);
		
		
		
		Position newPosition = new Position(
					position.getXShort() + velocity.getXShort(),
					position.getYShort() + velocity.getYShort());
		if (!canChangeMap()) {
			mapChangeWait++;
			if (newPosition.getXShort() < 0) {
				newPosition.setX(position);
			}
			if (GLOBAL.PLAYER_U_MAX < newPosition.getXShort()) {
				newPosition.setX(position);
			}
			if (newPosition.getYShort() < 0) {
				newPosition.setY(position);
			}
			if (GLOBAL.PLAYER_U_MAX < newPosition.getYShort()) {
				newPosition.setY(position);
			}
		}
		else {
			if (newPosition.getXShort() < 0) {
				mapChangeTo.x = -1;
				position.setX(newPosition);
				return true;
			}
			if (GLOBAL.PLAYER_U_MAX < newPosition.getXShort()) {
				mapChangeTo.x = 1;
				position.setX(newPosition);
				return true;
			}
			if (newPosition.getYShort() < 0) {
				mapChangeTo.y = -1;
				position.setY(newPosition);
				return true;
			}
			if (GLOBAL.PLAYER_U_MAX < newPosition.getYShort()) {
				mapChangeTo.y = 1;
				position.setY(newPosition);
				return true;
			}
		}
		
		movePlayer(newPosition, mapSquares);
		
		return false;
	}
	
	private boolean checkFall(MapSquare[][] mapSquares) {
		if (position.getY() + 1 < GLOBAL.MAP_PIXEL_SIZE) {
			return 	mapSquares[position.getX()][position.getY()].isFall() ||
					mapSquares[position.getX()][position.getYMaxPlayer()].isFall() ||
					mapSquares[position.getXMaxPlayer()][position.getY()].isFall() ||
					mapSquares[position.getXMaxPlayer()][position.getYMaxPlayer()].isFall();
		}
		return false;
	}

	private boolean checkSpeed(MapSquare[][] mapSquares) {
		if (position.getY() + 1 < GLOBAL.MAP_PIXEL_SIZE) {
			return 	mapSquares[position.getX()][position.getY()].isSpeed() ||
					mapSquares[position.getX()][position.getYMaxPlayer()].isSpeed() ||
					mapSquares[position.getXMaxPlayer()][position.getY()].isSpeed() ||
					mapSquares[position.getXMaxPlayer()][position.getYMaxPlayer()].isSpeed();
		}
		return false;
	}

	private void movePlayer(Position newPosition, MapSquare[][] mapSquares) {
		
		if (velocity.getXShort() < 0 || velocity.getYShort() < 0) {
			if (checkCollide(newPosition.getX(), newPosition.getY(),
				position.getX(), position.getY(), mapSquares, newPosition)) {
				movePlayer(newPosition, mapSquares);
				return;
			}
		}
		if (velocity.getXShort() < 0 || velocity.getYShort() > 0) {
			if (checkCollide(newPosition.getX(), newPosition.getYMaxPlayer(),
				position.getX(), position.getYMaxPlayer(), mapSquares, newPosition)) {
					resetJump();
					movePlayer(newPosition, mapSquares);
					return;
			}
		}
		if (velocity.getXShort() > 0 || velocity.getYShort() < 0) {
			if (checkCollide(newPosition.getXMaxPlayer(), newPosition.getY(),
				position.getXMaxPlayer(), position.getY(), mapSquares, newPosition)) {
				movePlayer(newPosition, mapSquares);
				return;
			}
		}
		if (velocity.getXShort() > 0 || velocity.getYShort() > 0) {
			if (checkCollide(newPosition.getXMaxPlayer(), newPosition.getYMaxPlayer(),
				position.getXMaxPlayer(), position.getYMaxPlayer(), mapSquares, newPosition)) {
					resetJump();
					movePlayer(newPosition, mapSquares);
					return;
			}
		}
		position.set(newPosition);
	}
	
	private boolean canChangeMap() {
		return mapChangeWait >= GLOBAL.PLAYER_MAP_CHANGE_WAIT;
	}
	
	private boolean checkCollide(int newX, int newY, int currX, int currY, MapSquare[][] mapSquares, Position newPosition) {
		boolean collides = false;
		if (mapSquares[newX][newY].getWallPlayer()) {
			if (mapSquares[currX][newY].getWallPlayer()) {
				newPosition.setY(position);
				velocity.setY(0);
				collides = true;
			}
			if (mapSquares[newX][currY].getWallPlayer()) {
				newPosition.setX(position);
				velocity.setX(0);
				collides = true;
			}
			if (collides == false) {
				if (velocity.getXShort() > velocity.getYShort()) {
					newPosition.setY(position);
					velocity.setY(0);
					collides = true;
				}
				else {
					newPosition.setX(position);
					velocity.setX(0);
					collides = true;
				}
			}
		}
		
		return collides;
	}

	private void resetJump() {
		jumpsLeft = GLOBAL.PLAYER_JUMP_LIMIT;
	}

	public void tinyJump() {
		velocity.subtractYShort(GLOBAL.PLAYER_JUMP);
	}
	
	private void jump() {
		velocity.setYShort(-GLOBAL.PLAYER_JUMP);
	}
	
	public Position getPosition() {
		return position.clone();
	}

	public Point getMapChangeTo() {
		Point mct = (Point) mapChangeTo.clone();
		mapChangeTo = new Point(0, 0);
		return mct;
	}

	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		
		paintPlayer(g2d, position.getXScreen(), position.getYScreen(), GLOBAL.playerScreenSize.width, GLOBAL.playerScreenSize.height);
		
		g2d.drawString(coins + "           Deaths:" + deaths, (int) (GLOBAL.screenPixelWidth + (int)GLOBAL.screenPixelWidth), (int)GLOBAL.screenPixelHeight);
		Coin.paintCoin(g2d, 1, 0);
	}
	
	public static void paintPlayer(Graphics2D g2d, int x, int y, int width, int height) {
		g2d.drawImage(playerImage, x, y, width, height, null);
	}

	public static void load() {
		try {
			playerImage = ImageIO.read(Player.class.getResource("/Player.png"));
		} catch (IOException e) {}
	}

	public void changeMap(boolean isChanging) {
		mapChangeWait = 0;
		if (isChanging) {
			if (position.getXShort() > GLOBAL.PLAYER_U_MAX) {
				position.setX(0);
			}
			else if (position.getXShort() < 0) {
				position.setXShort(GLOBAL.PLAYER_U_MAX);
			}
			if (position.getYShort() > GLOBAL.PLAYER_U_MAX) {
				position.setY(0);
			}
			else if (position.getYShort() < 0) {
				position.setYShort(GLOBAL.PLAYER_U_MAX);
			}
		}
		else {
			if (position.getXShort() > GLOBAL.PLAYER_U_MAX) {
				position.setXShort(GLOBAL.PLAYER_U_MAX);
			}
			else if (position.getXShort() < 0) {
				position.setX(0);
			}
			if (position.getYShort() > GLOBAL.PLAYER_U_MAX) {
				position.setYShort(GLOBAL.PLAYER_U_MAX);
			}
			else if (position.getYShort() < 0) {
				position.setY(0);
			}
		}
		lastPosition.set(position);
	}

	public void addCoins(int numberOfCoins) {
		coins += numberOfCoins;
	}

	public void kill() {
		deaths++;
		position.set(lastPosition);
		velocity = new Position();
	}

	public int getCoins() {
		return coins;
	}
	
	public int getDeaths() {
		return deaths;
	}

	public void completeReset() {
		mapChangeWait = 0;
		jumpsLeft = 0;
		jumpWait = 0;
		coins = 0;
		deaths = 0;
		position = new Position();
		velocity = new Position();
		
		position.setX(GLOBAL.PLAYER_ORIGINAL_POSITION_X);
		position.setY(GLOBAL.PLAYER_ORIGINAL_POSITION_Y);
		lastPosition = new Position(position);
		
		mapChangeTo = new Point(0, 0);
	}
}
