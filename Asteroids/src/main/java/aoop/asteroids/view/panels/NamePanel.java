package aoop.asteroids.view.panels;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class NamePanel extends JPanel {

	private final JTextField name;

	/**
	 * A text field where player can put his nickname
	 */
	public NamePanel(Container nameContainer) {
		setBackground(Color.black);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel nameLabel = (JLabel) add(new JLabel("Player name : "));
		nameLabel.setForeground(Color.RED);
		Random random = new Random();
		int rand = random.nextInt(1000);
		name = new JTextField("Player " + rand);
		name.setPreferredSize(new Dimension(140, 20));
		add(name);

		nameContainer.add(this);
	}

	/**
	 * Getter
	 * @return name in form of string
	 */
	public String getName() {
		return this.name.getText();
	}
}
