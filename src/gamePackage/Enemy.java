package gamePackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import gamePackage.GLOBAL.Direction;

public class Enemy {
	
	Position position;
	Direction direction;
	
	public Enemy(int x, int y, int i) {
		x *= GLOBAL.SHORT_MULTIPLIER;
		y *= GLOBAL.SHORT_MULTIPLIER;
		position = new Position(x, y);
		
		if (MapSquare.ENEMY_COLOR.getRGB() == i) {
			this.direction = Direction.UP;
		}
		else {
			this.direction = Direction.DOWN;
		}
	}

	public void move(MapSquare[][] mapSquares) {
		
		Position newPosition = new Position(position);
		
		switch (direction) {
		case DOWN:
			newPosition.addYShort(GLOBAL.ENEMY_STEP);
			break;
		case LEFT:
			newPosition.subtractXShort(GLOBAL.ENEMY_STEP);
			break;
		case RIGHT:
			newPosition.addXShort(GLOBAL.ENEMY_STEP);
			break;
		case UP:
			newPosition.subtractYShort(GLOBAL.ENEMY_STEP);
			break;
		}
		if (!newPosition.within(GLOBAL.MAP_SHORT_SIZE)) {
				direction = direction.next();
		}
		
		switch (direction) {
		case DOWN:
			if (mapSquares[newPosition.getX()][newPosition.getYMaxM1()].getWallEnemy()) {
				direction = direction.next();
				return;
			}
			break;
		case LEFT:
			if (mapSquares[newPosition.getX()][newPosition.getY()].getWallEnemy()) {
				direction = direction.next();
				return;
			}
			break;
		case RIGHT:
			if (mapSquares[newPosition.getXMaxM1()][newPosition.getY()].getWallEnemy()) {
				direction = direction.next();
				return;
			}
			break;
		case UP:
			if (mapSquares[newPosition.getX()][newPosition.getY()].getWallEnemy()) {
				direction = direction.next();
				return;
			}
			break;
		}
		position.set(newPosition);
	}

	public boolean checkEnemy(Position checkPosition) {
		return 	withinEnemy(checkPosition.getXShort(), checkPosition.getYShort()) ||
				withinEnemy(checkPosition.getXShort() + GLOBAL.PLAYER_SIZE, checkPosition.getYShort()) ||
				withinEnemy(checkPosition.getXShort(), checkPosition.getYShort() + GLOBAL.PLAYER_SIZE) ||
				withinEnemy(checkPosition.getXShort() + GLOBAL.PLAYER_SIZE, checkPosition.getYShort() + GLOBAL.PLAYER_SIZE);
	}
	
	private boolean withinEnemy(int px, int py) {
		return 	position.getXShort() < px && px < position.getXMaxShort() && 
				position.getYShort() < py && py < position.getYMaxShort();
	}

	public void paint(Graphics g, double width, double height, Dimension screenSize) {
		g.setColor(Color.RED);
		g.fillOval(position.getX(screenSize), position.getY(screenSize), (int)width, (int)height);
	}
}