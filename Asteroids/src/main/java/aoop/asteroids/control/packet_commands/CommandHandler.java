package aoop.asteroids.control.packet_commands;

import aoop.asteroids.model.Game;

public class CommandHandler {

	//Empty constructor
	public CommandHandler() {
	}

	/**
	 * Method to handle the incoming commands
	 * @param game the actual game.
	 * @param packet the packet to handle.
	 */
	public static void handleCommand(Game game, String[] packet) {
		if (packet[1].equals("ShipUpdate")) {
			new ShipPacketCommand(game, packet).executeCommand();
		} else if (packet[1].equals("UpdateAsteroids")) {
			new AsteroidsPacketCommand(game, packet).executeCommand();
		} else if (packet[1].equals("BulletUpdate")) {
			new BulletPacketCommand(game, packet).executeCommand();
		}
	}

}
