package aoop.asteroids.view.panels;

import aoop.asteroids.control.buttons.MultiPHostButton;
import aoop.asteroids.control.buttons.MultiPJoinButton;
import aoop.asteroids.control.buttons.SinglePButton;
import aoop.asteroids.view.MainMenuFrame;

import javax.swing.*;
import java.awt.*;

public class SinglePanel extends JPanel {

	/**
	 * Created just so we can have black backgorund in the menu.
	 */
	public SinglePanel(MainMenuFrame menuFrame, Container buttonContainer) {
		setBackground(Color.black);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		add(new SinglePButton(menuFrame));
		add(new MultiPHostButton(menuFrame));
		add(new MultiPJoinButton(menuFrame));

		buttonContainer.add(this);
	}
}
