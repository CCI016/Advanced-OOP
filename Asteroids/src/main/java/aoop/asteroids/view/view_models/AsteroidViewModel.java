package aoop.asteroids.view.view_models;

import aoop.asteroids.model.Asteroid;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * View model for displaying an asteroid object.
 */
public class AsteroidViewModel extends GameObjectViewModel<Asteroid> {

	/**
	 * The system separator character.
	 */
	private final static String separator = System.getProperty("file.separator");

	/**
	 * Path to asteroid image.
	 */
	private final File asteroidPNG = new File("src" + separator + "main" + separator + "resources" + separator + "images" + separator + "asteroid.png");

	/**
	 * Constructs a new view model with the given game object.
	 *
	 * @param gameObject The object that will be displayed when this view model is drawn.
	 */
	public AsteroidViewModel(Asteroid gameObject) {
		super(gameObject);
	}

	/**
	 * Draws the game object that was given to this view model.
	 *
	 * @param graphics2D The graphics object which provides the necessary drawing methods.
	 * @param location   The location at which to draw the object.
	 */
	@Override
	public void draw(Graphics2D graphics2D, Point.Double location) {
		double radius = this.getGameObject().getRadius();
		graphics2D.setColor(Color.GRAY);
		try {
			Image asteroidIMG = ImageIO.read(asteroidPNG);
			graphics2D.drawImage(asteroidIMG,
					(int) (location.getX() - (int) radius),
					(int) (location.getY() - (int) radius),
					(2 * (int) radius),
					(2 * (int) radius),
					null);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
