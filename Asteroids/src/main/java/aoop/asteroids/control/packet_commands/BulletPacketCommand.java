package aoop.asteroids.control.packet_commands;

import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Game;

public class BulletPacketCommand implements PacketCommand {

	private final Game game;

	private final String[] string;

	/**
	 * Constructor
	 * @param game the game.
	 * @param string the packet.
	 */
	public BulletPacketCommand(Game game, String[] string) {
		this.game = game;
		this.string = string;
	}

	/**
	 * Method to check if the bullet is new.
	 * @param inputID id of the bullet
	 * @return true or false
	 */
	private boolean isBulletNew(String inputID) {
		for (Bullet bullet : game.getBullets()) {
			if (bullet.getID().equals(inputID)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Interpreting the packet, and sorting the packet info into a bullet.
	 */
	@Override
	public void executeCommand() {
		double pointX = Double.parseDouble(string[3]);
		double pointY = Double.parseDouble(string[4]);
		double velocityX = Double.parseDouble(string[5]);
		double velocityY = Double.parseDouble(string[6]);
		int bulletSteps = Integer.parseInt(string[7]);
		if (isBulletNew(string[2])) {
			Bullet bullet = new Bullet(pointX, pointY, velocityX, velocityY, string[2]);
			bullet.setUniqueID(string[2]);
			bullet.setStepsLeft(bulletSteps);
			game.getBullets().add(bullet);
		}
	}
}
