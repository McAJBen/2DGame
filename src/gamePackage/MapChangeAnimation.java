package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import gamePackage.GLOBAL.Direction;

public class MapChangeAnimation {
	private BufferedImage oldImg, newImg;
	private Direction direction;
	private double stage;
	private double x, y;
	
	public MapChangeAnimation(Point newMap, BufferedImage oldImg, BufferedImage newImg,
			double x, double y) {
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
		stage = 0;
		this.oldImg = oldImg;
		this.newImg = newImg;
		this.x = x;
		this.y = y;
		
	}
	
	public boolean move() {
		stage += GLOBAL.ANIMATION_STEP;
		switch (direction) {
		case DOWN:
			y -= GLOBAL.PLAYER_ANIMATION_STEP;
			break;
		case LEFT:
			x += GLOBAL.PLAYER_ANIMATION_STEP;
			break;
		case RIGHT:
			x -= GLOBAL.PLAYER_ANIMATION_STEP;
			break;
		case UP:
			y += GLOBAL.PLAYER_ANIMATION_STEP;
			break;
		}
		return stage >= 1;
	}

	public void paint(Graphics g, Dimension screenSize) {
		
		Point oldMap = new Point(0, 0);
		Point newMap = new Point(0, 0);
		
		switch (direction) {
		case RIGHT:
			oldMap.x = (int) (-stage * screenSize.width);
			newMap.x = (int) ((1 - stage) * screenSize.width);
			break;
		case LEFT:
			oldMap.x = (int) (stage * screenSize.width);
			newMap.x = (int) ((stage - 1) * screenSize.width);
			break;
		case DOWN:
			oldMap.y = (int) (-stage * screenSize.height);
			newMap.y = (int) ((1 - stage) * screenSize.height);
			break;
		case UP:
			oldMap.y = (int) (stage * screenSize.height);
			newMap.y = (int) ((stage - 1) * screenSize.height);
			break;
		}
		
		g.drawImage(oldImg,
				oldMap.x,
				oldMap.y,
				screenSize.width, screenSize.height, null);
		g.drawImage(newImg,
				newMap.x,
				newMap.y, 
				screenSize.width, screenSize.height, null);
		
		g.fillRect(
				(int)(x * screenSize.width / GLOBAL.MAP_PIXEL_SIZE),
				(int)(y * screenSize.height / GLOBAL.MAP_PIXEL_SIZE),
				(int)(GLOBAL.PLAYER_SIZE * screenSize.width / GLOBAL.MAP_SHORT_SIZE),
				(int)(GLOBAL.PLAYER_SIZE * screenSize.height / GLOBAL.MAP_SHORT_SIZE));
		
	}
}
