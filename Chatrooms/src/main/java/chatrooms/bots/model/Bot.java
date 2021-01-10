package chatrooms.bots.model;


import chatrooms.server.model.Server;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

public class Bot {

	protected final String name;
	protected static boolean run;
	protected boolean messageControl; // true => it is possible to send a message, false => otherwise
	protected final int id;
	protected int chatroomPort;
	protected Socket socket;
	protected PrintWriter out;
	protected BufferedReader in;
	protected ArrayList<String> messages;
	protected Behaviour behaviour; // if it is false, bot will have echo behaviour, else it will have combined behaviour

	private final static ArrayList<String> names =  new ArrayList<>(
			Arrays.asList("John", "Max", "Emma", "Olivia", "Oliver", "Dan"));

	/**
	 * Constructor
	 * @param id the id of each bot
	 */

	public Bot(int id) {
		run = true;
		chatroomPort = 0;
		socket = new Socket();
		this.id = id;
		Random rand = new Random();
		this.name = names.get(rand.nextInt(names.size()));
		behaviour = new Behaviour(rand.nextBoolean());
		messageControl = true;
	}

	/**
	 * Method which allows bot to connect to the Main server, and retrieving an active chatroom
	 */

	protected void connectToServer() {
		try {

			socket = new Socket();
			socket.connect(new InetSocketAddress("localhost", Server.SERVER_PORT), 1000);

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println(name + " " + id + ": " + chatroomPort); // sends a message to the server that it is a bot and not a chatroom

			String line = in.readLine();
			if(line != null) {
				chatroomPort = Integer.parseInt(line);
			} else {
				System.out.println("ERROR: Couldn't get an active chatroom");
				connectToServer();
			}

		} catch (IOException e) {
			System.out.println("Bot couldn't connect to the server");
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				System.out.println("Socket couldn't close connection (Bot -> Server)");
			}
		}
	}

	/**
	 * Method to connect to the chatroom
	 */

	protected synchronized void connectToChatroom() {
		try {
			if (chatroomPort == 0) {
				System.out.println("ERROR: Empty chatroom port");
			} else {
				messages = new ArrayList<>();

				socket = new Socket();
				socket.connect(new InetSocketAddress("localhost", chatroomPort));
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println(name + " " + id + " connected to Chatroom." );
				out.println(name + " " + id + ": Hello chat, my name is " + name);
				System.out.println("Bot " + name + " " + id + " connected to: " + chatroomPort);
			}
		} catch (IOException e) {
			System.out.println("Couldn't connect to chatroom");
		}
	}

	/**
	 * Method that connects the bot first to the server, then to chatroom when a bot has migrated
	 */

	protected void reconnect() {
		if (run) {
			if (socket.isConnected()) {
				try {
					if (chatroomPort != 0) {
						out.println(name + " " + id + " left the Chatroom.");
					}
					socket.close();
				} catch (IOException e) {
					System.out.println("Bot tried to connect to another chatroom, but an error occurred");
				}
			}
			connectToServer();
			connectToChatroom();
		} else {
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println("Couldn't disconnect");
			}
		}

	}

	/**
	 * Method to send messages by the bots
	 */

	protected void sendMessage() {
		if (messages != null) {
			messageControl = false;
			try {
				Random r = new Random();
				Thread.sleep(r.nextInt(10) * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (run) {
				behaviour.sendMessage(out, messages, name, id);
			}
		}
	}

	/**
	 * Method that shuts down the bot
	 */

	protected void shutDown() {
		run = false;
	}

}


