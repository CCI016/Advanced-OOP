package aoop.asteroids.control.actions;

import aoop.asteroids.control.NewGameAction;
import aoop.asteroids.model.Game;
import aoop.asteroids.view.AsteroidsFrame;
import aoop.asteroids.view.MainMenuFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePAction implements ActionListener {

	/**
	 * The frame of the main menu
	 */
	private final MainMenuFrame menuFrame;

	/**
	 * Constructor
	 *
	 * @param menuFrame the frame of the main menu.
	 */
	public SinglePAction(MainMenuFrame menuFrame) {
		this.menuFrame = menuFrame;
	}

	/**
	 * Initializes a singleplayer game, with the ship color and name chosen by the player.
	 * @param e event of the action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Game game = new Game(false, true);
		game.getSpaceship().setShipColor(menuFrame.getColorPanel().getColor());
		game.getSpaceship().setPlayerNick(menuFrame.getNamePanel().getName());
		AsteroidsFrame asteroidsFrame = new AsteroidsFrame(game);
		new NewGameAction(game).actionPerformed(
				new ActionEvent(asteroidsFrame, ActionEvent.ACTION_PERFORMED, null)
		);
		menuFrame.dispose();
	}
}
