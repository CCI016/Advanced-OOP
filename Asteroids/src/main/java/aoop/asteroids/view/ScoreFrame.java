package aoop.asteroids.view;

import aoop.asteroids.view.panels.ScorePanel;

import javax.swing.*;
import java.awt.*;

public class ScoreFrame extends JFrame {

	private static final Dimension SCORE_SIZE = new Dimension(800, 800);

	private static final String SCORE_TITLE = "Asteroid Score Board";

	private ScorePanel scorePanel;

	/**
	 * Constructor
	 */
	public ScoreFrame() {
		super(SCORE_TITLE);
		drawScoreGUI();
	}

	/**
	 * Method to initialize the Score frame.
	 */
	private void drawScoreGUI() {
		setSize(SCORE_SIZE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		scorePanel = new ScorePanel(this);
		setVisible(true);
	}

}
