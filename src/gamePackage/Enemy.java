package gamePackage;

import java.awt.Color;
import java.awt.Graphics;

import gamePackage.GLOBAL.Direction;

public class Enemy {
	
	Position position;
	Direction direction;
	
	private static int stepWidth;
	private static int stepHeight;
	private static int stepSWidth;
	private static int stepSHeight;
	private static byte step;
	
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
	
	public static void move() {
		step--;
		stepWidth = (int) ((step + Byte.MAX_VALUE) * GLOBAL.pixelWidth / Byte.MAX_VALUE / 4);
		stepHeight = (int) ((step + Byte.MAX_VALUE) * GLOBAL.pixelHeight / Byte.MAX_VALUE / 4);
		stepSWidth = stepWidth * 2;
		stepSHeight = stepHeight * 2;
		
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

	public void paint(Graphics g) {
		g.setColor(new Color(136, 0, 21));
		g.fillOval(position.getXScreen(), position.getYScreen(), (int)GLOBAL.pixelWidth, (int)GLOBAL.pixelHeight);
		g.setColor(Color.RED);
		g.fillOval(
				position.getXScreen() + stepWidth,
				position.getYScreen() + stepHeight,
				(int)GLOBAL.pixelWidth - stepSWidth,
				(int)GLOBAL.pixelHeight - stepSHeight);
	}
}












