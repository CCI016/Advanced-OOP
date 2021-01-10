package chatrooms.bots.control;

import chatrooms.bots.model.BotManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KillButtonListener implements ActionListener {
	private final BotManager botManager;

	public KillButtonListener(BotManager botManager) {
		this.botManager = botManager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		botManager.stopAllBots();
	}
}
