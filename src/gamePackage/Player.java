package gamePackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

public class Player {
	
	private Position position;
	private Position velocity;
	private Position lastPosition;
	private Point mapChangeTo;
	private int coins;
	
	public Player() {
		completeReset();
	}
	
	public boolean move(int dx, int dy, boolean jump, MapSquare[][] mapSquares) {
		
		
		velocity.addX(dx * GLOBAL.PLAYER_STEP);
		velocity.addY(dy * GLOBAL.PLAYER_STEP);
		velocity.addY(GLOBAL.PLAYER_STEP);
		
		if (jump) {
			velocity.addY( -GLOBAL.PLAYER_JUMP); // TODO disable multi-jumping
		}
		
		velocity.Friction(0.9);
		
		Position newPosition = new Position(
					position.getXShort() + velocity.getXShort(),
					position.getYShort() + velocity.getYShort());
		
		
		if (newPosition.getXShort() < 0) {
			mapChangeTo.x = -1;
			position.setX(newPosition);
			return true;
		}
		else if (GLOBAL.PLAYER_MAX_PIXEL < newPosition.getXShort()) {
			mapChangeTo.x = 1;
			position.setX(newPosition);
			return true;
		}
		else if (newPosition.getYShort() < 0) {
			mapChangeTo.y = -1;
			position.setY(newPosition);
			return true;
		}
		else if (GLOBAL.PLAYER_MAX_PIXEL < newPosition.getYShort()) {
			mapChangeTo.y = 1;
			position.setY(newPosition);
			return true;
		}
		
		movePlayer(newPosition, mapSquares);
		
		return false;
	}
	
	private void movePlayer(Position newPosition, MapSquare[][] mapSquares) {
		boolean tryAgain = false;
		
		if (velocity.getXShort() < 0 || velocity.getYShort() < 0) {
			if (mapSquares[newPosition.getX()][newPosition.getY()].getWall()) {
				if (mapSquares[position.getX()][newPosition.getY()].getWall()) {
					newPosition.setY(position);
					velocity.setY(0);
					tryAgain = true;
				}
				if (mapSquares[newPosition.getX()][position.getY()].getWall()) {
					newPosition.setX(position);
					velocity.setX(0);
					tryAgain = true;
				}
			}
		}
		if (velocity.getXShort() < 0 || velocity.getYShort() > 0) {
			if (mapSquares[newPosition.getX()][newPosition.getYMaxPlayer()].getWall()) {
				if (mapSquares[position.getX()][newPosition.getYMaxPlayer()].getWall()) {
					newPosition.setY(position);
					velocity.setY(0);
					tryAgain = true;
				}
				if (mapSquares[newPosition.getX()][position.getYMaxPlayer()].getWall()) {
					newPosition.setX(position);
					velocity.setX(0);
					tryAgain = true;
				}
			}
		}
		if (velocity.getXShort() > 0 || velocity.getYShort() < 0) {
			if (mapSquares[newPosition.getXMaxPlayer()][newPosition.getY()].getWall()) {
				if (mapSquares[position.getXMaxPlayer()][newPosition.getY()].getWall()) {
					newPosition.setY(position);
					velocity.setY(0);
					tryAgain = true;
				}
				if (mapSquares[newPosition.getXMaxPlayer()][position.getY()].getWall()) {
					newPosition.setX(position);
					velocity.setX(0);
					tryAgain = true;
				}
			}
		}
		if (velocity.getXShort() > 0 || velocity.getYShort() > 0) {
			if (mapSquares[newPosition.getXMaxPlayer()][newPosition.getYMaxPlayer()].getWall()) {
				if (mapSquares[position.getXMaxPlayer()][newPosition.getYMaxPlayer()].getWall()) {
					newPosition.setY(position);
					velocity.setY(0);
					tryAgain = true;
				}
				if (mapSquares[newPosition.getXMaxPlayer()][position.getYMaxPlayer()].getWall()) {
					newPosition.setX(position);
					velocity.setX(0);
					tryAgain = true;
				}
			}
		}
		if (tryAgain) {
			movePlayer(newPosition, mapSquares);
		}
		else {
			position.set(newPosition);
		}
	}
	
