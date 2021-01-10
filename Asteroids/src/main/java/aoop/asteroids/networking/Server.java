package aoop.asteroids.networking;

import aoop.asteroids.game_observer.GameUpdateListener;
import aoop.asteroids.model.Asteroid;
import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.Spaceship;

import java.net.*;
import java.util.ArrayList;

public class Server extends PacketHandler implements Runnable, GameUpdateListener {
	private final Game serverGame; // This game will run locally on the server
	private DatagramSocket ds; //Socket used for Server <-> Player communication
	private final ArrayList<ConnectionDetails> players;
	private ConnectionDetails host;
	private boolean running;

	/**
	 * Constructor of the server
	 * @param serverPort port of the server
	 */
	public Server(int serverPort) {
		try {
			ds = new DatagramSocket(serverPort);
		} catch (SocketException e) {
			System.err.println("An error occurred when trying to create the server");
		}
		serverGame = new Game(false, true);
		serverGame.addListener(this);
		players = new ArrayList<>();
		host = null;
		running = false;
	}

	/**
	 * Returns the InetAddress of the server
	 */
	public InetAddress getServersAddress() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ds.getLocalAddress();
	}

	/**
	 * Initializes the server game
	 */
	public void launchServerGame() {
		serverGame.quit();
		serverGame.initializeGameData();
		serverGame.start();
	}

	/**
	 * Main loop of the server.
	 */
	@Override
	public void run() {
		launchServerGame();
		running = true;
		while (running) {
			DatagramPacket receivedPacket = receive(ds);
			String[] packet = fromByteToString(receivedPacket.getData()).split("@");

			if ("JOIN".equals(packet[0])) {
				newConnection(receivedPacket.getAddress(), receivedPacket.getPort());
			} else if ("DISCONNECT".equals(packet[0])) {
				disconnectPlayer(receivedPacket.getAddress(), receivedPacket.getPort());
			} else if ("MODEL".equals(packet[0])) {
				updateGame(serverGame, packet);
			}

		}
	}

	/**
	 * Updates all player's local game
	 */
	private synchronized void updatePlayersGame() {
		for (ConnectionDetails player : players) {
			updatePlayerShips(player);
			updatePlayerAsteroids(player);
			updatePlayerBullets(player);
		}
	}

	/**
	 * Method to update the player ships.
	 */
	private void updatePlayerShips(ConnectionDetails player) {
		for (Spaceship ship : serverGame.getSpaceships()) {
			PacketHandler.send(ds, player, getShipPacket(ship));
		}
	}
	/**
	 * Method to update the player asteroids.
	 */
	private synchronized void updatePlayerAsteroids(ConnectionDetails player) {
		for (Asteroid asteroid : serverGame.getAsteroids()) {
			PacketHandler.send(ds, player, getAsteroidPacket(asteroid));
		}
	}

	/**
	 * Method to update the player bullets.
	 */
	private void updatePlayerBullets(ConnectionDetails player) {
		for (Bullet bullet : serverGame.getBullets()) {
			PacketHandler.send(ds, player, getBulletPacket(bullet));
		}
	}

	/**
	 * Whenever a player decides to disconnect, delete him from the server connection
	 * if the player is the host, disconnect all other players
	 */
	private void disconnectPlayer(InetAddress address, int port) {
		ConnectionDetails player = new ConnectionDetails(address, port);

		if (host.getIp() == player.getIp() && host.getPort() == player.getPort()) {
			players.remove(host);
			for (ConnectionDetails pl : players) {
				send(ds, pl, prepareEndPacket());
				players.remove(pl);
			}
		} else {
			players.remove(player);
		}
	}

	/**
	 * If a new connection packet is received, then
	 */
	private void newConnection(InetAddress address, int port) {
		ConnectionDetails player = new ConnectionDetails(address, port);

		if (players.size() == 0) {
			host = player;
		}

		players.add(player);
	}

	/**
	 * New update on the server game? -> Inform other players
	 *
	 * @param timeSinceLastTick The number of milliseconds that have passed since the last game tick occurred. This is
	 *                          used so that things like a display may continue showing an animated model while no
	 */
	@Override
	public void onGameUpdated(long timeSinceLastTick) {
		updatePlayersGame();
	}

	/**
	 * Returns the server game
	 */
	public Game getGame() {
		return serverGame;
	}
}
