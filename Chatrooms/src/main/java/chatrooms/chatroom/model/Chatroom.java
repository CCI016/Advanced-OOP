package chatrooms.chatroom.model;

import chatrooms.chatroom.view.ChatroomPanel;
import chatrooms.server.model.Server;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Chatroom extends Thread  {
	private final MessageFeed messageFeed;
	private ChatroomPanel panel;

	public Chatroom() {
		messageFeed = new MessageFeed();
	}

	/**
	 * Method that starts the chat room, connects to the main server, informs about the it's port and disconnects from server.
	 */

	@Override
	public void run() {
		try (ServerSocket chatRoom = new ServerSocket(0)) {
			setupGUI(chatRoom.getLocalPort());

			Socket socket = new Socket();
			socket.connect(new InetSocketAddress("localhost", Server.SERVER_PORT), 1000);
			if (!socket.isConnected()) {
				// Connection with the server failed? abort!
				System.out.println("The socket is not connected!");
				return;
			}

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			// informs the server about the port it's listening on!
			out.println("Port Number: " + chatRoom.getLocalPort());
			closeServerConn(socket, out);

			//noinspection InfiniteLoopStatement
			while (true) {
				Socket incoming = chatRoom.accept();
				Thread t = new Thread(new ThreaderChatroom(incoming, messageFeed, panel));
				t.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that sets up the frame and the panel for the GUI of the chatroom
	 * @param port
	 */

	private void setupGUI(int port) {
		SwingUtilities.invokeLater(() -> {
			panel = new ChatroomPanel(messageFeed,port);
			JFrame frame = new JFrame();

			frame.setContentPane(panel);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		});
	}


	/**
	 * Method that closes the connection with the main server
	 */

	public static void closeServerConn(Socket s, PrintWriter out) {
		try {
			out.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
