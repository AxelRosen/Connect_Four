package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.GameBoard;
import model.Main;

public class MainController {
	private Main main;
	private GameBoard board;

	@FXML
	private Button highScoreBtn;
	//
	@FXML
	private TextField playerName1;

	@FXML
	private TextField playerName2;

	@FXML
	void addPlayer1(ActionEvent event) {
		main.addPlayer1();
	}

	@FXML
	public void showHighScore(ActionEvent event) {
		main.showHighScore();
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
	 * Takes the name entered in the EnterPlayer1 view and adds it to the
	 * database if it does not exist
	 * 
	 * @Param ActionEvent the event of what happened
	 */
	public void enterPlayer1(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			if (main.appendPlayer(playerName1.getText())) {
				main.addPlayer2();
			} else {
				playerName1.setText("Name exists, choose a new one");
			}
		}
	}

	/**
	 * Takes the name entered in the EnterPlayer2 view and adds it to the
	 * database if it does not exist
	 * 
	 * @Param ActionEvent the event of what happened
	 */
	public void enterPlayer2(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			if (main.appendPlayer(playerName2.getText())) {
				main.createGame();
			} else {
				playerName2.setText("Name exists, choose a new one");
			}
		}
	}

	/**
	 * Calls the method showLog from the class Main.
	 * 
	 * @Param ActionEvent the event of what happened
	 */
	@FXML
	public void showLog(ActionEvent event) {
		main.showLog();
	}

}