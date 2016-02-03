package gamePackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Player {
	
	private Position position;
	private Position velocity;
	private Position lastPosition;
	private Point mapChangeTo;
	private int coins;
	private byte jumpWait;
	private byte jumpsLeft;
	
	public Player() {
		completeReset();
	}
	
	public boolean move(int dx, boolean jump, MapSquare[][] mapSquares) {
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
		
		
		if (newPosition.getXShort() < 0) {
			mapChangeTo.x = -1;
			position.setX(newPosition);
			return true;
		}
		else if (GLOBAL.PLAYER_SHORT_MAX < newPosition.getXShort()) {
			mapChangeTo.x = 1;
			position.setX(newPosition);
			return true;
		}
		else if (newPosition.getYShort() < 0) {
			mapChangeTo.y = -1;
			position.setY(newPosition);
			return true;
		}
		else if (GLOBAL.PLAYER_SHORT_MAX < newPosition.getYShort()) {
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
			if (mapSquares[newPosition.getX()][newPosition.getY()].getWallPlayer()) {
				if (mapSquares[position.getX()][newPosition.getY()].getWallPlayer()) {
					newPosition.setY(position);
					velocity.setY(0);
					tryAgain = true;
				}
				if (mapSquares[newPosition.getX()][position.getY()].getWallPlayer()) {
					newPosition.setX(position);
					velocity.setX(0);
					tryAgain = true;
				}
			}
		}
		if (velocity.getXShort() < 0 || velocity.getYShort() > 0) {
			if (mapSquares[newPosition.getX()][newPosition.getYMaxPlayer()].getWallPlayer()) {
				if (mapSquares[position.getX()][newPosition.getYMaxPlayer()].getWallPlayer()) {
					
					jumpsLeft = GLOBAL.PLAYER_JUMP_LIMIT;
					
					newPosition.setY(position);
					velocity.setY(0);
					tryAgain = true;
				}
				if (mapSquares[newPosition.getX()][position.getYMaxPlayer()].getWallPlayer()) {
					newPosition.setX(position);
					velocity.setX(0);
					tryAgain = true;
				}
			}
		}
		if (velocity.getXShort() > 0 || velocity.getYShort() < 0) {
			if (mapSquares[newPosition.getXMaxPlayer()][newPosition.getY()].getWallPlayer()) {
				if (mapSquares[position.getXMaxPlayer()][newPosition.getY()].getWallPlayer()) {
					newPosition.setY(position);
					velocity.setY(0);
					tryAgain = true;
				}
				if (mapSquares[newPosition.getXMaxPlayer()][position.getY()].getWallPlayer()) {
					newPosition.setX(position);
					velocity.setX(0);
					tryAgain = true;
				}
			}
		}
		if (velocity.getXShort() > 0 || velocity.getYShort() > 0) {
			if (mapSquares[newPosition.getXMaxPlayer()][newPosition.getYMaxPlayer()].getWallPlayer()) {
				if (mapSquares[position.getXMaxPlayer()][newPosition.getYMaxPlayer()].getWallPlayer()) {
					
					jumpsLeft = GLOBAL.PLAYER_JUMP_LIMIT;
					
					newPosition.setY(position);
					velocity.setY(0);
					tryAgain = true;
				}
				if (mapSquares[newPosition.getXMaxPlayer()][position.getYMaxPlayer()].getWallPlayer()) {
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

	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		
		g.fillRect(position.getXScreen(), position.getYScreen(), GLOBAL.playerScreenSize.width, GLOBAL.playerScreenSize.height);
		
		g.drawString("Coins: " + coins, GLOBAL.screenCoinPosition.x, GLOBAL.screenCoinPosition.y);
	}

	public void changeMap(boolean isChanging) {
		if (isChanging) {
			if (position.getXShort() > GLOBAL.PLAYER_SHORT_MAX) {
				position.setX(0);
			}
			else if (position.getXShort() < 0) {
				position.setXShort(GLOBAL.PLAYER_SHORT_MAX);
			}
			if (position.getYShort() > GLOBAL.PLAYER_SHORT_MAX) {
				position.setY(0);
			}
			else if (position.getYShort() < 0) {
				position.setYShort(GLOBAL.PLAYER_SHORT_MAX);
			}
		}
		else {
			if (position.getXShort() > GLOBAL.PLAYER_SHORT_MAX) {
				position.setXShort(GLOBAL.PLAYER_SHORT_MAX);
			}
			else if (position.getXShort() < 0) {
				position.setX(0);
			}
			if (position.getYShort() > GLOBAL.PLAYER_SHORT_MAX) {
				position.setYShort(GLOBAL.PLAYER_SHORT_MAX);
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
		velocity = new Position();
	}

	public int getCoins() {
		return coins;
	}

	public void completeReset() {
		jumpsLeft = 0;
		jumpWait = 0;
		coins = 0;
		position = new Position();
		velocity = new Position();
		
		position.setX(GLOBAL.PLAYER_ORIGINAL_POSITION.x);
		position.setY(GLOBAL.PLAYER_ORIGINAL_POSITION.y);
		lastPosition = new Position(position);
		
		mapChangeTo = new Point(0, 0);
	}
}
