package aoop.asteroids.control.buttons;

import aoop.asteroids.control.actions.SpectateAction;
import aoop.asteroids.view.MainMenuFrame;

import javax.swing.*;

public class SpectateButton extends JButton {

	/**
	 * Creates a button for joining a game as a spectator
	 */
	public SpectateButton(MainMenuFrame menuFrame) {
		super("Spectate game");
		addActionListener(new SpectateAction(menuFrame));
	}
}
