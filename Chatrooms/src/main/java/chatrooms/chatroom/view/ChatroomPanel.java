package chatrooms.chatroom.view;

import chatrooms.chatroom.model.MessageFeed;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ChatroomPanel extends JPanel implements PropertyChangeListener {
    private final MessageFeed messageFeed;
    private final JTextArea outputTextArea;
    private final int port;

    private final int ROWS = 40;
    private final int COLUMNS = 50;
    private final int GAP = 3;

    public ChatroomPanel(MessageFeed messageFeed, int port) {
        this.messageFeed = messageFeed;
        this.messageFeed.addListener(this);
        this.port = port;

        outputTextArea = new JTextArea(ROWS, COLUMNS);
        outputTextArea.setFocusable(false);
        outputTextArea.setEditable(false);

        setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(Scroll(outputTextArea, "Chatroom " + port));
    }

    /**
     * Creates a new JPanel of a singular component and sets its title and border using a BorderFactory
     * @param component The component to be put in the JPanel
     * @param title The title that will be put on the JPanel
     * @return a JPanel containing a title and border
     */
    private JPanel Scroll(JComponent component, String title) {
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createTitledBorder(title));
        wrapperPanel.add(new JScrollPane(component));
        return wrapperPanel;
    }

    public synchronized void botConnected(String s) {
        outputTextArea.append(s + "\n");
    }

    @Override
    public synchronized void propertyChange(PropertyChangeEvent evt) {
        outputTextArea.append(messageFeed.getMessage() + "\n");
    }
}
