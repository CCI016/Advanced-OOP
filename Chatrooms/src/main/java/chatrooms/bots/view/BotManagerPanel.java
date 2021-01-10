package chatrooms.bots.view;

import chatrooms.bots.model.BotManager;

import javax.swing.*;

public class BotManagerPanel extends JPanel {
	private final BotManager botManager;

	public BotManagerPanel(BotManager botManager) {
		this.botManager = botManager;
		JPanel panel = new JPanel();
	}

}
