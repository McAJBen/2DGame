package animationPackage;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import gamePackage.GLOBAL;
import gamePackage.Position;
import gamePackage.GLOBAL.Direction;

public class MapChangeAnimation {
	
	private BufferedImage oldImg, newImg;
	private Direction direction;
	private Position position;
	
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
		step = 0;
		this.oldImg = oldImg;
		this.newImg = newImg;
		this.position = new Position(position);
	}
	
	public boolean move() {
		step += GLOBAL.MAP_CHANGE_ANIMATION_STEP;
		switch (direction) {
		case DOWN:
			position.subtractYShort(GLOBAL.MAP_CHANGE_ANIMATION_PLAYER_STEP);
			break;
		case LEFT:
			position.addXShort(GLOBAL.MAP_CHANGE_ANIMATION_PLAYER_STEP);
			break;
		case RIGHT:
			position.subtractXShort(GLOBAL.MAP_CHANGE_ANIMATION_PLAYER_STEP);
			break;
		case UP:
			position.addYShort(GLOBAL.MAP_CHANGE_ANIMATION_PLAYER_STEP);
			break;
		}
		return step >= GLOBAL.MAP_SHORT_SIZE;
	}

	public void paint(Graphics g) {
		
		switch (direction) {
		case RIGHT:
			paintImages(g, -step, 0, GLOBAL.MAP_SHORT_SIZE - step, 0);
			break;
		case LEFT:
			paintImages(g, step, 0, step - GLOBAL.MAP_SHORT_SIZE, 0);
			break;
		case DOWN:
			paintImages(g, 0, -step, 0, GLOBAL.MAP_SHORT_SIZE - step);
			break;
		case UP:
			paintImages(g, 0, step, 0, step - GLOBAL.MAP_SHORT_SIZE);
			break;
		}
	}
	
	private void paintImages(Graphics g, int ox, int oy, int nx, int ny) {
		g.drawImage(oldImg,
				(int)(ox * GLOBAL.screenShortWidth),
				(int) (oy * GLOBAL.screenShortHeight),
				GLOBAL.screenSize.width, GLOBAL.screenSize.height, null);
		
		g.drawImage(newImg,
				(int)(nx * GLOBAL.screenShortWidth),
				(int) (ny * GLOBAL.screenShortHeight),
				GLOBAL.screenSize.width, GLOBAL.screenSize.height, null);
		
		g.fillRect(position.getXScreen(), position.getYScreen(), GLOBAL.playerScreenSize.width, GLOBAL.playerScreenSize.height);
	}
}
