package aoop.asteroids.networking;

public enum PacketType {

	JOIN(0), // For connecting to server
	MODEL(1), // This will be for sending important updates
	UNIMPORTANT(2), // This will be for sending unimportant messages
	DISCONNECT(3), // This will be used when a player decides to disconnect form the server
	END(4); // This will be used when the host leaves and it will disconnect others from the server

	private final int packetType;

	PacketType(int packetType) {
		this.packetType = packetType;
	}

	/**
	 * Sorting the packets into several types.
	 */
	public static PacketType findType(String packetType) {
		switch (packetType) {
			case "END":
				return END;
			case "DISCONNECT":
				return DISCONNECT;
			case "UNIMPORTANT":
				return UNIMPORTANT;
			case "MODEL":
				return MODEL;
			case "JOIN":
				return JOIN;
		}
		return null;
	}
}
