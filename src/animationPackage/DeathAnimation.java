package animationPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DeathAnimation {
	
	private BufferedImage map, blood;
	private double stage;

	public DeathAnimation(BufferedImage currentMap) {
		map = currentMap;
		stage = 0;
	}

	public boolean move() {
		stage += 0.01; // TODO fix
		return stage >= 1;
	}

	public void paint(Graphics g, Dimension screenSize) {
		g.drawImage(map, 0, 0, screenSize.width, screenSize.height, null);
		g.setColor(Color.RED);
		g.drawImage(getBlood(), 0, 0, (int) ((2 - stage) * screenSize.width), (int) (stage * screenSize.height * 5), null);
		
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
