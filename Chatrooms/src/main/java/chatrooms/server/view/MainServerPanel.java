package chatrooms.server.view;

import javax.swing.*;
import java.awt.*;

public class MainServerPanel extends JPanel {
	
	private final JTextArea outputTextArea;

	private final int ROWS = 40;
	private final int COLUMNS = 50;
	private final int GAP = 3;

	public MainServerPanel(int port) {
		outputTextArea = new JTextArea(ROWS, COLUMNS);

		outputTextArea.setFocusable(false);
		outputTextArea.setEditable(false);

		//outputTextArea.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
		setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(Scroll(outputTextArea, " Main Server " + port));
	}
	/**
	 * * Creates a new JPanel of a singular component and sets its title and border using a BorderFactory
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

	public synchronized void chatroomConnected(int port) {
		outputTextArea.append("Chatroom " + port + " informed server about the port it's listening to! \n" );
		outputTextArea.append(port + " Was successful added to the active chatroom list. \n");
	}

	public synchronized void botConnected(String s) {
		if (s != null) {
			if (s.charAt(0) == '0') {
				outputTextArea.append("Bot " + s.substring(1) + " connected to the server\n");
			} else {
				outputTextArea.append("Bot " + s + " is changing chatrooms\n");
			}
		}
	}
}
