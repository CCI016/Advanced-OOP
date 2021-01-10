package chatrooms.server;

import chatrooms.server.model.Server;

public class MainServer {
	public static void main(String[] args) {
		Thread server = new Thread(new Server());
		server.start();
	}
}

