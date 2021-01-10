package chatrooms.bots.model;

import java.io.IOException;

public class LocalBot extends Bot implements Runnable {

	private static final boolean run = true;

	/**
	 * Constructpr
	 * @param id the id of the bot
	 */

	public LocalBot(int id) {
		super(id);
	}

	/**
	 * Method that runs the local bot thread
	 */

	@Override
	public void run() {
		connect();
		while (run) {
			try {
				if (socket.isConnected() && in.ready()) {
					String message = in.readLine();
					if (message != null) {
						if ( messages.isEmpty() || (!messages.get(messages.size() - 1).equals(message))) {
							messages.add(message.substring(message.indexOf(":") + 1));
						}
					}
				}
			} catch (IOException e) {
				System.out.println("Bot migrated");
			}
			if (messageControl) {
				sendMess();
			}
		}
	}

	/**
	 * Method to connect the local bot
	 */

	private synchronized void connect() {
		super.reconnect();
	}

	/**
	 * Method used by local bot to send the message
	 */

	private void sendMess() {
		super.sendMessage();
		messageControl = true;
	}
}
