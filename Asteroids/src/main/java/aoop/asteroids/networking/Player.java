package aoop.asteroids.networking;

import aoop.asteroids.control.NewGameAction;
import aoop.asteroids.game_observer.GameUpdateListener;
import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Game;
import aoop.asteroids.view.AsteroidsFrame;
import aoop.asteroids.view.MainMenuFrame;

import java.awt.event.ActionEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Player extends PacketHandler implements Runnable, GameUpdateListener {
	private final ConnectionDetails serverConnDetails;
	private DatagramSocket socket;
	private final Game game;
	private boolean running;
	private AsteroidsFrame asteroidsFrame;

	/**
	 * Constructor of the player.
	 * @param serverPort port of the server
	 * @param serverAddress InetAdress of the server
	 * @param isSpectator boolean to check if the player is a spectator or not.
	 */
	public Player(int serverPort, InetAddress serverAddress, boolean isSpectator) {

		try {
			socket = new DatagramSocket(0);
		} catch (SocketException e) {
			System.err.println("Couldn't initiate a Socket for the player");
		}

		asteroidsFrame = null;
		serverConnDetails = new ConnectionDetails(serverAddress, serverPort);
		this.game = new Game(isSpectator, false);
		game.addListener(this);
		running = false;
	}

	/**
	 * Player tries to connect to the server
	 */
	public void tryToConnectToServer() {
		if (socket != null) {
			byte[] packet = prepareJoinPacket();
			send(socket, serverConnDetails, packet);
		}
	}

	/**
	 * Initiates the game on the server
	 */
	private void launchGame() {
		asteroidsFrame = new AsteroidsFrame(this.game);
		new NewGameAction(this.game).actionPerformed(
				new ActionEvent(asteroidsFrame, ActionEvent.ACTION_PERFORMED, null)
		);
		if (game.isSpectator()) {
			game.setShipNull();
		}
	}

	/**
	 * Runs the client.
	 */
	@Override
	public void run() {
		tryToConnectToServer();
		running = true;
		launchGame();

		while (running) {
			DatagramPacket receivedPacket = receive(socket);
			String[] packet = fromByteToString(receivedPacket.getData()).split("@");

			if ("END".equals(packet[0])) {
				disconnect();
			} else if ("MODEL".equals(packet[0])) {
				updateGame(game, packet);
			}
			if (asteroidsFrame.getGame().isGameOver()) {
				disconnect();
			}
		}
	}

	/**
	 * End the game.
	 */
	private void endGame() {
		game.quit();
		running = false;
		asteroidsFrame.setVisible(false);
		new MainMenuFrame();
	}

	/**
	 * Disconnects from the server
	 */
	public void disconnect() {
		send(socket, serverConnDetails, prepareDisconnectPacket());
		endGame();
		socket.close();
	}

	@Override
	public void onGameUpdated(long timeSinceLastTick) {
		notifyServer();
	}

	/**
	 * Notifies server about the updates on player's local game
	 */
	private void notifyServer() {
		if (!game.isSpectator()) {
			sendShipUpdate();
			sendBulletsUpdate();
		}
	}

	/**
	 * Method send the bullets update.
	 */
	private void sendBulletsUpdate() {
		for (Bullet bullet : game.getBullets()) {
			send(socket, serverConnDetails, getBulletPacket(bullet));
		}
	}

	/**
	 * Method to send the ship update.
	 */
	private void sendShipUpdate() {
		send(socket, serverConnDetails, getShipPacket(game.getSpaceship()));
	}

	/**
	 * Getter
	 * @return the game of the player.
	 */
	public Game getGame() {
		return game;
	}
}
