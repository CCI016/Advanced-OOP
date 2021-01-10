package aoop.asteroids.udp;


import aoop.asteroids.networking.PacketHandler;
import aoop.asteroids.networking.PacketType;
import aoop.asteroids.networking.Server;
import aoop.asteroids.model.Spaceship;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.net.DatagramSocket;
import java.net.SocketException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PacketSendingTest {

	/**
	 * Verify type
	 */
	@Test
	public void testShipTransform() {
		Spaceship spaceship = new Spaceship();
		spaceship.setPlayerNick("HALELUIA");
		spaceship.setShipColor(Color.RED);
		String[] ship = PacketHandler.fromByteToString(PacketHandler.getShipPacket(spaceship)).split("@");
		assertEquals(PacketType.MODEL, PacketType.findType(ship[0]));
		assertNotEquals("AsteroidsUpdate", ship[1]);
		assertEquals(ship[10], spaceship.getShipMaster());
		assertEquals(ship[2], spaceship.getID());
	}

	@Test
	public void testPreparing() {
		String[] strings = PacketHandler.fromByteToString(PacketHandler.prepareJoinPacket()).split("@");
		assertEquals("JOIN", strings[0]);
		assertNotEquals("DISCONNECT", strings[0]);
	 	strings = PacketHandler.fromByteToString(PacketHandler.prepareDisconnectPacket()).split("@");
		assertNotEquals("JOIN", strings[0]);
		assertEquals("DISCONNECT", strings[0]);
		strings = PacketHandler.fromByteToString(PacketHandler.prepareEndPacket()).split("@");
		assertNotEquals("JOIN", strings[0]);
		assertNotEquals("DISCONNECT", strings[0]);
		assertEquals("END", strings[0]);
	}

	@Test
	public void testServer() {
		Server server = new Server(7777);
		try {
			DatagramSocket ds = new DatagramSocket(7777);
		} catch (SocketException e) {
			System.out.println("Server Opens");
		}
	}

}
