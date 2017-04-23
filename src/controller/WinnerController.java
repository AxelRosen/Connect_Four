package controller;
/**
 * Sample Skeleton for 'Menu.fxml' Controller Class
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.GameBoard;
import model.Main;

public class WinnerController {
	private Main main;
	private GameBoard board;

	@FXML
	private TextField winnerField;

	/**
	 * Sets the textField in the WinnerPopUp view
	 * 
	 * @Param String the name of the winner
	 */
	public void setField(String winner) {
		winnerField.setText(winner);
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
	 * Calls the createGame method in the Main class.
	 */
	@FXML
	void playAgain(ActionEvent event) {
		board.reset();
		main.createGame();
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

}
