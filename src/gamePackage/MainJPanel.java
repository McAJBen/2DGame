package gamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainJPanel extends JPanel {

	private static final Dimension 
					SCREEN_SIZE = new Dimension(500, 500),
					SCREEN_OFFSET = new Dimension(7, 30);
	
	public MainJPanel() {
		
	}

	public static void main(String[] args) {
        JFrame frame = new JFrame("2DGame");
        MainJPanel imageEvolutionJPanel = new MainJPanel();
        frame.add(imageEvolutionJPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        SettingsHandler sh = new SettingsHandler();
        if ((boolean) sh.getSetting("FULLSCREEN")) {
        	frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        	frame.setUndecorated(true);
        	frame.requestFocus();
        	frame.repaint();
        }
        else {
        	frame.setSize(SCREEN_SIZE.width + SCREEN_OFFSET.width, SCREEN_SIZE.height + SCREEN_OFFSET.height);
        }
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.requestFocus();
        imageEvolutionJPanel.start();
    }
	
	private void start() {
        
	}
	
	public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
    }
}
