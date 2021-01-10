package aoop.asteroids.view.panels;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionPanel extends JPanel {

	private final JTextField port;
	private JTextField IP;

	/**
	 * Draws the ip and the port
	 */
	public ConnectionPanel(Container connectionsContainer) {
		setBackground(Color.black);
		String ipAddress;
		setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel ipLabel = (JLabel) add(new JLabel("IP : "));
		ipLabel.setForeground(Color.RED);

		try {
			ipAddress = InetAddress.getLocalHost().toString();
			String[] string = ipAddress.split("/");
			IP = new JTextField(string[1]);
			IP.setPreferredSize(new Dimension(120, 20));
			add(IP);
		} catch (UnknownHostException e) {
			System.err.println("Couldn't generate the IP address.");
		}

		JLabel portLabel = (JLabel) add(new JLabel("Port :"));
		portLabel.setForeground(Color.RED);
		port = new JTextField("8080");
		port.setPreferredSize(new Dimension(55, 20));
		add(port);
		connectionsContainer.add(this);
	}

	/**
	 * Getter
	 * @return ip in form of string.
	 */
	public String getIP() {
		return IP.getText();
	}

	/**
	 * Getter
	 * @return the port in form of a integer.
	 */
	public int getPort() {
		return Integer.parseInt(port.getText());
	}
}
