package animationPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import gamePackage.GLOBAL;

public class EndAnimation {
	private double stage;

	public EndAnimation() {
		stage = 0;
	}

	public boolean move() {
		stage += GLOBAL.END_ANIMATION_STEP;
		return stage >= GLOBAL.END_ANIMATION_LENGTH;
	}

	public void paint(Graphics g, Dimension screenSize) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, screenSize.width, screenSize.height);
		g.setColor(Color.BLACK);
		g.drawString("Congraturation", screenSize.width / 2, screenSize.height / 2);
	}
}