	public double getX() { // TODO get rid of this
		return position.getXDouble();
	}
	
	public double getY() {
		return position.getYDouble();
	}
	
	public Position getPosition() {
		return position.clone();
	}

	public Point getMapChangeTo() {
		Point mct = (Point) mapChangeTo.clone();
		mapChangeTo = new Point(0, 0);
		return mct;
	}

	public void paint(Graphics g, Dimension screenSize) {
		g.setColor(Color.BLACK);
		g.fillRect(
				(int)(position.getXDouble() / GLOBAL.MAP_PIXEL_SIZE * screenSize.width),
				(int)(position.getYDouble() / GLOBAL.MAP_PIXEL_SIZE * screenSize.height),
				(int)(GLOBAL.PLAYER_SIZE * screenSize.width / GLOBAL.MAP_SHORT_SIZE),
				(int)(GLOBAL.PLAYER_SIZE * screenSize.height / GLOBAL.MAP_SHORT_SIZE));
		
		g.drawString("Coins: " + coins, 0, screenSize.height - 4);
		
		
		
		g.drawString(position.getXShort() + "",
				(int)(position.getXDouble() / GLOBAL.MAP_PIXEL_SIZE * screenSize.width),
				(int)(position.getYDouble() / GLOBAL.MAP_PIXEL_SIZE * screenSize.height) - 32);
		
		g.drawString(position.getYShort() + "", 
				(int)(position.getXDouble() / GLOBAL.MAP_PIXEL_SIZE * screenSize.width),
				(int)(position.getYDouble() / GLOBAL.MAP_PIXEL_SIZE * screenSize.height) - 22);
		
		g.drawString(velocity.getXShort() + "",
				(int)(position.getXDouble() / GLOBAL.MAP_PIXEL_SIZE * screenSize.width),
				(int)(position.getYDouble() / GLOBAL.MAP_PIXEL_SIZE * screenSize.height) - 12);
		
		g.drawString(velocity.getYShort() + "", 
				(int)(position.getXDouble() / GLOBAL.MAP_PIXEL_SIZE * screenSize.width),
				(int)(position.getYDouble() / GLOBAL.MAP_PIXEL_SIZE * screenSize.height) - 2);
	}

	public void changeMap(boolean isChanging) {
		if (isChanging) {
			if (position.getXShort() > GLOBAL.PLAYER_MAX_PIXEL) {
				position.setX(0);
			}
			else if (position.getXShort() < 0) {
				position.setXShort(GLOBAL.PLAYER_MAX_PIXEL);
			}
			if (position.getYShort() > GLOBAL.PLAYER_MAX_PIXEL) {
				position.setY(0);
			}
			else if (position.getYShort() < 0) {
				position.setYShort(GLOBAL.PLAYER_MAX_PIXEL);
			}
		}
		else {
			if (position.getXShort() > GLOBAL.PLAYER_MAX_PIXEL) {
				position.setXShort(GLOBAL.PLAYER_MAX_PIXEL);
			}
			else if (position.getXShort() < 0) {
				position.setX(0);
			}
			if (position.getYShort() > GLOBAL.PLAYER_MAX_PIXEL) {
				position.setYShort(GLOBAL.PLAYER_MAX_PIXEL);
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
		position.set(lastPosition);
	}

	public int getCoins() {
		return coins;
	}

	public void completeReset() {
		coins = 0;
		position = new Position();
		velocity = new Position();
		
		
		position.setX(GLOBAL.PLAYER_ORIGINAL_X);
		position.setY(GLOBAL.PLAYER_ORIGINAL_Y);
		lastPosition = new Position(position);
		
		mapChangeTo = new Point(0, 0);
		
	}
	
}
