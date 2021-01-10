package aoop.asteroids.control.actions;

import aoop.asteroids.networking.Player;
import aoop.asteroids.view.MainMenuFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MultiPJoinAction implements ActionListener {
	/**
	 * The frame of the main menu
	 */
	private final MainMenuFrame menuFrame;

	/**
	 * Constructor
	 *
	 * @param menuFrame the frame of the main menu
	 */
	public MultiPJoinAction(MainMenuFrame menuFrame) {
		this.menuFrame = menuFrame;
	}

	/**
	 * Reads the input port, connects the player to the server,using the port given, with the color and name he chose.
	 * @param e event of the action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = JOptionPane.showInputDialog("Enter port: ");
		int port = Integer.parseInt(s);
		try {
			InetAddress address = InetAddress.getLocalHost();
			Player player = new Player(port, address, false);
			player.getGame().getSpaceship().setShipColor(menuFrame.getColorPanel().getColor());
			player.getGame().getSpaceship().setPlayerNick(menuFrame.getNamePanel().getName());
			Thread playerThread = new Thread(player);
			playerThread.start();
			menuFrame.dispose();
		} catch (UnknownHostException ex) {
			System.err.println("Couldn't join multiplayer game.");
		}
	}
}
