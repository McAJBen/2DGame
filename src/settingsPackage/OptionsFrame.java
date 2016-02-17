package settingsPackage;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gamePackage.MainJPanel;

@SuppressWarnings("serial")
public class OptionsFrame extends JFrame {
	
	JCheckBox debugMode;
	JCheckBox fullscreen;
	JTextField mapX;
	JTextField mapY;
	JTextField playerX;
	JTextField playerY;
	JTextField maxCoins;
	
	public OptionsFrame() {
        super();
        setTitle("Options");
        setSize(300, 300);
        add(buildPanel());
        setResizable(false);
        setVisible(true);
    }
	
	private JPanel buildPanel() {
    	JPanel panel;
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(getPanel());
        return panel;
    }
	
	private JPanel getPanel() {
    
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		
		mapX = new JTextField(GLOBAL.MAP_START_X + "", 1);
		mapX.addFocusListener(new mapXListener());
		panel.add(new JLabel("Map start X"));
		panel.add(mapX);
		
		mapY = new JTextField(GLOBAL.MAP_START_Y + "", 1);
		mapY.addFocusListener(new mapYListener());
		panel.add(new JLabel("Map start Y"));
		panel.add(mapY);
		
		playerX = new JTextField(GLOBAL.PLAYER_ORIGINAL_POSITION_X + "", 1);
		playerX.addFocusListener(new playerXListener());
		panel.add(new JLabel("Player start X"));
		panel.add(playerX);
		
		playerY = new JTextField(GLOBAL.PLAYER_ORIGINAL_POSITION_Y + "", 1);
		playerY.addFocusListener(new playerYListener());
		panel.add(new JLabel("Player start Y"));
		panel.add(playerY);
		
		maxCoins = new JTextField(GLOBAL.MAX_COINS + "", 1);
		maxCoins.addFocusListener(new maxCoinsListener());
		panel.add(new JLabel("Max coins"));
		panel.add(maxCoins);
		
		fullscreen = new JCheckBox("Fullscreen", GLOBAL.FULLSCREEN);
		fullscreen.addActionListener(new fullscreenListener());
		panel.add(fullscreen);
    	panel.add(new JLabel());
    	
    	debugMode = new JCheckBox("Debug Mode", GLOBAL.DEBUG_MODE);
    	debugMode.addActionListener(new debugModeListener());
		panel.add(debugMode);
		
		JButton restartButton = new JButton("Restart");
		restartButton.addActionListener(new restartListener());
		panel.add(restartButton);
		
		return panel;
	}
	
	private class fullscreenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GLOBAL.setFullscreen(fullscreen.isSelected());
    	}
    }
	
	private class debugModeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GLOBAL.setDebugMode(debugMode.isSelected());
    	}
    }
	
	private class mapXListener implements FocusListener {
		public void focusGained(FocusEvent arg0) {}
		public void focusLost(FocusEvent e) {
			GLOBAL.setMapStartX(Integer.parseInt(mapX.getText()));
		}
    }
	private class mapYListener implements FocusListener {
		public void focusGained(FocusEvent arg0) {}
		public void focusLost(FocusEvent e) {
			GLOBAL.setMapStartY(Integer.parseInt(mapY.getText()));
		}
    }
	
	private class playerXListener implements FocusListener {
		public void focusGained(FocusEvent arg0) {}
		public void focusLost(FocusEvent e) {
			GLOBAL.setPlayerOriginalPositionX(Integer.parseInt(playerX.getText()));
		}
    }
	
	private class playerYListener implements FocusListener {
		public void focusGained(FocusEvent arg0) {}
		public void focusLost(FocusEvent e) {
			GLOBAL.setPlayerOriginalPositionY(Integer.parseInt(playerY.getText()));
		}
    }
	
	private class maxCoinsListener implements FocusListener {
		public void focusGained(FocusEvent arg0) {}
		public void focusLost(FocusEvent e) {
			GLOBAL.setMaxCoins(Integer.parseInt(maxCoins.getText()));
		}
    }
	
	private class restartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MainJPanel.setRestart();
		}
	}
}

