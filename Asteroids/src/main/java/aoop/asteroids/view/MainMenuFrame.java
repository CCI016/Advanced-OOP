package aoop.asteroids.view;

import aoop.asteroids.view.panels.*;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {

	private static final Dimension MENU_SIZE = new Dimension(800, 800);

	private static final String MENU_TITLE = "Asteroid Shooter";

	/**
	 * The panel responsible for ship colors.
	 */
	private ColorPanel colorPanel;

	/**
	 * The panel responsible for player connections.
	 */
	private ConnectionPanel connectionPanel;

	/**
	 * The panel responsible for ship name.
	 */
	private NamePanel namePanel;

	/**
	 * Constructor of main menu frame
	 */
	public MainMenuFrame() {
		super(MENU_TITLE);
		drawMenuGUI();
	}

	/**
	 * Method to draw the basic GUI of the menu
	 */
	private void drawMenuGUI() {
		setSize(MENU_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		addPanels(getContentPane());
		setVisible(true);
	}

	/**
	 * Add all the above panels to the frame
	 *
	 * @param panels the panels we used
	 */
	private void addPanels(Container panels) {
		panels.setLayout(new BoxLayout(panels, BoxLayout.PAGE_AXIS));
		colorPanel = new ColorPanel(panels);
		connectionPanel = new ConnectionPanel(panels);
		namePanel = new NamePanel(panels);
		new SinglePanel(this, panels);
		new GamePanel(this, panels);
	}

	/**
	 * Getter
	 *
	 * @return panel responsible for names
	 */
	public ColorPanel getColorPanel() {
		return colorPanel;
	}

	/**
	 * Getter
	 *
	 * @return panel responsible for names
	 */
	public ConnectionPanel getConnectionPanel() {
		return connectionPanel;
	}

	/**
	 * Getter
	 *
	 * @return panel responsible for names
	 */
	public NamePanel getNamePanel() {
		return namePanel;
	}
}
