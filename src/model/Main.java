package model;

import java.io.IOException;

import controller.BoardController;
import controller.HighScoreController;
import controller.LogController;
import controller.MainController;
import controller.WinnerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage primaryStage;
	private GameBoard board = new GameBoard();
	private BorderPane layout;
	private Database db;
	private int id;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Connect Four");
		db = new Database();

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StartView.fxml"));
			layout = (BorderPane) loader.load();
			Scene scene = new Scene(layout, 933.33, 800);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
			this.primaryStage.setResizable(true);
			createMenu();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the view for the menu. Also changes the controller.
	 */
	public void createMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Menu.fxml"));
			Pane mainPane = (Pane) loader.load();
			layout.setCenter(mainPane);
			MainController controller = loader.getController();
			controller.setMain(this);
			controller.setBoard(board);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the view for adding player 1. Also changes the controller.
	 */
	public void addPlayer1() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EnterPlayer1.fxml"));
			Pane mainPane = (Pane) loader.load();
			layout.setCenter(mainPane);
			mainPane.requestFocus();
			MainController controller = loader.getController();
			controller.setMain(this);
			controller.setBoard(board);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the view for adding player 2. Also changes the controller.
	 */
	public void addPlayer2() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EnterPlayer2.fxml"));
			Pane mainPane = (Pane) loader.load();
			layout.setCenter(mainPane);
			mainPane.requestFocus();
			MainController controller = loader.getController();
			controller.setMain(this);
			controller.setBoard(board);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Creates the view for a new Gameboard. Also changes the controller.
	 */
	public void createGame() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Board.fxml"));
			Pane gamePane = (Pane) loader.load();
			layout.setCenter(gamePane);
			BoardController controller = loader.getController();
			controller.setMain(this);
			controller.setBoard(board);
			controller.setDB(db);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Creates the view for showing the high score. Also changes the controller
	 * and updates the score.
	 */
	public void showHighScore() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HighScore.fxml"));
			Pane gamePane = (Pane) loader.load();
			layout.setCenter(gamePane);
			HighScoreController controller = loader.getController();
			controller.setMain(this);
			controller.setBoard(board);
			controller.setDb(db);
			controller.updateScore();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the view for the winner or tie screen. Changes the controller.
	 * Also fills the text area with the appropriate text.
	 * 
	 * @Param id, the id of the winning player.
	 */
	public void winnerPopUp(int id) {
		db.updateScore(id);
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WinnerPopUp.fxml"));
			Pane gamePane = (Pane) loader.load();
			layout.setCenter(gamePane);
			WinnerController controller = loader.getController();
			controller.setMain(this);
			controller.setBoard(board);
			if (id == 3) {
				controller.setField("It's a tie!");
			} else {
				controller.setField("Winner is " + db.getPlayer(id));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the view for showing the log. Also changes the controller.
	 */
	public void showLog() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Log.fxml"));
			Pane gamePane = (Pane) loader.load();
			layout.setCenter(gamePane);
			LogController controller = loader.getController();
			controller.setMain(this);
			controller.updateLog(db.printLog());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the entered player name is taken. If it is not taken it is
	 * added to the database and board.
	 * 
	 * @Param name, the name of the player that should be added
	 * @Returns true if the player was added, false otherwise.
	 */
	public boolean appendPlayer(String name) {
		if (!db.hasPlayer(name)) {
			id = db.getLastId();
			Player player = new Player(id, name);
			db.addToDB(player.getId(), player.getName());
			board.addPlayerToBoard(player.getId());
			return true;
		} else {
			return false;
		}
	}

	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
