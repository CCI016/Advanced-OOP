package aoop.asteroids.control.packet_commands;

import aoop.asteroids.model.Asteroid;
import aoop.asteroids.model.AsteroidSize;
import aoop.asteroids.model.Game;

import java.awt.*;

public class AsteroidsPacketCommand implements PacketCommand {

	private final Game game;

	private final String[] string;

	/**
	 * Constructor
	 * @param game the game
	 * @param string the packet.
	 */
	public AsteroidsPacketCommand(Game game, String[] string) {
		this.game = game;
		this.string = string;
	}

	/**
	 * Method to check if asteroid is new.
	 * @param ID id of asteroid
	 * @param isDestroyed boolean that indicates whether the asteroid is destroyed or not
	 * @return true or false.
	 */
	private boolean isAsteroidNew(String ID, boolean isDestroyed) {
		for (Asteroid a : this.game.getAsteroids()) {
			if (a.getID().equals(ID)) {
				if (isDestroyed) {
					a.destroy();
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * Interpreting the packet, and sorting the packet info into an asteroid.
	 */
	@Override
	public void executeCommand() {
		double pointX = Double.parseDouble(string[3]);
		double pointY = Double.parseDouble(string[4]);
		double velocityX = Double.parseDouble(string[5]);
		double velocityY = Double.parseDouble(string[6]);
		boolean destroyed = Boolean.parseBoolean(string[7]);
		AsteroidSize radius = AsteroidSize.getAsteroidSize((int) Double.parseDouble(string[7]));
		if (isAsteroidNew(string[2], destroyed)) {
			Point.Double velocity = new Point.Double(velocityX, velocityY);
			Point.Double location = new Point.Double(pointX, pointY);
			Asteroid asteroid = new Asteroid(location, velocity, radius);
			asteroid.setUniqueID(string[2]);
			game.getAsteroids().add(asteroid);
		}
	}
}