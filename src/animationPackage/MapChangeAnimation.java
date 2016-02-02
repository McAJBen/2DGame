package animationPackage;

import java.awt.Dimension;
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

	public void paint(Graphics g, Dimension screenSize) {
		
		switch (direction) {
		case RIGHT:
			paintImages(g, screenSize, -step, 0, GLOBAL.MAP_SHORT_SIZE - step, 0);
			break;
		case LEFT:
			paintImages(g, screenSize, step, 0, step - GLOBAL.MAP_SHORT_SIZE, 0);
			break;
		case DOWN:
			paintImages(g, screenSize, 0, -step, 0, GLOBAL.MAP_SHORT_SIZE - step);
			break;
		case UP:
			paintImages(g, screenSize, 0, step, 0, step - GLOBAL.MAP_SHORT_SIZE);
			break;
		}
	}
	
	private void paintImages(Graphics g, Dimension screenSize, int ox, int oy, int nx, int ny) {
		
		g.drawImage(oldImg,
				ox * screenSize.width / GLOBAL.MAP_SHORT_SIZE,
				oy * screenSize.height / GLOBAL.MAP_SHORT_SIZE,
				screenSize.width, screenSize.height, null);
		
		g.drawImage(newImg,
				nx * screenSize.width / GLOBAL.MAP_SHORT_SIZE,
				ny * screenSize.height / GLOBAL.MAP_SHORT_SIZE,
				screenSize.width, screenSize.height, null);
		
		/*g.fillRect( // TODO fix
				(int)(position.getXDouble() / GLOBAL.MAP_PIXEL_SIZE * screenSize.width),
				(int)(position.getYDouble() / GLOBAL.MAP_PIXEL_SIZE * screenSize.height),
				(int)(GLOBAL.PLAYER_SIZE * screenSize.width / GLOBAL.MAP_SHORT_SIZE),
				(int)(GLOBAL.PLAYER_SIZE * screenSize.height / GLOBAL.MAP_SHORT_SIZE));*/
	}
	
	
	
}
