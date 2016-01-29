package gamePackage;

import java.awt.Color;
import java.awt.Graphics;

import gamePackage.GLOBAL.Direction;

public class Enemy {
	
	int x;
	int y;
	Direction direction;
	
	
	public Enemy(int x, int y, int i) {
		this.x = x * 10;
		this.y = y * 10;
		
		if (MapSquare.ENEMY_COLOR.getRGB() == i) {
			this.direction = Direction.UP;
		}
		else {
			this.direction = Direction.DOWN;
		}
		
		
	}

	public void move(MapSquare[][] mapSquares) {
		
		int nx = x;
		int ny = y;
		
		switch (direction) {
		case DOWN:
			ny++;
			break;
		case LEFT:
			nx--;
			break;
		case RIGHT:
			nx++;
			break;
		case UP:
			ny--;
			break;
		}
		if (nx < 0 ||
			GLOBAL.MAP_PIXEL_SIZE < nx / 10 ||
			ny < 0 ||
			GLOBAL.MAP_PIXEL_SIZE < ny / 10) {
				direction = direction.next();
		}
		
		switch (direction) {
		case DOWN:
			if (mapSquares[(nx - 1) / 10 + 1][(ny - 1) / 10 + 1].getWallEnemy()) {
				direction = direction.next();
				return;
			}
			break;
		case LEFT:
			if (mapSquares[nx / 10][(ny - 1) / 10 + 1].getWallEnemy()) {
				direction = direction.next();
				return;
			}
			break;
		case RIGHT:
			if (mapSquares[(nx - 1) / 10 + 1][ny / 10].getWallEnemy()) {
				direction = direction.next();
				return;
			}
			break;
		case UP:
			if (mapSquares[nx / 10][ny / 10].getWallEnemy()) {
				direction = direction.next();
				return;
			}
			break;
		}
		x = nx;
		y = ny;
	}

	public boolean checkEnemy(double px, double py) {
		return withinEnemy(px, py) ||
			   withinEnemy(px + GLOBAL.PLAYER_SIZE, py) ||
			   withinEnemy(px, py + GLOBAL.PLAYER_SIZE) ||
			   withinEnemy(px + GLOBAL.PLAYER_SIZE, py + GLOBAL.PLAYER_SIZE);
	}
	
	private boolean withinEnemy(double px, double py) {
		return x / 10 < px && px < x / 10 + 1 && y / 10 < py && py < y / 10 + 1;
	}

	public void paint(Graphics g, double width, double height) {
		
		g.setColor(Color.RED);
		
		g.fillOval((int)(x * width / 10), (int)(y * height / 10), (int)width, (int)height);
		
		//g.drawString(x + ", " + y, (int)(x * width / 10), (int)(y * height / 10));
	}
}
