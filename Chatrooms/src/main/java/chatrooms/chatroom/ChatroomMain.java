package chatrooms.chatroom;

import chatrooms.chatroom.model.Chatroom;

public class ChatroomMain {

	public static void main(String[] args) {
		// Creating the chatrooms and starting them
		Thread firstChatroom = new Thread(new Chatroom());
		firstChatroom.start();
		Thread secondChatroom = new Thread(new Chatroom());
		secondChatroom.start();
		Thread thirdChatroom = new Thread(new Chatroom());
		thirdChatroom.start();
	}
}
