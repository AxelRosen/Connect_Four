package controller;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Database;
import model.GameBoard;
import model.Main;

public class BoardController {
	private Main main;
	private GameBoard board;
	private Database db;
	private int[] intRowCol = new int[2];
	private int[] nextRowCol = new int[2];
	private int currentPlayer = 1;
	private PathTransition transition = new PathTransition();

	@FXML
	private VBox background;

	@FXML
	private GridPane grid;

	@FXML
	private Region region;

	@FXML
	private TextField winnerField;

	/**
	 * Calls the method createGame from the class Main.
	 * 
	 * @Param ActionEvent the event of what happened
	 */
	@FXML
	void newGame(ActionEvent event) {
		main.createGame();
	}

	/**
	 * Calls the method createMenu from the class Main.
	 * 
	 * @Param ActionEvent the event of what happened
	 */
	@FXML
	void goToMenu(ActionEvent event) {
		main.createMenu();
	}

	/**
	 * Calls the method playAgain from the class Main.
	 * 
	 * @Param ActionEvent the event of what happened
	 */
	@FXML
	void playAgain(ActionEvent event) {
		main.createGame();
	}

	/**
	 * Sets the main in this class to the main being used.
	 * Also resets the int vector nextRowCol.
	 * 
	 * @Param Main the object of the class Main.
	 */
	public void setMain(Main main) {
		this.main = main;
		nextRowCol[0] = 0;
		nextRowCol[1] = 0;
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
	 * Gets the id from the square in the grid being clicked. Updates the
	 * variable intRowCol with what position in the grid that should be painted.
	 * Also switches the turn of the players.
	 * 
	 * @Param ActionEvent the event of what happened
	 */
	public void updateGui(MouseEvent event) {
		String stringRegion = event.getPickResult().getIntersectedNode().getId();
		String[] rowcol = stringRegion.split("_");
		try {
			if (currentPlayer == 1) {
				intRowCol = board.putMarker(rowcol[0], currentPlayer, true);
				db.addToLogDB(db.getPlayer(board.getPlayer(currentPlayer)),
						"placed their marker on row: " + (intRowCol[0]) + " column: " + intRowCol[1]);
				playerPaint(currentPlayer);
				currentPlayer = 2;
			} else {
				intRowCol = board.putMarker(rowcol[0], currentPlayer, true);
				db.addToLogDB(db.getPlayer(board.getPlayer(currentPlayer)),
						"placed their marker on row: " + (intRowCol[0]) + " column: " + intRowCol[1]);
				playerPaint(currentPlayer);
				currentPlayer = 1;
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Gets the id from the square in the grid being hovered. Updates the
	 * variable nextRowCol with what position in the grid that should be
	 * painted. Clears the last grid position by drawing a rectangle in it. Also
	 * switches the turn of the players.
	 * 
	 * @Param ActionEvent the event of what happened
	 */
	@FXML
	public void showNextMove(MouseEvent event) {
		String stringRegion = event.getPickResult().getIntersectedNode().getId();
		String[] rowcol = stringRegion.split("_");
		if (!board.checkIfMarked(nextRowCol[0], nextRowCol[1])) {
			Rectangle eraseRectangle = eraseMarker();
			grid.add(eraseRectangle, nextRowCol[0], nextRowCol[1]);
		}
		if (!board.columnFull(rowcol[0])) {
			try {
				if (currentPlayer == 1) {
					nextRowCol = board.putMarker(rowcol[0], currentPlayer, false);
					nextMove(currentPlayer);
				} else {
					nextRowCol = board.putMarker(rowcol[0], currentPlayer, false);
					nextMove(currentPlayer);
				}
			} catch (Exception e) {
			}
			// }
		}
	}

	/**
	 * Adds a low opacity ellipse to the grid, changes the color depending on
	 * the player.
	 * 
	 * @Param int the id of the player, either 1 or 2.
	 */
	private void nextMove(int id) {
		Ellipse ellipse = drawEllipse();
		ellipse.radiusXProperty().bind(grid.widthProperty().divide(14.2));
		ellipse.radiusYProperty().bind(grid.heightProperty().divide(12.2));
		ellipse.setOpacity(0.3);
		if (id == 1) {
			ellipse.setFill(Color.RED);
		} else {
			ellipse.setFill(Color.BLUE);
		}
		grid.add(ellipse, nextRowCol[0], nextRowCol[1]);
	}

	/**
	 * Adds a circle to the grid with a falling animation. Checks if the board
	 * has a winner or if the board is full, reacts by calling methods in the
	 * Main class.
	 * 
	 * @Param int currentPlayer is the id of the current player.
	 */
	public void playerPaint(int currentPlayer) {
		Rectangle eraseRectangle = eraseMarker();
		Ellipse ellipse = drawEllipse();
		if (currentPlayer == 1) {
			ellipse.setFill(Color.RED);
		} else {
			ellipse.setFill(Color.BLUE);
		}
		transition.setNode(ellipse);
		transition.setDuration(Duration.seconds(0.1));
		Line line = new Line();
		line.setStartX(0.0f);
		line.setStartY(-900.0f);
		line.setEndX(0.0f);
		transition.setPath(line);
		transition.setCycleCount(1);
		transition.play();
		grid.add(eraseRectangle, intRowCol[0], intRowCol[1]);
		grid.add(ellipse, intRowCol[0], intRowCol[1]);
		if (board.checkForVictory(currentPlayer)) {
			grid.setDisable(true);
			db.addToLogDB(db.getPlayer(board.getPlayer(currentPlayer)), "won the game!");
			main.winnerPopUp(board.getPlayer(currentPlayer));
		}
		if (board.boardFull()) {
			main.winnerPopUp(3);
		}
	}

	/**
	 * Creates a rectangle that will be used to clear the last grid position.
	 * 
	 * @Return Rectangle the rectangle that will be painted on the grid.
	 */
	private Rectangle eraseMarker() {
		Rectangle eraseRectangle = new Rectangle(-20, -20, (grid.getWidth() / 7.1), grid.getHeight() / 6.1);
		eraseRectangle.widthProperty().bind(grid.widthProperty().divide(7.1));
		eraseRectangle.heightProperty().bind(grid.heightProperty().divide(6.1));
		eraseRectangle.setMouseTransparent(true);
		eraseRectangle.setFill(Color.valueOf("#f4f4f4"));
		return eraseRectangle;
	}

	/**
	 * Creates an ellipse that will be used to fill the grid position.
	 * 
	 * @Return Ellipse the ellipse that will be painted on the grid.
	 */
	private Ellipse drawEllipse() {
		Ellipse ellipse = new Ellipse(grid.getWidth() / 14, grid.getHeight() / 12);
		ellipse.radiusXProperty().bind(grid.widthProperty().divide(14));
		ellipse.radiusYProperty().bind(grid.heightProperty().divide(12));
		ellipse.setMouseTransparent(true);
		return ellipse;
	}

	/**
	 * Sets the database to the one being used.
	 * 
	 * @Param Database db the database being used.
	 */
	public void setDB(Database db) {
		this.db = db;
	}

}
