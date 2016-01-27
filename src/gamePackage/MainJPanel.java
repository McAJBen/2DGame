package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainJPanel extends JPanel {

	private static final Dimension 
					SCREEN_SIZE = new Dimension(500, 500),
					SCREEN_OFFSET = new Dimension(7, 30);
	private static final long
					FRAME_WAIT_MS = 20; // FPS = 50
	
	private GameKeyboardListener keyListener = new GameKeyboardListener();
	private Thread repaintThread;
	
	public MainJPanel() {
		
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
        frame.requestFocus();
        imageEvolutionJPanel.start();
    }
	
	private KeyListener getKeyListener() {
		return keyListener;
	}

	private void start() {
		Thread repaintThread = new Thread("repaintThread") {
		 	@Override
			public void run() {
	 			long runTime = System.currentTimeMillis();
	 			double timesRan = 0;
	 			long startTime = System.currentTimeMillis() + FRAME_WAIT_MS;
	 			while (!isInterrupted()) {
	 				repaint();
	 				timesRan++;
	 				while (System.currentTimeMillis() < startTime) {
	 					try {
	 						sleep(1);
	 					} catch (InterruptedException e) {}
	 				}
	 				System.out.println(timesRan / (System.currentTimeMillis() - runTime) * 1_000);
	 				startTime += FRAME_WAIT_MS;
				}
			}
		};
		repaintThread.start();
		
		
		
		
	}
	
	public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
