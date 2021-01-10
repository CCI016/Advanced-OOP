package aoop.asteroids.control.buttons;

import aoop.asteroids.control.actions.SinglePAction;
import aoop.asteroids.view.MainMenuFrame;

import javax.swing.*;

public class SinglePButton extends JButton {

	/**
	 * Creates a button for starting a singleplayer game
	 */
	public SinglePButton(MainMenuFrame menuFrame) {
		super("Single player campaign");
		this.addActionListener(new SinglePAction(menuFrame));
	}
}
