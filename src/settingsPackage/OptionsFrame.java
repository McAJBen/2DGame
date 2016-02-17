package settingsPackage;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		mapX.addActionListener(new mapXListener());
		panel.add(new JLabel("Map start X"));
		panel.add(mapX);
		
		mapY = new JTextField(GLOBAL.MAP_START_Y + "", 1);
		mapY.addActionListener(new mapYListener());
		panel.add(new JLabel("Map start Y"));
		panel.add(mapY);
		
		playerX = new JTextField(GLOBAL.PLAYER_ORIGINAL_POSITION_X + "", 1);
		playerX.addActionListener(new playerXListener());
		panel.add(new JLabel("Player start X"));
		panel.add(playerX);
		
		playerY = new JTextField(GLOBAL.PLAYER_ORIGINAL_POSITION_Y + "", 1);
		playerY.addActionListener(new playerYListener());
		panel.add(new JLabel("Player start Y"));
		panel.add(playerY);
		
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
	
	private class mapXListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GLOBAL.setMapStartX(Integer.parseInt(mapX.getText()));
    	}
    }
	
	private class mapYListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GLOBAL.setMapStartY(Integer.parseInt(mapY.getText()));
    	}
    }
	
	private class playerXListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GLOBAL.setPlayerOriginalPositionX(Integer.parseInt(playerX.getText()));
    	}
    }
	
	private class playerYListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GLOBAL.setPlayerOriginalPositionY(Integer.parseInt(playerY.getText()));
    	}
    }
	
	private class restartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MainJPanel.setRestart();
		}
	}
	
	
}

