package aoop.asteroids.view.panels;

import javax.swing.*;
import java.awt.*;

public class ColorPanel extends JPanel {

	private final JColorChooser shipColorChooser;

	/**
	 * Sets the color of the spaceship
	 */
	public ColorPanel(Container colorContainer) {
		setBackground(Color.black);
		shipColorChooser = new JColorChooser();

		setAlignmentX(Component.CENTER_ALIGNMENT);

		add(shipColorChooser);

		colorContainer.add(this);
	}

	public Color getColor() {
		return shipColorChooser.getColor();
	}
}
