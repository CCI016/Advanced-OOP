package aoop.asteroids.networking;

import aoop.asteroids.control.packet_commands.CommandHandler;
import aoop.asteroids.model.Asteroid;
import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.Spaceship;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PacketHandler {
	private final static String splitter = "@";
	private static final int SIZE = 256;

	/**
	 * Method to handle the commands from the packets.
	 */
	protected synchronized static void updateGame(Game game, String[] packet) {
		CommandHandler.handleCommand(game, packet);
	}

	/**
	 * Method to send the packets.
	 */
	public static void send(DatagramSocket ds, ConnectionDetails receiverDetails, byte[] data) {
		InetAddress ip = receiverDetails.getIp();
		int port = receiverDetails.getPort();
		DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
		try {
			ds.send(packet);
		} catch (IOException e) {
			System.err.println("An error occurred when trying to send the packet");
		}
	}

	/**
	 * Whenever a new player will connect to the serve, it will inform it about it's ip and it's port;
	 * This function prepares this packet;
	 */
	public static byte[] prepareJoinPacket() {
		String packet = "JOIN" + splitter;
		return packet.getBytes();
	}

	/**
	 * Prepares the disconnection packet;
	 */
	public static byte[] prepareDisconnectPacket() {
		String packet = "DISCONNECT" + splitter;
		return packet.getBytes();
	}

	/**
	 * The server will send end to the players.
	 */
	public static byte[] prepareEndPacket() {
		String packet = "END" + splitter;
		return packet.getBytes();
	}

	/**
	 * Transforms a byte packet to an string
	 */
	public static String fromByteToString(byte[] packet) {
		return new String(packet);
	}

	/**
	 * Transforms a ship into a byte array
	 */
	public static byte[] getShipPacket(Spaceship ship) {
		String s =
				"MODEL" + splitter
						+ "ShipUpdate" + splitter
						+ ship.getID() + splitter
						+ ship.getLocation().x + splitter
						+ ship.getLocation().y + splitter
						+ ship.getVelocity().x + splitter
						+ ship.getVelocity().y + splitter
						+ ship.isAccelerating() + splitter
						+ ship.getDirection() + splitter
						+ ship.isDestroyed() + splitter
						+ ship.getShipMaster() + splitter
						+ ship.getShipColor().toString();
		return s.getBytes();
	}

	/**
	 * This method prepares a bullet to be sent to the server or players
	 */
	protected byte[] getBulletPacket(Bullet gameObject) {
		String s =
				"MODEL" + splitter +
						"BulletUpdate" + splitter +
						gameObject.getID() + splitter +
						gameObject.getLocation().x + splitter +
						gameObject.getLocation().y + splitter +
						gameObject.getVelocity().x + splitter +
						gameObject.getVelocity().y + splitter +
						gameObject.getStepsLeft() + splitter +
						gameObject.getMasterShipId();
		return s.getBytes();
	}

	/**
	 * This method prepares an asteroid to be sent to server or players
	 */
	protected byte[] getAsteroidPacket(Asteroid gameObject) {
		String s =
				"MODEL" + splitter +
						"UpdateAsteroids" + splitter +
						gameObject.getID() + splitter +
						gameObject.getLocation().x + splitter +
						gameObject.getLocation().y + splitter +
						gameObject.getVelocity().x + splitter +
						gameObject.getVelocity().y + splitter +
						gameObject.getRadius() + splitter +
						gameObject.isDestroyed();
		return s.getBytes();
	}

	/**
	 * Method to receive the packets.
	 */
	public DatagramPacket receive(DatagramSocket ds) {
		byte[] data = new byte[SIZE];
		DatagramPacket packet = new DatagramPacket(data, data.length);

		try {
			ds.receive(packet);
			return packet;
		} catch (IOException e) {
			System.err.println("An error occurred when trying to receive packet");
		}
		return null;
	}
}
