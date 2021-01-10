package chatrooms.chatroom.model;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * Message model used for the client. It stores the last message that was sent to/received from the server
 */

public class MessageFeed {
	private final ArrayList<String> message;
	private final PropertyChangeSupport changeSupport;

	public MessageFeed() {
		message = new ArrayList<>();
		this.changeSupport = new PropertyChangeSupport(this);
	}

	public String getMessage() {
		return message.get(message.size() - 1);
	}

	/**
	 * Method that creates a new PropertyChangeEvent every time the message is updated. It then calls firePropertyChange with this event
	 * @param message the new message
	 */

	 public synchronized void setMessage(String message) {
		PropertyChangeEvent messageEvent = new PropertyChangeEvent(this, "new message", this.message, message);
		this.message.add(message);
		changeSupport.firePropertyChange(messageEvent);
	}

	public boolean isEmpty() {
		return message.isEmpty();
	}

	/**
	 * Method used to add PropertyChangeListeners to changeSupport
	 * @param listener listener to be added to changeSupport
	 */
	 public synchronized void addListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}
}
