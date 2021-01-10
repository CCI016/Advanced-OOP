package aoop.asteroids.control.actions;

import aoop.asteroids.view.AsteroidsFrame;
import aoop.asteroids.view.MainMenuFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenOnAsterFrame implements KeyListener {
	private static final int EXIT_GAME = KeyEvent.VK_E; //When press e, it will return player to main menu.
	private final AsteroidsFrame asteroidsFrame;

	public KeyListenOnAsterFrame(AsteroidsFrame asteroidsFrame) {
		this.asteroidsFrame = asteroidsFrame;
	}

	/**
	 * When we press E it will return player to main menu
	 *
	 * @param event even done when key is pressed
	 */
	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == EXIT_GAME) {
			asteroidsFrame.getGame().quit();
			asteroidsFrame.setVisible(false);
			new MainMenuFrame();
		}
	}

	/**
	 * Method does nothing, needed to implement because of KeyListener interface
	 *
	 * @param e event done when typing
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Method does nothing, needed to implement because of KeyListener interface
	 *
	 * @param e event done when typing
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}

}
