package aoop.asteroids.control.actions;

import aoop.asteroids.view.MainMenuFrame;
import aoop.asteroids.view.ScoreFrame;
import aoop.asteroids.view.panels.ScorePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ScoreAction extends AbstractAction {

	/**
	 * The frame of the main menu.
	 */
	private final MainMenuFrame menuFrame;

	/**
	 * Panel where the score is displayed.
	 */
	private ScorePanel scorePanel;

	public ScoreAction(MainMenuFrame menuFrame) {
		this.menuFrame = menuFrame;
	}

	/**
	 * Adding the scorepanel to the scoreframe.
	 * @param e event of the action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ScoreFrame scoreFrame = new ScoreFrame();
		scorePanel = new ScorePanel(scoreFrame);
		scoreFrame.add(scorePanel);
	}
}
