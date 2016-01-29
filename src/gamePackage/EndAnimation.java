package gamePackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class EndAnimation {
	private double stage;

	public EndAnimation() {
		stage = 0;
	}

	public boolean move() {
		stage += GLOBAL.ANIMATION_STEP;
		return stage >= 10;
	}

	public void paint(Graphics g, Dimension screenSize) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, screenSize.width, screenSize.height);
		g.setColor(Color.BLACK);
		g.drawString("Congraturation", screenSize.width / 2, screenSize.height / 2);
		
	}
}
