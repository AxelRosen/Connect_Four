package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Database;
import model.GameBoard;
import model.Main;

public class HighScoreController {
	private Main main;
	private GameBoard board;
	private Database db;

	@FXML
	private TextField firstName;

	@FXML
	private TextField firstScore;

	@FXML
	private TextField secondName;

	@FXML
	private TextField secondScore;

	@FXML
	private TextField thirdScore;

	@FXML
	private TextField thirdName;

	/**
	 * Updates the textfields with the top three players in the database.
	 */
	public void updateScore() {
		try {
			String s = db.printTopThree();
			String[] split = s.split(" ");
			firstName.setText(split[0]);
			firstScore.setText(split[1]);
			secondName.setText(split[2]);
			secondScore.setText(split[3]);
			thirdName.setText(split[4]);
			thirdScore.setText(split[5]);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}

	/**
	 * Resets the Gameboard and then calls the createMenu method in the Main
	 * class.
	 * 
	 */
	@FXML
	void goToMenu(ActionEvent event) {
		board.reset();
		main.createMenu();
	}

	/**
	 * Sets the main in this class to the main being used.
	 * 
	 * @Param Main the object of the class Main.
	 */
	public void setMain(Main main) {
		this.main = main;
	}

	/**
	 * Sets the board in this class to the board being used.
	 * 
	 * @Param Board the object of the class Board.
	 */
	public void setBoard(GameBoard board) {
		this.board = board;
	}

	/**
	 * Sets the database in this class to the database being used.
	 * 
	 * @Param Database the object of the class Database.
	 */
	public void setDb(Database db) {
		this.db = db;
	}

}
