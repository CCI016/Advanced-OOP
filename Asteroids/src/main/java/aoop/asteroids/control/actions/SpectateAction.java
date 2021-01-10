package aoop.asteroids.control.actions;

import aoop.asteroids.networking.Player;
import aoop.asteroids.view.MainMenuFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SpectateAction implements ActionListener {

	/**
	 * The frame of the main menu.
	 */
	private final MainMenuFrame menuFrame;

	/**
	 * Constructor
	 *
	 * @param menuFrame The frame of the main menu.
	 */
	public SpectateAction(MainMenuFrame menuFrame) {
		this.menuFrame = menuFrame;
	}


	/**
	 * Connects a spectator to the server, using the input port.
	 * @param e event of the action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = JOptionPane.showInputDialog("Enter port: ");
		int port = Integer.parseInt(s);
		try {
			InetAddress address = InetAddress.getByName(menuFrame.getConnectionPanel().getIP());
			Player player = new Player(port, address, true);
			menuFrame.dispose();
			Thread spectator = new Thread(player);
			spectator.start();
		} catch (UnknownHostException ex) {
			System.err.println("Couldn't join the multiplayer game to spectate.");
		}
	}
}
