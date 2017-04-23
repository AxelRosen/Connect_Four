package model;

import java.util.ArrayList;


public class GameBoard {
	public int[][] board;
	public int i;
	public int row;
	private int[] rowcol;
	private ArrayList<Integer> players;

	public GameBoard() {
		board = new int[7][6];
		rowcol = new int[2];
		players = new ArrayList<Integer>();
		i = 1;
	}

	/**
	 * Finds the place to put the next marker. Depending on the parameter place,
	 * either places the marker or returns what place the next marker should be
	 * placed.
	 *
	 * @param stringRow
	 *            A String that indicates the spot on the board
	 * @param player1or2
	 *            Which player who called the method
	 * @param place
	 *            boolean that decides whether to place the marker or not
	 * @return the int array, either updated or as it was before
	 */
	public int[] putMarker(String stringRow, int player1or2, boolean place) {
		row = convertStringToInt(stringRow);
		if (board[row][0] == 0) {
			for (i = 5; i >= 0; i--) {
				if (board[row][i] == 0) {
					if (place) {
						board[row][i] = player1or2;
					}
					break;
				}
			}
			rowcol[0] = row;
			rowcol[1] = i;
			return rowcol;
		}
		return null;
	}

	/**
	 * Checks the Gameboard, according to the rules, if the player that made the
	 * last move has won and returns the appropriate boolean.
	 *
	 * @param player1or2
	 *            Which player who called the method, either 1 or 2
	 * @return true if the player has won, false otherwise
	 */
	public boolean checkForVictory(int player1or2) {

		// horizontalCheck
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 7; i++) {
				if (board[i][j] == player1or2 && board[i][j + 1] == player1or2 && board[i][j + 2] == player1or2
						&& board[i][j + 3] == player1or2) {
					return true;
				}
			}
		}
		// verticalCheck
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				if (board[i][j] == player1or2 && board[i + 1][j] == player1or2 && board[i + 2][j] == player1or2
						&& board[i + 3][j] == player1or2) {
					return true;
				}
			}
		}
		// ascendingDiagonalCheck
		for (int i = 3; i < 7; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == player1or2 && board[i - 1][j + 1] == player1or2 && board[i - 2][j + 2] == player1or2
						&& board[i - 3][j + 3] == player1or2) {
					return true;
				}
			}
		}
		// descendingDiagonalCheck
		for (int i = 3; i < 7; i++) {
			for (int j = 3; j < 6; j++) {
				if (board[i][j] == player1or2 && board[i - 1][j - 1] == player1or2 && board[i - 2][j - 2] == player1or2
						&& board[i - 3][j - 3] == player1or2) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the Gameboard is full
	 *
	 * @return true if the board is full, false otherwise
	 */
	public boolean boardFull() {
		for (i = 6; i >= 0; i--) {
			if (board[i][0] == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the Gameboard
	 *
	 * @return this Gameboard
	 */
	public int[][] getBoard() {
		return board;
	}

	/**
	 * Converts the String to an int. Only up to seven is needed cause it is the
	 * current maximal width or height of the board
	 *
	 * @param stringRow
	 *            The String representation of the row that should be converted
	 * @return the corresponding row represented as an int.
	 */
	private int convertStringToInt(String stringRow) {
		switch (stringRow) {
		default:
			return 0;
		case "one":
			return 1;
		case "two":
			return 2;
		case "three":
			return 3;
		case "four":
			return 4;
		case "five":
			return 5;
		case "six":
			return 6;
		case "seven":
			return 7;
		}
	}

	/**
	 * Checks if the board is a new one, if so the players are reset and adds a
	 * player to the board.
	 * 
	 * @param id
	 *            The id of the player.
	 */
	public void addPlayerToBoard(int id) {
		if (players.size() == 2) {
			players.clear();
		}
		players.add(id);
	}

	/**
	 * Creates a new empy board.
	 */
	public void reset() {
		board = new int[7][6];
	}

	/**
	 * Returns the player.
	 * 
	 * @param currentPlayer
	 *            The board id of the player, either 1 or 2.
	 * @return the id of the player
	 */
	public int getPlayer(int currentPlayer) {
		return players.get(currentPlayer - 1);
	}

	/**
	 * Checks if the column is full and return the corresponding boolean.
	 * 
	 * @param col
	 *            The column which should be checked
	 * @return true if full or false otherwise
	 */
	public boolean columnFull(String col) {
		if (board[convertStringToInt(col)][0] != 0) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the field on the board has a marker in it returns the
	 * corresponding boolean.
	 * 
	 * @param row
	 *            The row which should be checked
	 * @param col
	 *            The column which should be checked
	 * @return true if full or false otherwise
	 */
	public boolean checkIfMarked(int row, int col) {
		if (board[row][col] == 0) {
			return false;
		}
		return true;
	}

}
