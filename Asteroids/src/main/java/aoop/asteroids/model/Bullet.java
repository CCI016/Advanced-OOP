package aoop.asteroids.model;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * The bullet is the ultimate weapon of the player. It has the same mechanics
 * as an asteroid, in which it cannot divert from its trajectory. However, the
 * bullet has the addition that it only exists for a certain amount of game
 * steps.
 */
public class Bullet extends GameObject {
	/**
	 * The number of steps, or game ticks, that a bullet stays alive for, before it is destroyed.
	 */
	public static final int DEFAULT_BULLET_STEP_LIFETIME = 45;
	/**
	 * The id of the ship which is the owner of this bullet
	 */
	private final String masterShipUniqueId;
	/**
	 * The amount of steps this bullet still is allowed to live. When this value drops below 0, the bullet is removed
	 * from the game model.
	 */
	private int stepsLeft;

	/**
	 * Constructs a new bullet using the given location and velocity parameters, and a default number of steps until the
	 * bullet is destroyed.
	 *
	 * @param locationX The location of this bullet on the x-axis.
	 * @param locationY The location of this bullet on the y-axis.
	 * @param velocityX velocity of the bullet as projected on the X-axis.
	 * @param velocityY velocity of the bullet as projected on the Y-axis.
	 */
	public Bullet(double locationX, double locationY, double velocityX, double velocityY, String shipUID) {
		this(locationX, locationY, velocityX, velocityY, DEFAULT_BULLET_STEP_LIFETIME, shipUID);
	}

	/**
	 * Constructs a new bullet with a set number of steps until it is destroyed.
	 *
	 * @param locationX The location of this bullet on the x-axis.
	 * @param locationY The location of this bullet on the y-axis.
	 * @param velocityX Velocity of the bullet as projected on the X-axis.
	 * @param velocityY Velocity of the bullet as projected on the Y-axis.
	 * @param stepsLeft Amount of steps the bullet is allowed to live.
	 */
	private Bullet(double locationX, double locationY, double velocityX, double velocityY, int stepsLeft, String shipUID) {
		super(locationX, locationY, velocityX, velocityY, 0);
		this.stepsLeft = stepsLeft;
		masterShipUniqueId = shipUID;
		soundEffects();
	}

	/**
	 * Playing sound effects
	 */
	private void soundEffects() {
		String sep = System.getProperty("file.separator");
		File file = new File("src" + sep + "main" + sep + "resources" + sep + "sound" + sep + "fire.wav");

		try {
			Clip play = AudioSystem.getClip();
			play.open(AudioSystem.getAudioInputStream(file));
			play.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			System.err.println("Couldn't play sound.");
		}
	}

	/**
	 * Updates the bullet. First calls the parent's nextStep() method to update the object's location, and specifically
	 * for the bullet class, there is a lifetime to the bullet, indicated by the number of steps left until it should be
	 * destroyed. At each step, this value is decremented, and once it reaches zero, the bullet is destroyed.
	 */
	@Override
	public void nextStep() {
		super.nextStep();

		this.stepsLeft--;
		if (this.stepsLeft <= 0) {
			this.destroy();
		}
	}

	/**
	 * return the steps left
	 */
	public int getStepsLeft() {
		return stepsLeft;
	}

	/**
	 * Sets the steps left
	 */
	public void setStepsLeft(int stepsLeft) {
		this.stepsLeft = stepsLeft;
	}

	/**
	 * @return The number of steps, or game ticks, for which this object is immune from collisions.
	 */
	@Override
	protected int getDefaultStepsUntilCollisionPossible() {
		return 3;
	}

	/**
	 * @return Owner of the bullet
	 */
	public String getMasterShipId() {
		return masterShipUniqueId;
	}
}
