package chatrooms.bots.model;

import java.io.IOException;
import java.util.Random;

public class MigratoryBot extends Bot implements Runnable{

	private static boolean controlMigration; // true => possible to migrate; false, impossible to migrate

	/**
	 * Constructor
	 * @param id the id of migratory bot
	 */

	public MigratoryBot(int id) {
		super(id);
		controlMigration = true;
	}

	/**
	 * Method to runs the migratory bot thread
	 */
	@Override
	public void run() {
		connect();

		//noinspection InfiniteLoopStatement
		while (run) {
			if (controlMigration) { // once in 45 seconds the bot will migrate
				migrate(45);
			}

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
	 * Creates a timer and once in 45 s the bot will migrate
	 */

	private void migrate(int delay) {
		controlMigration = false;
		try {
			Thread.sleep(delay * 1000);
			connect();

		} catch (InterruptedException e) {
			System.out.println("Sleep was interrupted");
		}

	}

	/**
	 * Method used for connecting to the chatroom or migrating
 	 */

	 private synchronized void connect() {
		super.reconnect();
		controlMigration = true;
	}

	/**
	 * Method that allows migratory bots to send a message
	 */

	private void sendMess() {
		super.sendMessage();
		Random migrateChance = new Random();
		if (migrateChance.nextInt(5) == 2) {
			// bot has a 20% chance to migrate before sending a message
			migrate(4);
		}
		messageControl = true;
	}
}
