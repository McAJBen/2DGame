package animationPackage;

import java.awt.Color;
import java.awt.Graphics2D;

import settingsPackage.GLOBAL;

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

	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, GLOBAL.screenSize.width, GLOBAL.screenSize.height);
		g2d.setColor(Color.BLACK);
		g2d.drawString("Congraturation", GLOBAL.screenSize.width / 2, GLOBAL.screenSize.height / 2);
		g2d.drawString(deaths + " Deaths", GLOBAL.screenSize.width / 2, 10 + GLOBAL.screenSize.height / 2);
	}
}
