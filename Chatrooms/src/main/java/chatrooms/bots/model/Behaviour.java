package chatrooms.bots.model;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Behaviour {

	private final boolean behaviour;

	public Behaviour(boolean behaviour) {
		this.behaviour = behaviour;
	}

	/**
	 *  Method to send messages to the messageFeed
	 */
	protected void sendMessage(PrintWriter out, ArrayList<String> messageFeed, String name, int id) {
		if (!behaviour) {
			echoBehavior(out, messageFeed, name, id);
		} else {
			combinedBehaviour(out, messageFeed, name, id);
		}
	}

	/**
	 * Method that implies the ECHO behaviour of the bots when they repeat the same message
	 */

	private void echoBehavior(PrintWriter out, ArrayList<String> messageFeed, String name, int id) {
		if (!messageFeed.isEmpty()) {
			Random r = new Random();
			if (r.nextInt(3) == 1) {
				out.println(name + " " + id + " " + sendRandomMess());
			} else {
				out.println(name + " " + id + " " + "echo: " + messageFeed.get(messageFeed.size() - 1));
			}
		}
	}

	/**
	 * Method that takes random words from several messages, and randomly forms a sentence with these words.
	 */

	private void combinedBehaviour( PrintWriter out, ArrayList<String> messageFeed,  String name, int id) {
		if (!messageFeed.isEmpty()) {
			String messages = "";
			for (String s : messageFeed) {
				messages = messages.concat(s + " ");
			}
			String[] words = messages.split("\\W+");
			String message = "";
			for (int j = 0; j < 7; j++) {
				message = message.concat(words[new Random().nextInt(words.length)] + " ");
			}
			out.println(name + " " + id + " : " + message);
		}
	}

	/**
	 * Method to initialise messages arraylist
	 * @return randomly a selected message
	 */

	private String sendRandomMess() {
		ArrayList<String> messages = new ArrayList<>();
		messages.add("Why are there deadlines, if I do everything one night before?");
		messages.add("The vindicat in middle of pandemic be like, let's go to Italy, it'll be fun)))");
		messages.add("The only reason I attend the AOOP tutorials are the memes, haha");
		messages.add("Me when the new flatmate who is in a student association starts coughing - Emergency meeting");
		messages.add("When the university announced that everything will be online, i was surprised,\n because i never went to lectures anyway");

		Random r = new Random();
		return messages.get(r.nextInt(messages.size()));
	}

}
