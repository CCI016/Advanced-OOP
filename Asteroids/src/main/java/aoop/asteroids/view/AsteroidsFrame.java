package aoop.asteroids.view;

import aoop.asteroids.control.NewGameAction;
import aoop.asteroids.control.PlayerKeyListener;
import aoop.asteroids.control.actions.KeyListenOnAsterFrame;
import aoop.asteroids.model.Game;

import javax.swing.*;
import java.awt.*;

/**
 * The main window that's used for displaying the game.
 */
public class AsteroidsFrame extends JFrame {
	/**
	 * The size of the window
	 */
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 800;
	/**
	 * The size that the window should be.
	 */
	public static final Dimension WINDOW_SIZE = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
	/**
	 * The title which appears in the upper border of the window.
	 */
	private static final String WINDOW_TITLE = "Asteroids";
	/**
	 * The game model.
	 */
	private final Game game;

	/**
	 * Constructs the game's main window.
	 *
	 * @param game The game model that this window will show.
	 */
	public AsteroidsFrame(Game game) {
		this.game = game;
		initSwingUI();
	}

	/**
	 * A helper method to do the tedious task of initializing the Swing UI components.
	 */
	private void initSwingUI() {
		// Basic frame properties.
		setTitle(WINDOW_TITLE);
		setSize(WINDOW_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		if (!this.game.isSpectator()) {
			addKeyListener(new PlayerKeyListener(game));
		}

		addKeyListener(new KeyListenOnAsterFrame(this));

		// Creating the menuBar for the game
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Game");
		menuBar.add(menu);
		menu.add(new NewGameAction(game));
		setJMenuBar(menuBar);

		add(new AsteroidsPanel(game));
		setVisible(true);
	}

	/**
	 * Getter that returns the current game in the frame
	 */
	public Game getGame() {
		return game;
	}
}
