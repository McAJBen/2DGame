package settingsPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class OptionsMenu {
	
	private Color backgroundColor;
	private Font font;
	private short step;
	private boolean stepDirection;
	private boolean wasClosed;
	
	public OptionsMenu() {
		backgroundColor = new Color(0, 0, 0, 100);
		font = new Font("Arial", Font.PLAIN, 40);
		step = 0;
		stepDirection = true;
		wasClosed = false;
	}
	
	public void paintPause(Graphics g) {
		move();
		
		g.setColor(backgroundColor);
		g.fillRect(0, 0, GLOBAL.screenSize.width, GLOBAL.screenSize.height);
		g.setColor(new Color(step, step, step));
		g.setFont(font);
		g.drawString("PAUSED", GLOBAL.optionsTextPosition.width, GLOBAL.optionsTextPosition.height);
	}
	
	public void paintOptions(Graphics g) {
		paintPause(g);
		// TODO change to options paused
	}
	
	private void move() {
		step += stepDirection ? 1 : -1;
		if (step > 150) {
			stepDirection = false;
		}
		else if (step < 50) {
			stepDirection = true;
		}
	}
	
	public void openOptionsMenu() {
		Thread optionsThread = new Thread("optionsThread") {
		 	@Override
			public void run() {
		 		OptionsFrame of = new OptionsFrame();
		 		try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		 		while (of.isActive()) {
		 			// do wait for exit
		 		}
		 		of.setEnabled(false);
		 		of.dispose();
		 		wasClosed = true;
			}
		};
		optionsThread.start();
	}

	public boolean wasClosed() {
		if (wasClosed) {
			wasClosed = false;
			return true;
		}
		return false;
	}
}
