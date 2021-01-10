package aoop.asteroids.control.game_database;

import javax.persistence.*;
import java.util.ArrayList;

public class DataBaseManager {

	private static final String FILE_NAME = "scoreboard";
	private static EntityManager entityManager;

	private static EntityManagerFactory entityManagerFactory;

	/**
	 * Method which creates a database which stores unique players.
	 */
	private static void createDatabase(){
		entityManagerFactory = Persistence.createEntityManagerFactory( "" + "db/" + FILE_NAME + ".odb");
		entityManager = entityManagerFactory.createEntityManager();
	}

	/**
	 * Method to close the database (it's not needed anymore).
	 */
	private static void closeDatabase(){
		entityManager.close();
		entityManagerFactory.close();
	}

	/**
	 * Method to add the score of a player to the database.
	 * @param playerName the name of the player
	 * @param score score of the player
	 */
	public static void addScore(String playerName, int score) {
		createDatabase();
		entityManager.getTransaction().begin();

		Score playerScore = new Score(playerName, score);
		entityManager.persist(playerScore);
		entityManager.getTransaction().commit();
		closeDatabase();
	}

	/**
	 * Method that deletes all scores from the database.
	 */
	public static void deleteScores(){
		createDatabase();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("DELETE FROM Score");
		query.executeUpdate();
		entityManager.getTransaction().commit();
		closeDatabase();
	}

	/**
	 * Method to get all the players and their respective score, using a query, and ordering them as a list in a
	 * descending order.
	 */
	public static ArrayList<Score> queryScoreboard(){
		createDatabase();
		entityManager.getTransaction().begin();
		TypedQuery<Score> query =  entityManager.createQuery("SELECT s FROM Score s ORDER BY s.score DESC", Score.class);

		ArrayList<Score> queryResultList = (ArrayList<Score>) query.getResultList();
		closeDatabase();
		return  queryResultList;
	}
}
