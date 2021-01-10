package aoop.asteroids.control.buttons;

import aoop.asteroids.control.actions.MultiPJoinAction;
import aoop.asteroids.view.MainMenuFrame;

import javax.swing.*;

public class MultiPJoinButton extends JButton {

	/**
	 * Creates the button for joining the multiplayer game
	 */
	public MultiPJoinButton(MainMenuFrame menuFrame) {
		super("Join Multiplayer game");
		addActionListener(new MultiPJoinAction(menuFrame));
	}
}
