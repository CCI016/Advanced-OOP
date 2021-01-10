package aoop.asteroids.control.buttons;

import aoop.asteroids.control.actions.MultiPHostAction;
import aoop.asteroids.view.MainMenuFrame;

import javax.swing.*;

public class MultiPHostButton extends JButton {

	/**
	 * Creates a button for hosting a multiplayer game
	 */
	public MultiPHostButton(MainMenuFrame menuFrame) {
		super("Host Multiplayer game");
		addActionListener(new MultiPHostAction(menuFrame));
	}
}
