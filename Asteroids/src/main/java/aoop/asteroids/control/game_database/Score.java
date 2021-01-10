package aoop.asteroids.control.game_database;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Score implements Serializable {

	/**
	 * Name of the player, used to identify who's score is it.
	 */
	private String playerName;

	/**
	 * The actual score of a player.
	 * */
	private int score;

	/**
	 * Constructor
	 * @param playerName name of the player
	 * @param score the score of the layer
	 */
	public Score (String playerName, int score){
		this.playerName = playerName;
		this.score = score;

	}

	/** Converts the name of the player and it's score into a string of the format : PLayer1 17.
	 */
	public String toString(){
		return String.format("(%s,%d)", playerName, score);
	}

	/**
	 * Getter to return the name of the player
	 */
	public String getName(){
		return playerName;
	}

	/**
	 * Getter to return the score of the player
	 */
	public int getScore(){
		return score;
	}
}
