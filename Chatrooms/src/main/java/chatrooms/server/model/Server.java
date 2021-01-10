package chatrooms.server.model;

import chatrooms.server.view.MainServerPanel;

import javax.swing.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server implements Runnable {
	private static  ArrayList <Integer> chatroomPorts;
	private int threadNr;
	public static final int SERVER_PORT = 8888;
	private MainServerPanel panel;

	public Server() {
		threadNr = 1;
		chatroomPorts = new ArrayList<>();
	}

	@Override
	public synchronized void run() {
		try (ServerSocket server = new ServerSocket(SERVER_PORT)){
			setupGUI();

			//noinspection InfiniteLoopStatement
			while (true) {
				threadNr++;
				Socket incoming = server.accept();
				Thread t = new Thread(new ThreaderMainServer(incoming, panel));
				t.start();
				threadNr++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static int getChatroomPort() {
		Random random = new Random();
		return chatroomPorts.get(random.nextInt(chatroomPorts.size()));
	}

	protected static void addChatroomPorts(int port) {
		chatroomPorts.add(port);
	}

	/**
	 * Sets up the frame and the panel for the GUI
	 */

	private void setupGUI() {
		SwingUtilities.invokeLater(() -> {
			panel = new MainServerPanel(SERVER_PORT);
			JFrame frame = new JFrame();

			frame.setContentPane(panel);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		});
	}

	public int getThreadNr() {
		return threadNr;
	}
}
