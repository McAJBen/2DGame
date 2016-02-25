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
		mapX.addFocusListener(new shortListener(SettingName.MAP_START_X));
		panel.add(new JLabel("Map start X"));
		panel.add(mapX);
		
		mapY = new JTextField(GLOBAL.MAP_START_Y + "", 1);
		mapY.addFocusListener(new shortListener(SettingName.MAP_START_Y));
		panel.add(new JLabel("Map start Y"));
		panel.add(mapY);
		
		playerX = new JTextField(GLOBAL.PLAYER_ORIGINAL_POSITION_X + "", 1);
		playerX.addFocusListener(new shortListener(SettingName.PLAYER_ORIGINAL_POSITION_X));
		panel.add(new JLabel("Player start X"));
		panel.add(playerX);
		
		playerY = new JTextField(GLOBAL.PLAYER_ORIGINAL_POSITION_Y + "", 1);
		playerY.addFocusListener(new shortListener(SettingName.PLAYER_ORIGINAL_POSITION_Y));
		panel.add(new JLabel("Player start Y"));
		panel.add(playerY);
		
		maxCoins = new JTextField(GLOBAL.MAX_COINS + "", 1);
		maxCoins.addFocusListener(new intListener(SettingName.MAX_COINS));
		panel.add(new JLabel("Max coins"));
		panel.add(maxCoins);
		
		fullscreen = new JCheckBox("Fullscreen", GLOBAL.FULLSCREEN);
		fullscreen.addActionListener(new booleanListener(SettingName.FULLSCREEN));
		panel.add(fullscreen);
    	panel.add(new JLabel());
    	
    	debugMode = new JCheckBox("Debug Mode", GLOBAL.DEBUG_MODE);
    	debugMode.addActionListener(new booleanListener(SettingName.DEBUG_MODE));
		panel.add(debugMode);
		
		JButton restartButton = new JButton("Restart");
		restartButton.addActionListener(new restartListener());
		panel.add(restartButton);
		
		return panel;
	}
	
	private class booleanListener implements ActionListener {
		SettingName name;
		public booleanListener(SettingName name) {
			this.name = name;
		}
		public void actionPerformed(ActionEvent e) {
			GLOBAL.addSetting(name, ((JCheckBox) e.getSource()).isSelected());
    	}
    }
	
	private class shortListener implements FocusListener {
		SettingName name;
		public shortListener(SettingName name) {
			this.name = name;
		}
		public void focusGained(FocusEvent arg0) {}
		public void focusLost(FocusEvent e) {
			GLOBAL.addSetting(name, Short.parseShort(((JTextField) e.getSource()).getText()));
		}
    }
	
	private class intListener implements FocusListener {
		SettingName name;
		public intListener(SettingName name) {
			this.name = name;
		}
		public void focusGained(FocusEvent arg0) {}
		public void focusLost(FocusEvent e) {
			GLOBAL.addSetting(name, Integer.parseInt(((JTextField) e.getSource()).getText()));
		}
    }
	
	private class restartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MainJPanel.setRestart();
		}
	}
}

