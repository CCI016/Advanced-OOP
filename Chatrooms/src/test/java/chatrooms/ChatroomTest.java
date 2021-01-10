package chatrooms;

import chatrooms.bots.model.*;
import chatrooms.chatroom.model.Chatroom;
import chatrooms.chatroom.model.MessageFeed;
import chatrooms.server.model.Server;
import chatrooms.server.model.ThreaderMainServer;
import chatrooms.server.view.MainServerPanel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ChatroomTest {

	/**
	 * testing constructors
	 */
	@Test
	public void testConstructors() {
		Server server = new Server();
		assertNotNull(server);
		ThreaderMainServer threaderMainServer = new ThreaderMainServer(new Socket(), new MainServerPanel(1));
		assertNotNull(threaderMainServer);
		Chatroom chatroom = new Chatroom();
		assertNotNull(chatroom);
		MessageFeed messageFeed = new MessageFeed();
		assertNotNull(messageFeed);
		Bot bot = new Bot(1);
		assertNotNull(bot);
		BotManager botManager = new BotManager();
		assertNotNull(botManager);
		LocalBot localBot = new LocalBot(2);
		assertNotNull(localBot);
		MigratoryBot migratoryBot = new MigratoryBot(2);
		assertNotNull(migratoryBot);
		Behaviour behaviour = new Behaviour(false);
		assertNotNull(behaviour);
	}

	/**
	 * Method to test if the application works as intended
	 */
	@Test
	public void testConn() throws IOException, InterruptedException, NullPointerException {
		Server s = new Server();

		// First testing the server if it runs
		Thread server = new Thread(s);
		assertFalse(server.isAlive());
		server.start();

		// Wait a bit to ensure that the thread has started
		Thread.sleep(100);
		assertTrue(server.isAlive());

		// Testing if the chatroom runs
		Chatroom chatroom = new Chatroom();
		Thread firstChatroom = new Thread(chatroom);
		assertFalse(firstChatroom.isAlive());
		firstChatroom.start();
		Thread.sleep(100);
		assertTrue(firstChatroom.isAlive());
	}
}
