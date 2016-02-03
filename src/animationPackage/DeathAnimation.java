package animationPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import gamePackage.GLOBAL;

public class DeathAnimation {
	
	private BufferedImage map;
	private static BufferedImage blood;
	private short step;

	public DeathAnimation(BufferedImage currentMap) {
		map = currentMap;
		step = 0;
	}

	public boolean move() {
		step += GLOBAL.DEATH_ANIMATION_STEP;
		return step >= GLOBAL.DEATH_ANIMATION_LENGTH;
	}

	public void paint(Graphics g) {
		g.drawImage(map, 0, 0, GLOBAL.screenSize.width, GLOBAL.screenSize.height, null);
		g.setColor(Color.RED);
		g.drawImage(getBlood(), 0, 0, GLOBAL.screenSize.width, (int) (step * GLOBAL.screenShortHeight), null);
	}

	private Image getBlood() {
		if (blood == null) {
			try {
    			blood = ImageIO.read(getClass().getResource("/Death.png"));
    		} catch (IOException e) {}
		}
		return blood;
	}
}
