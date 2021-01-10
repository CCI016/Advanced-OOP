package chatrooms.bots.model;

import chatrooms.bots.control.KillButtonListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class BotManager {

	private final ArrayList<Bot> bots;
	private static final Random rn = new Random();
	private static final int NUM_BOTS = rn.nextInt((20 - 15) + 1) + 15;
	private static final int MIG_BOTS_NUM = rn.nextInt((NUM_BOTS)) + 1;
	private static final int LOC_BOTS_NUM = NUM_BOTS - MIG_BOTS_NUM;

	/**
	 * Constructor
	 */

	public BotManager() {
		bots = new ArrayList<>();
		System.out.println(NUM_BOTS);
		setupGui();
		startAllBots();
	}


	/**
	 * Method that starts all the bots
	 */

	private void startAllBots() {
		ThreadPoolExecutor botExe = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUM_BOTS);
		for (int i = 0; i <MIG_BOTS_NUM; i++) { // First slots of the array will be filled with the migratory bots
			MigratoryBot bot = new MigratoryBot(i);
			bots.add(bot);
			botExe.submit(bot);
		}
		for (int i = MIG_BOTS_NUM; i < NUM_BOTS; i++) { // The remaining positions in the array will be filled with local bots
			LocalBot bot = new LocalBot(i);
			bots.add(bot);
			botExe.submit(new LocalBot(i));
		}
	}

	/**
	 * Method that stops all the bots
	 */

	public void stopAllBots() {
		for (Bot bot : bots) {
			bot.shutDown();
		}
	}

	/**
	 * Method that sets up the basic GUI for the bot-kill button
	 */

	private void setupGui() {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame();
			JPanel panel = new JPanel();

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(200, 200);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			frame.add(panel);

			JButton kill = new JButton("Stop Bots");
			kill.addActionListener(new KillButtonListener(this));
			kill.setPreferredSize(new Dimension(100, 100));

			panel.add(kill);

		});
	}
}