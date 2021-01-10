package aoop.asteroids.model;

/**
 * This enumeration defines the different possible sizes of asteroids.
 */
public enum AsteroidSize {
	SMALL(10),
	MEDIUM(20),
	LARGE(40);

	/**
	 * The radius that each different asteroid size is.
	 */
	private final double radius;

	/**
	 * Constructor that requires that any new enumeration values provide a valid radius.
	 *
	 * @param radius The radius of the asteroid.
	 */
	AsteroidSize(double radius) {
		this.radius = radius;
	}

	/**
	 * This function will be used for creating objects from packets
	 */
	public static AsteroidSize getAsteroidSize(int size) {
		switch (size) {
			case 10:
				return SMALL;
			case 20:
				return MEDIUM;
			case 40:
				return LARGE;
		}
		return null;
	}

	/**
	 * @return The radius of this asteroid size.
	 */
	public double getRadius() {
		return this.radius;
	}

	/**
	 * @return The size of asteroids that are produced when this one is destroyed. May return null if this asteroid is
	 * too small to produce successors.
	 */
	public AsteroidSize getSuccessorSize() {
		if (this.equals(LARGE)) {
			return MEDIUM;
		} else if (this.equals(MEDIUM)) {
			return SMALL;
		} else {
			return null;
		}
	}
}
