package aoop.asteroids.view.panels;

import aoop.asteroids.control.buttons.ScoreButton;
import aoop.asteroids.control.buttons.SpectateButton;
import aoop.asteroids.view.MainMenuFrame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

	/**
	 * Draws the buttons in the main menu
	 */
	public GamePanel(MainMenuFrame menuFrame, Container gameContainer) {
		setBackground(Color.black);
		setAlignmentX(Component.CENTER_ALIGNMENT);

		add(new SpectateButton(menuFrame));
		add(new ScoreButton(menuFrame));

		gameContainer.add(this);
	}
}
