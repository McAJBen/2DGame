package animationPackage;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import settingsPackage.GLOBAL;
import gamePackage.Position;
import gamePackage.Player;

public class MapChangeAnimation {
	
	public static enum Direction {
		RIGHT, DOWN, LEFT, UP
	}
	
	private BufferedImage oldImg, newImg;
	private Direction direction;
	private Position playerPosition;
	private int imagePosition;
	
	private int step;
	
	public MapChangeAnimation(Point newMap, BufferedImage oldImg, BufferedImage newImg, Position position) {
		if (newMap.x > 0) {
			direction = Direction.RIGHT;
		}
		else if (newMap.x < 0) {
			direction = Direction.LEFT;
		}
		else if (newMap.y > 0) {
			direction = Direction.DOWN;
		}
		else if (newMap.y < 0) {
			direction = Direction.UP;
		}
		imagePosition = 0;
		this.oldImg = oldImg;
		this.newImg = newImg;
		this.playerPosition = new Position(position);
	}
	
	public boolean move() {
		step++;
		imagePosition += GLOBAL.MAP_CHANGE_ANIMATION_STEP;
		switch (direction) {
		case DOWN:
			playerPosition.setYShort(GLOBAL.PLAYER_U_MAX - getPlayerStep());
			break;
		case LEFT:
			playerPosition.setXShort(getPlayerStep());
			break;
		case RIGHT:
			playerPosition.setXShort(GLOBAL.PLAYER_U_MAX - getPlayerStep());
			break;
		case UP:
			playerPosition.setYShort(getPlayerStep());
			break;
		}
		return step >= GLOBAL.MAP_CHANGE_ANIMATION_LENGTH;
	}
	
	private int getPlayerStep() {
		return step * GLOBAL.PLAYER_U_MAX / GLOBAL.MAP_CHANGE_ANIMATION_LENGTH;
	}

	public void paint(Graphics2D g2d) {
		
		switch (direction) {
		case RIGHT:
			paintImages(g2d, -imagePosition, 0, GLOBAL.MAP_U_SIZE - imagePosition, 0);
			break;
		case LEFT:
			paintImages(g2d, imagePosition, 0, imagePosition - GLOBAL.MAP_U_SIZE, 0);
			break;
		case DOWN:
			paintImages(g2d, 0, -imagePosition, 0, GLOBAL.MAP_U_SIZE - imagePosition);
			break;
		case UP:
			paintImages(g2d, 0, imagePosition, 0, imagePosition - GLOBAL.MAP_U_SIZE);
			break;
		}
	}
	
	private void paintImages(Graphics2D g2d, int ox, int oy, int nx, int ny) {
		paintMap(g2d, oldImg, ox, oy);
		paintMap(g2d, newImg, nx, ny);
		Player.paintPlayer(g2d, playerPosition.getXScreen(), playerPosition.getYScreen(),
				GLOBAL.playerScreenSize.width, GLOBAL.playerScreenSize.height);
	}
	
	private void paintMap(Graphics2D g2d, BufferedImage img, int x, int y) {
		g2d.drawImage(img,
			(int)(x * GLOBAL.screenUWidth),
			(int)(y * GLOBAL.screenUHeight),
			GLOBAL.screenSize.width, GLOBAL.screenSize.height, null);
	}
}
