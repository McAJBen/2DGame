package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainJPanel extends JPanel {

	private static final Dimension 
					SCREEN_SIZE = new Dimension(500, 500),
					SCREEN_OFFSET = new Dimension(17, 40);
	private static final long
					FRAME_WAIT_MS = 20; // FPS = 50
	
	private Game game;
	
	public MainJPanel() {
		game = new Game(SCREEN_SIZE);
		
	}

	public static void main(String[] args) {
        JFrame frame = new JFrame("2DGame");
        MainJPanel imageEvolutionJPanel = new MainJPanel();
        frame.add(imageEvolutionJPanel);
        frame.addKeyListener(imageEvolutionJPanel.getKeyListener());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        SettingsHandler sh = new SettingsHandler();
        if ((boolean) sh.getSetting("FULLSCREEN")) {
        	frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        	frame.setUndecorated(true);
        }
        else {
        	frame.setSize(SCREEN_SIZE.width + SCREEN_OFFSET.width, SCREEN_SIZE.height + SCREEN_OFFSET.height);
        }
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
            	Dimension size = frame.getSize();
            	size.width -= SCREEN_OFFSET.width;
            	size.height -= SCREEN_OFFSET.height;
                imageEvolutionJPanel.game.changeWindowSize(size);       
            }
			public void componentHidden(ComponentEvent arg0) {}
			public void componentMoved(ComponentEvent arg0) {}
			public void componentShown(ComponentEvent arg0) {}
        });
        frame.requestFocus();
        imageEvolutionJPanel.start();
    }
	
	private KeyListener getKeyListener() {
		return game.getKeyListener();
	}

	private void start() {
		Thread repaintThread = new Thread("repaintThread") {
		 	@Override
			public void run() {
	 			long startTime = System.currentTimeMillis() + FRAME_WAIT_MS;
	 			while (!isInterrupted()) {
	 				repaint();
	 				while (System.currentTimeMillis() < startTime) {
	 					try {
	 						sleep(1);
	 					} catch (InterruptedException e) {}
	 				}
	 				startTime += FRAME_WAIT_MS;
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
