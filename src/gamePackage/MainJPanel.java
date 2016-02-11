package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import animationPackage.DeathAnimation;
import mapPackage.MapSquare;
import settingsPackage.GLOBAL;

@SuppressWarnings("serial")
public class MainJPanel extends JPanel {
	
	private Game game;
	
	public MainJPanel() {
		game = new Game();
	}

	public static void main(String[] args) {
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
    }
	
	private static void load() {
		GLOBAL.setSettings();
		Player.load();
		DeathAnimation.load();
		MapSquare.load();
	}

	private KeyListener getKeyListener() {
		return game.getKeyListener();
	}

	private void start() {
		Thread repaintThread = new Thread("repaintThread") {
		 	@Override
			public void run() {
	 			long startTime = System.currentTimeMillis() + GLOBAL.FRAME_WAIT_MS;
	 			while (!isInterrupted()) {
	 				repaint();
	 				while (System.currentTimeMillis() < startTime) {
	 					try {
	 						sleep(1);
	 					} catch (InterruptedException e) {}
	 				}
	 				startTime += GLOBAL.FRAME_WAIT_MS;
				}
			}
		};
		repaintThread.start();
		game.start();
	}
	
	public void paint(Graphics g) {
        super.paint(g);
        game.paint(g);
    }
}
