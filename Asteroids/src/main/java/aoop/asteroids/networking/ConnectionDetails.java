package aoop.asteroids.networking;

import java.net.InetAddress;

public class ConnectionDetails {

	private final InetAddress ip;

	private final int port;

	/**
	 * Constructor
	 */
	public ConnectionDetails(InetAddress ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	/**
	 * Getter
	 * @return ip
	 */
	public InetAddress getIp() {
		return ip;
	}

	/**
	 * Getter
	 * @return port
	 */
	public int getPort() {
		return port;
	}
}
