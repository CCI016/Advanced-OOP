package aoop.asteroids.control.buttons;

import aoop.asteroids.control.actions.ScoreAction;
import aoop.asteroids.view.MainMenuFrame;
import aoop.asteroids.view.panels.ScorePanel;

import javax.swing.*;

public class ScoreButton extends JButton {

	private ScorePanel scorePanel;

	/**
	 * Creates a button to be able to view the user scoreboard
	 *
	 * @param menuFrame the menu frame of the game
	 */
	public ScoreButton(MainMenuFrame menuFrame) {
		super("Scoreboard");
		addActionListener(new ScoreAction(menuFrame));
	}
}
