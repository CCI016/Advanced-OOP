package aoop.asteroids.view.panels;

import aoop.asteroids.control.actions.ResetScoreAction;
import aoop.asteroids.control.game_database.DataBaseManager;
import aoop.asteroids.control.game_database.Score;
import aoop.asteroids.view.AsteroidsFrame;
import aoop.asteroids.view.ScoreFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScorePanel extends JPanel {


	private final ScoreFrame scoreFrame;

	private JTabbedPane tabbedPane;

	/**
	 * Constructor
	 * @param scoreFrame the frame where score will be displayed
	 */
	public ScorePanel(ScoreFrame scoreFrame) {
		this.scoreFrame = scoreFrame;
		this. createTabs();
		initScoreMenu();
	}

	/**
	 * Creating tabs for the score board.
	 */
	private void createTabs() {
		tabbedPane = new JTabbedPane();
		JPanel scorePanel = initScorePanel();
		tabbedPane.addTab("Score Board", null, scorePanel, "Player scores :");
		add(tabbedPane);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

	}

	/**
	 * Drawing the actual score panel.
	 * @return the score panel
	 */
	private JPanel initScorePanel() {
		JPanel scorePanel = new JPanel(new GridBagLayout());
		scorePanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		setSize(AsteroidsFrame.WINDOW_WIDTH + 50, AsteroidsFrame.WINDOW_HEIGHT + 40);
		scorePanel.setPreferredSize(new Dimension(AsteroidsFrame.WINDOW_WIDTH, AsteroidsFrame.WINDOW_HEIGHT));
		scorePanel.setSize(new Dimension(AsteroidsFrame.WINDOW_WIDTH, AsteroidsFrame.WINDOW_HEIGHT));

		scorePanel.add(setScores());

		return scorePanel;
	}

	/**
	 * Retrieving the scores from the database and arranging them on the score board.
	 * @return the score panel
	 */
	private JPanel setScores() {

		ArrayList<Score> playerScore = DataBaseManager.queryScoreboard();
		JPanel scorePanel;

		if (playerScore.size() == 0) {
			scorePanel = new JPanel(new BorderLayout());
			JLabel emptyLabel = new JLabel("Score Board is empty.");
			scorePanel.add(emptyLabel);
		} else {
			int numberOfRows = Math.min(playerScore.size(), 10);
			scorePanel = new JPanel(new GridLayout(numberOfRows, 1, 0, 10));
			for (int i = 0; i < numberOfRows; i++) {
				JPanel scoreRow = new JPanel(new FlowLayout());
				Score scores = playerScore.get(i);
				scoreRow.add(new JLabel((i + 1) + ":", JLabel.LEFT));
				scoreRow.add(new JLabel(scores.getName(), JLabel.LEFT));
				scoreRow.add(new JLabel(Integer.toString(scores.getScore()), JLabel.LEFT));
				scorePanel.add(scoreRow);
			}
		}
		return scorePanel;
	}

	/**
	 * Method used to create the menu of the frame.
	 */
	private void initScoreMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Scores");
		menu.add(new ResetScoreAction(this));
		menuBar.add(menu);
		scoreFrame.setJMenuBar(menuBar);
	}

	/**
	 * Method used to reset the scores.
	 */
	public void scoreReset() {
		DataBaseManager.deleteScores();
		this.remove(tabbedPane);
		this.createTabs();
		this.revalidate();
	}
}
