package aoop.asteroids.model;

import aoop.asteroids.control.actions.GameUpdater;
import aoop.asteroids.control.game_database.DataBaseManager;
import aoop.asteroids.game_observer.ObservableGame;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class is the main model for the Asteroids game. It contains all game objects, and has methods to start and stop
 * the game.
 * <p>
 * This is strictly a model class, containing only the state of the game. Updates to the game are done in
 * {@link GameUpdater}, which runs in its own thread, and manages the main game loop and physics updates.
 */
public class Game extends ObservableGame {
	/**
	 * Responsible for spectate mode(The player will not have a ship)
	 */
	private final boolean spectator;
	/**
	 * The spaceship object that the player is in control of.
	 */
	private Spaceship ship;
	/**
	 * The list of all bullets currently active in the game.
	 */
	private Collection<Bullet> bullets;
	/**
	 * The list of all asteroids in the game.
	 */
	private Collection<Asteroid> asteroids;
	/**
	 * The list of all ships in the game
	 */
	private Collection<Spaceship> spaceships;
	/**
	 * Indicates whether or not the game is running. Setting this to false causes the game to exit its loop and quit.
	 */
	private volatile boolean running = false;
	/**
	 * The game updater thread, which is responsible for updating the game's state as time goes on.
	 */
	private Thread gameUpdaterThread;
	/**
	 * is this game on server or no?
	 */
	private final boolean server;

	/**
	 * Constructs a new game, with a new spaceship and all other model data in its default starting state.
	 */

	public Game(boolean spectator, boolean server) {
		this.ship = new Spaceship();
		this.server = server;
		this.spectator = spectator;
		this.initializeGameData();

	}

	/**
	 * Initializes all of the model objects used by the game. Can also be used to reset the game's state back to a
	 * default starting state before beginning a new game.
	 */
	public void initializeGameData() {
		this.spaceships = new CopyOnWriteArrayList<>();
		this.bullets = new CopyOnWriteArrayList<>();
		this.asteroids = new CopyOnWriteArrayList<>();
		this.ship.reset();
	}

	public Collection<Spaceship> getSpaceships() {
		return spaceships;
	}

	/**
	 * @return The game's spaceship.
	 */
	public Spaceship getSpaceship() {
		return this.ship;
	}

	/**
	 * @return The collection of asteroids in the game.
	 */
	public Collection<Asteroid> getAsteroids() {
		return this.asteroids;
	}

	/**
	 * @return The collection of bullets in the game.
	 */
	public Collection<Bullet> getBullets() {
		return this.bullets;
	}

	/**
	 * @return Whether or not the game is running.
	 */
	public synchronized boolean isRunning() {
		return this.running;
	}

	/**
	 * @return True if the player's ship has been destroyed, or false otherwise.
	 */
	public boolean isGameOver() {
		if (ship == null) {
			return false;
		}
		return this.ship.isDestroyed();
	}

	/**
	 * Using this game's current model, spools up a new game updater thread to begin a game loop and start processing
	 * user input and physics updates. Only if the game isn't currently running, that is.
	 */
	public void start() {
		if (!this.running) {
			this.running = true;
			this.gameUpdaterThread = new Thread(new GameUpdater(this));
			this.gameUpdaterThread.start();
		}
	}

	/**
	 * Tries to quit the game, if it is running.
	 */
	public void quit() {
		if (ship != null) {
			saveShipScore();
		}
		if (this.running) {
			try { // Attempt to wait for the game updater to exit its game loop.
				this.gameUpdaterThread.join(100);
			} catch (InterruptedException exception) {
				System.err.println("Interrupted while waiting for the game updater thread to finish execution.");
			} finally {
				this.running = false;
				this.gameUpdaterThread = null; // Throw away the game updater thread and let the GC remove it.
			}
		}
	}

	/**
	 * @return it is spectator?
	 */
	public boolean isSpectator() {
		return spectator;
	}

	/**
	 * Method that is used to load at the end of the game, the name and score of the ship to the database.
	 */
	private void saveShipScore() {
		if (ship.getScore() > 0) {
			DataBaseManager.addScore(ship.getShipMaster(), ship.getScore());
		}
	}

	/**
	 * Method to add a ship to the collection of spaceships.
	 */
	public void addSpaceship(Spaceship ship) {
		spaceships.add(ship);
	}

	/**
	 * Getter
	 * @return boolean that tells whether this game is a server
	 */
	public boolean isServer() {
		return server;
	}

	/**
	 * Setter, to set the ship of the game to null.
	 */
	public void setShipNull() {
		ship = null;
	}
}
