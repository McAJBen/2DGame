package animationPackage;

import java.awt.Color;
import java.awt.Graphics;

import gamePackage.GLOBAL;

public class EndAnimation {
	private double stage;
	private int deaths;

	public EndAnimation(int deaths) {
		stage = 0;
		this.deaths = deaths;
	}

	public boolean move() {
		stage += GLOBAL.END_ANIMATION_STEP;
		return stage >= GLOBAL.END_ANIMATION_LENGTH;
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GLOBAL.screenSize.width, GLOBAL.screenSize.height);
		g.setColor(Color.BLACK);
		g.drawString("Congraturation", GLOBAL.screenSize.width / 2, GLOBAL.screenSize.height / 2);
		g.drawString(deaths + " Deaths", GLOBAL.screenSize.width / 2, 10 + GLOBAL.screenSize.height / 2);
	}
}
