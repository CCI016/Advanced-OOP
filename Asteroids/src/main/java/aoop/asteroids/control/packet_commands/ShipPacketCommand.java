package aoop.asteroids.control.packet_commands;

import aoop.asteroids.model.Game;
import aoop.asteroids.model.Spaceship;

import java.awt.*;
import java.util.Scanner;

public class ShipPacketCommand implements PacketCommand {

	private final Game game;

	private final String[] string;

	/**
	 * Constructor
	 * @param game the game.
	 * @param string the packet.
	 */
	public ShipPacketCommand(Game game, String[] string) {
		this.game = game;
		this.string = string;
	}

	/**
	 * Method to check if the ship is new, parameters are self explanatory.
	 * @return true if new, otherwise false.
	 */
	private boolean isShipNew(String ID, double direction, double locationX, double locationY, double velocityX,
							  double velocityY, boolean isAccelerating, boolean isDestroyed) {
		//TODO Aici phd va trebui de facut si huineaua ceea cu compararea de mainship din joc
		for (Spaceship spaceship : this.game.getSpaceships()) {

			if (spaceship.getID().equals(ID)) {

				spaceship.setNewDirection(direction);
				spaceship.setAccelerateKeyPressed(isAccelerating);
				spaceship.spectateImplementation(velocityX, velocityY, locationX, locationY);

				if (isDestroyed) {
					spaceship.destroy();
				}

				return false;
			}

		}
		return true;
	}

	/**
	 * Interpreting the packet, and sorting the packet info into a spaceship.
	 */
	@Override
	public void executeCommand() {
		double pointX = Double.parseDouble(string[3]);
		double pointY = Double.parseDouble(string[4]);
		double velocityX = Double.parseDouble(string[5]);
		double velocityY = Double.parseDouble(string[6]);
		boolean isAccelerating = Boolean.parseBoolean(string[7]);
		double shipDirection = Double.parseDouble(string[8]);
		boolean destroyed = Boolean.parseBoolean(string[9]);
		boolean shipNew = isShipNew(string[2], shipDirection, pointX, pointY, velocityX,
				velocityY, isAccelerating, destroyed);

		if (shipNew && (this.game.getSpaceship() == null || !this.game.getSpaceship().getID().equals(string[2]))) {
			Spaceship ship = new Spaceship();
			ship.setUniqueID(string[2]);
			ship.setPlayerNick(string[10]);

			Scanner sc = new Scanner(string[11]);
			sc.useDelimiter("\\D+");
			Color color = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());

			ship.setShipColor(color);
			ship.setAccelerateKeyPressed(isAccelerating);
			this.game.addSpaceship(ship);
		}
	}
}
