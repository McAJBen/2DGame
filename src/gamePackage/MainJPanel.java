package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import animationPackage.DeathAnimation;
import mapPackage.Coin;
import settingsPackage.GLOBAL;

@SuppressWarnings("serial")
public class MainJPanel extends JPanel {
	
	private Game game;
	private static boolean restart = false;
	
	public MainJPanel() {
		game = new Game();
	}

	public static void main(String[] args) {
		do {
			restart = false;
			load();
	        JFrame frame = new JFrame("2DGame");
	        MainJPanel imageEvolutionJPanel = new MainJPanel();
	        frame.add(imageEvolutionJPanel);
	        frame.addKeyListener(imageEvolutionJPanel.getKeyListener());
	        frame.addComponentListener(new ComponentListener() {
	            public void componentResized(ComponentEvent e) {
	            	Dimension size = frame.getSize();
	            	if (!GLOBAL.FULLSCREEN) {
		            	size.width -= GLOBAL.SCREEN_OFFSET.width;
		            	size.height -= GLOBAL.SCREEN_OFFSET.height;
	            	}
	            	GLOBAL.setWindowSize(size);
	            }
				public void componentHidden(ComponentEvent arg0) {}
				public void componentMoved(ComponentEvent arg0) {}
				public void componentShown(ComponentEvent arg0) {}
	        });
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        if (GLOBAL.FULLSCREEN) {
	        	frame.setUndecorated(true);
	        	frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
	        }
	        else {
	        	frame.setSize(GLOBAL.SCREEN_SIZE.width + GLOBAL.SCREEN_OFFSET.width, GLOBAL.SCREEN_SIZE.height + GLOBAL.SCREEN_OFFSET.height);
	        }
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	        frame.requestFocus();
	        imageEvolutionJPanel.start();
	        
	        // when restart == true
	        frame.dispose();
	        imageEvolutionJPanel = null;
		} while (restart);
    }
	
	private static void load() {
		GLOBAL.setSettings();
		Player.load();
		DeathAnimation.load();
		Coin.load();
	}

	private KeyListener getKeyListener() {
		return game.getKeyListener();
	}

	private void start() {
		
		game.start();
		
		long startTime = System.currentTimeMillis() + GLOBAL.FRAME_WAIT_MS;
		while (!restart) {
			repaint();
			while (System.currentTimeMillis() < startTime) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {}
			}
			startTime += GLOBAL.FRAME_WAIT_MS;
		}
		game.stop();
	}
	
	public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        game.paint(g2d);
    }
	
	public static void setRestart() {
		restart = true;
	}
}
