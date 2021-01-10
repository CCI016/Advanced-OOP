package aoop.asteroids.control.actions;

import aoop.asteroids.networking.Player;
import aoop.asteroids.networking.Server;
import aoop.asteroids.view.MainMenuFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiPHostAction implements ActionListener {

	/**
	 * The frame of the main menu
	 */
	private final MainMenuFrame menuFrame;

	/**
	 * Constructor
	 *
	 * @param menuFrame the frame of the main menu
	 */
	public MultiPHostAction(MainMenuFrame menuFrame) {
		this.menuFrame = menuFrame;
	}

	/**
	 * Prepares the server to host a game, creates a player (host) that will be in this game,
	 * and starts the server and the player threads.
	 * @param e event of the action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Server server = new Server(menuFrame.getConnectionPanel().getPort());
		Player player = new Player(menuFrame.getConnectionPanel().getPort(), server.getServersAddress(), false);
		player.getGame().getSpaceship().setShipColor(menuFrame.getColorPanel().getColor());
		player.getGame().getSpaceship().setPlayerNick(menuFrame.getNamePanel().getName());
		menuFrame.dispose();
		Thread t = new Thread(server);
		t.start();
		Thread pt = new Thread(player);
		pt.start();
	}
}
