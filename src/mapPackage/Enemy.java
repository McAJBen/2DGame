package mapPackage;

import java.awt.Color;
import java.awt.Graphics;

import settingsPackage.GLOBAL;
import gamePackage.Position;

public class Enemy {
	
	public static enum Direction {
		RIGHT, DOWN, LEFT, 
		UP {
			@Override
			public Direction next() {
				return Direction.RIGHT;
			};
		};
		public Direction next() {
			return values()[ordinal() + 1];
		}
	}
	
	private Position position;
	private Direction direction;
	
	private static int stepX;
	private static int stepY;
	private static int stepWidth;
	private static int stepHeight;
	private static byte step;
	
	public Enemy(int x, int y, int i) {
		x *= GLOBAL.U_MULTIPLIER;
		y *= GLOBAL.U_MULTIPLIER;
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
		stepX = (int) ((step + Byte.MAX_VALUE) * GLOBAL.screenPixelWidth / Byte.MAX_VALUE / 4);
		stepY = (int) ((step + Byte.MAX_VALUE) * GLOBAL.screenPixelHeight / Byte.MAX_VALUE / 4);
		stepWidth = stepX * 2;
		stepHeight = stepY * 2;
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
		if (!newPosition.within(GLOBAL.MAP_U_SIZE)) {
			direction = direction.next();
			return;
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

	public boolean checkDeath(Position checkPosition) {
		return 	within(checkPosition.getXShort(), checkPosition.getYShort()) ||
				within(checkPosition.getXShort() + GLOBAL.PLAYER_SIZE, checkPosition.getYShort()) ||
				within(checkPosition.getXShort(), checkPosition.getYShort() + GLOBAL.PLAYER_SIZE) ||
				within(checkPosition.getXShort() + GLOBAL.PLAYER_SIZE, checkPosition.getYShort() + GLOBAL.PLAYER_SIZE);
	}
	
	private boolean within(int px, int py) {
		return 	position.getXShort() < px && px < position.getXMaxShort() && 
				position.getYShort() < py && py < position.getYMaxShort();
	}

	private void paint(Graphics g, int width, int height, int w2, int h2) {
		g.setColor(new Color(136, 0, 21));
		g.fillOval(position.getXScreen(), position.getYScreen(), width, height);
		g.setColor(Color.RED);
		g.fillOval(
				position.getXScreen() + stepX,
				position.getYScreen() + stepY,
				w2,
				h2);
	}

	public static boolean checkDeath(Position pos, Enemy[] enemies) {
		for (Enemy e: enemies) {
			if (e.checkDeath(pos)) {
				return true;
			}
		}
		return false;
	}

	public static void paint(Graphics imageG, Enemy[] enemies) {
		int width = (int)GLOBAL.screenPixelWidth;
		int height = (int)GLOBAL.screenPixelHeight;
		int w2 = width - stepWidth;
		int h2 = height - stepHeight;
		
		for (Enemy e: enemies) {
			e.paint(imageG, width, height, w2, h2);
		}
	}
}