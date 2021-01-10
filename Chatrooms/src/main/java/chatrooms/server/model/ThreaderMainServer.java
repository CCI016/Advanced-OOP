package chatrooms.server.model;

import chatrooms.server.view.MainServerPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The part of the server that will run concurrently; it will connect to and communicate with the client
 * using the socket that is provided
 */
public class ThreaderMainServer implements Runnable {
	private final Socket s;
	private final MainServerPanel panel;

	public ThreaderMainServer(Socket s, MainServerPanel panel) {
		this.s = s;
		this.panel = panel;
	}

	/**
	 * communicates with the chatroom, get's chatroom's port, and will handle the bots!
	 */
	@Override
	public void run() {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			 PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {

			String line = in.readLine();
			if (line != null) {

				// check if there is a chatroom port
				if (line.length() > 11 && (line.startsWith("Port Number:"))) {
					// ads the port to the list on main server
					int port = Integer.parseInt(line.substring(13));
					Server.addChatroomPorts(port);
					panel.chatroomConnected(port);
				} else {
					int botChatroomPort = Integer.parseInt(line.substring(line.indexOf(':') + 2));
					int i = Server.getChatroomPort();

					messageToPanel(botChatroomPort, line);

					while (botChatroomPort == i) {
						i = Server.getChatroomPort();
					}

					System.out.println(i);
					out.println(i);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				System.out.println("Server couldn't close connection!");
			}
		}
	}

	private void messageToPanel(int botChatroomPort, String line) {
		// checks if it is the first connect of the bot to the server or it just changes chatroom
		if (botChatroomPort == 0) {
			panel.botConnected(0 + line.substring(0, line.indexOf(":") ));
		} else {
			panel.botConnected(line.substring(0, line.indexOf(":")));
		}
	}
}
