package animationPackage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import gamePackage.Player;
import gamePackage.Position;
import mapPackage.MapSquare;
import settingsPackage.GLOBAL;

public class DeathAnimation {
	
	public static enum Death {
		ELECTRIC, ENEMY
	}
	private BufferedImage map;
	private static BufferedImage explosion;
	private Position playerPosition;
	private short step;

	private Death death;
	
	public DeathAnimation(BufferedImage currentMap, Death deathType, Position position) {
		map = currentMap;
		playerPosition = position;
		death = deathType;
		step = 0;
	}

	public boolean move() {
		step += GLOBAL.DEATH_ANIMATION_STEP;
		return step >= GLOBAL.DEATH_ANIMATION_LENGTH;
	}

	public void paint(Graphics2D g2d) {
		g2d.drawImage(map, 0, 0, GLOBAL.screenSize.width, GLOBAL.screenSize.height, null);
		
		Player.paintPlayer(g2d, 
					playerPosition.getXScreen(),
					playerPosition.getYScreen(),
					GLOBAL.playerScreenSize.width, 
					GLOBAL.playerScreenSize.height);
		
		switch (death) {
		case ENEMY:
			g2d.drawImage(explosion,
					playerPosition.getXScreen() - (int) (step * GLOBAL.screenUWidth) / 2,
					playerPosition.getYScreen() - (int) (step * GLOBAL.screenUHeight) / 2,
					(int) (step * GLOBAL.screenUWidth),
					(int) (step * GLOBAL.screenUHeight), null);
			break;
		case ELECTRIC:
			MapSquare.paintElectricity(g2d, 
					playerPosition.getXScreen() - GLOBAL.playerScreenSize.width / 2,
					playerPosition.getYScreen() - GLOBAL.playerScreenSize.height / 2, 
					GLOBAL.playerScreenSize.width * 2, 
					GLOBAL.playerScreenSize.height * 2,
					10);
		break;
		}
	}

	public static void load() {
		try {
			explosion = ImageIO.read(DeathAnimation.class.getResource("/Explosion.png"));
		} catch (IOException e) {}
	}
}
