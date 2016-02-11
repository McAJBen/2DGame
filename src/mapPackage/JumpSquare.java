package mapPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import gamePackage.GLOBAL;
import gamePackage.Position;

public class JumpSquare {
	
	private static final Color BUBBLE_COLOR = new Color(255, 130, 170);
	
	private static Random rand = new Random();
	
	private Position position;
	private Position bubblePosition;
	
	private short bubbleWidth;
	
	public JumpSquare(int x, int y) {
		x *= GLOBAL.U_MULTIPLIER;
		y *= GLOBAL.U_MULTIPLIER;
		position = new Position(x, y);
		bubblePosition = new Position();
		bubbleWidth = GLOBAL.MAX_BUBBLE_DIAM;
		move();
	}

	public void move() {
		if (bubbleWidth >= GLOBAL.MAX_BUBBLE_DIAM || rand.nextFloat() > 0.999f) { // reset bubble
			bubblePosition.set(position);
			
			bubblePosition.addXShort(getRandom());
			bubblePosition.addYShort(getRandom());
			
			bubbleWidth = (short) rand.nextInt(GLOBAL.MAX_BUBBLE_DIAM);
		}
		else if (rand.nextFloat() > 0.99f) {
			bubbleWidth += 2;
			bubblePosition.addXShort(-1);
			bubblePosition.addYShort(-1);
		}
	}
	
	private int getRandom() {
		return GLOBAL.MAX_BUBBLE_RADIUS + rand.nextInt(GLOBAL.U_MULTIPLIER - GLOBAL.MAX_BUBBLE_DIAM);
	}

	public boolean checkJumpSquare(Position checkPosition) {
		return 	within(checkPosition.getXShort(), checkPosition.getYShort()) ||
				within(checkPosition.getXShort() + GLOBAL.PLAYER_SIZE, checkPosition.getYShort()) ||
				within(checkPosition.getXShort(), checkPosition.getYShort() + GLOBAL.PLAYER_SIZE) ||
				within(checkPosition.getXShort() + GLOBAL.PLAYER_SIZE, checkPosition.getYShort() + GLOBAL.PLAYER_SIZE);
	}
	
	private boolean within(int px, int py) {
		return 	position.getXShort() < px && px < position.getXMaxShort() && 
				position.getYShort() < py && py < position.getYMaxShort();
	}

	public void paint(Graphics g) {
		g.setColor(MapSquare.JUMP_SQUARE_COLOR);
		
		int px = position.getXScreen();
		int py = position.getYScreen();
		int width = (int)((position.getXShort() + GLOBAL.U_MULTIPLIER) * GLOBAL.screenUWidth) - px;
		int height = (int)((position.getYShort() + GLOBAL.U_MULTIPLIER) * GLOBAL.screenUHeight) - py;
		
		g.fillRect(px, py, width, height);
		g.setColor(BUBBLE_COLOR);
		
		px = bubblePosition.getXScreen();
		py = bubblePosition.getYScreen();
		width = (int)((bubblePosition.getXShort() + bubbleWidth) * GLOBAL.screenUWidth) - px;
		height = (int)((bubblePosition.getYShort() + bubbleWidth) * GLOBAL.screenUHeight) - py;
		
		g.fillOval(px, py, width, height);
	}
}
