package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class MapChangeAnimation {
	
	private static final double STEP = 0.01;
	private static enum Direction {
		RIGHT, LEFT, DOWN, UP, 
	}
	
	private BufferedImage oldImg, newImg;
	private Direction direction;
	private Dimension playerSize;
	private double stage;
	private double x, y;
	
	public MapChangeAnimation(Point newMap, BufferedImage oldImg, BufferedImage newImg,
			double x, double y, Dimension playerSize) {
		if (newMap.x > 0) {
			direction = Direction.RIGHT;
			x = 1;
		}
		else if (newMap.x < 0) {
			direction = Direction.LEFT;
			x = 0;
		}
		else if (newMap.y > 0) {
			direction = Direction.DOWN;
			y = 1;
		}
		else if (newMap.y < 0) {
			direction = Direction.UP;
			y = 0;
		}
		stage = 0;
		this.oldImg = oldImg;
		this.newImg = newImg;
		this.x = x;
		this.y = y;
		this.playerSize = playerSize;
		
	}
	
	public boolean move() {
		stage += STEP;
		switch (direction) {
		case DOWN:
			y -= STEP;
			break;
		case LEFT:
			x += STEP;
			break;
		case RIGHT:
			x -= STEP;
			break;
		case UP:
			y += STEP;
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
			oldPosition.y = (int) (-stage * screenSize.width);
			newPosition.y = (int) ((1 - stage) * screenSize.width);
			break;
		case UP:
			oldPosition.y = (int) (stage * screenSize.width);
			newPosition.y = (int) ((stage - 1) * screenSize.width);
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
				(int)(x * (screenSize.width - playerSize.width)),
				(int)(y * (screenSize.height - playerSize.height)),
				playerSize.width, playerSize.height);
		
	}
	
	
	
	
	
	
	
}
