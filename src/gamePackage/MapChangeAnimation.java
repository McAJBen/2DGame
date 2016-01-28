package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import gamePackage.GLOBAL.Direction;

public class MapChangeAnimation {
	
	private BufferedImage oldImg, newImg;
	private Direction direction;
	private Dimension playerSize;
	private double stage;
	private double x, y;
	
	public MapChangeAnimation(Point newMap, BufferedImage oldImg, BufferedImage newImg,
			double x, double y, Dimension playerSize) {
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
		this.playerSize = playerSize;
		
	}
	
	public boolean move() {
		stage += GLOBAL.ANIMATION_STEP;
		switch (direction) {
		case DOWN:
			y -= GLOBAL.ANIMATION_STEP;
			break;
		case LEFT:
			x += GLOBAL.ANIMATION_STEP;
			break;
		case RIGHT:
			x -= GLOBAL.ANIMATION_STEP;
			break;
		case UP:
			y += GLOBAL.ANIMATION_STEP;
			break;
		}
		return stage >= 1;
	}

	public void paint(Graphics g, Dimension screenSize) {
		
		Point oldPosition = new Point(0, 0);
		Point newPosition = new Point(0, 0);
		
		switch (direction) {
		case RIGHT:
			oldPosition.x = (int) (-stage * screenSize.width);
			newPosition.x = (int) ((1 - stage) * screenSize.width);
			break;
		case LEFT:
			oldPosition.x = (int) (stage * screenSize.width);
			newPosition.x = (int) ((stage - 1) * screenSize.width);
			break;
		case DOWN:
			oldPosition.y = (int) (-stage * screenSize.height);
			newPosition.y = (int) ((1 - stage) * screenSize.height);
			break;
		case UP:
			oldPosition.y = (int) (stage * screenSize.height);
			newPosition.y = (int) ((stage - 1) * screenSize.height);
			break;
		}
		
		g.drawImage(oldImg,
				oldPosition.x,
				oldPosition.y,
				screenSize.width, screenSize.height, null);
		g.drawImage(newImg,
				newPosition.x,
				newPosition.y, 
				screenSize.width, screenSize.height, null);
		
		
		
		
		g.fillRect(
				(int)(x * screenSize.width),
				(int)(y * screenSize.height),
				playerSize.width, playerSize.height);
		
	}
	
	
	
	
	
	
	
}
