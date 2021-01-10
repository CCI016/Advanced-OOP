package aoop.asteroids.control.actions;

import aoop.asteroids.view.panels.ScorePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ResetScoreAction extends AbstractAction {

	/**
	 * The panel where the score is displayed.
	 */
	private final ScorePanel scorePanel;

	/**
	 * Constructor
	 * @param scorePanel the panel where we display the score.
	 */
	public ResetScoreAction(ScorePanel scorePanel) {
		super("Reset scores");
		this.scorePanel = scorePanel;
	}

	/**
	 * Resets the score of the database.
	 * @param e event of the action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		scorePanel.scoreReset();
	}
}
