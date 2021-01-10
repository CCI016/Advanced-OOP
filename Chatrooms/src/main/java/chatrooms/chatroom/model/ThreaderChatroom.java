package chatrooms.chatroom.model;

import chatrooms.chatroom.view.ChatroomPanel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;

public class ThreaderChatroom implements Runnable, PropertyChangeListener{
	private final ChatroomPanel panel;
	private final Socket s;
	private  int threadNr;
	private BufferedReader in;
	private PrintWriter out;
	private final MessageFeed messageFeed;
	private static String message;

	public ThreaderChatroom(Socket s, MessageFeed messageFeed, ChatroomPanel panel) {
		this.panel = panel;
		this.s = s;
		this.messageFeed = messageFeed;
		messageFeed.addListener(this);
		try  {
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that runs the thread handler of chatroom
	 */

	@Override
	public synchronized void run() {

			//noinspection InfiniteLoopStatement
			while(true) {
				try {
					if (in.ready()) {
						message = in.readLine();
						if (message != null) {
							if (message.substring((message.length() - 9)).equals("Chatroom.")) {
								panel.botConnected(message);
							} else {
								messageFeed.setMessage(message);
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Nothing has been sent");
				}
			}

	}
	/**
	 * Method used to make the desired change
	 * @param evt that happens when there is a change in changeSupport
	 */

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		out.println(messageFeed.getMessage());
	}
}