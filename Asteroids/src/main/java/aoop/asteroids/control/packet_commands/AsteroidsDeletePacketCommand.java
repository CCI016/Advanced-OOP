package aoop.asteroids.control.packet_commands;

import aoop.asteroids.model.Game;

import java.util.Arrays;
import java.util.List;

public class AsteroidsDeletePacketCommand implements PacketCommand {

	private final Game game;

	private final String[] string;

	/**
	 * Constructor
	 * @param game the game
	 * @param string packet
	 */
	public AsteroidsDeletePacketCommand(Game game, String[] string) {
		this.game = game;
		this.string = string;
	}

	/**
	 * Overriding the method from the PacketCommand interface, to be able to delete duplicate asteroids.
	 */
	@Override
	public void executeCommand() {
		List list = Arrays.asList(string);
		game.getAsteroids().forEach((a) -> {
			if (!list.contains(a.getID())) {
				a.destroy();
			}
		});
	}
}
