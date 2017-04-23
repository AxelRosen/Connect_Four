package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Database {
	private Statement st;
	private ResultSet rs;
	private String sql;
	private StringBuilder sb;
	private int rowCount;

	/**
	 * Creates two connections the databases, creation of the tables only needed
	 * once hence they are commented out.
	 * 
	 */
	public Database() {
		sb = new StringBuilder();

		try {
			Class.forName("org.h2.Driver");
			Connection conn1 = DriverManager
					.getConnection("jdbc:h2:~/Documents/LunicoreProgge/Connect_Four/src/model/PlayerDB", "", "");
			Connection conn2 = DriverManager
					.getConnection("jdbc:h2:~/Documents/LunicoreProgge/Connect_Four/src/model/LogDB", "", "");
			st = conn1.createStatement();
			st = conn2.createStatement();
			// st.execute("CREATE TABLE INFO(ID INT PRIMARY KEY, NAME VARCHAR(255), SCORE INT);");
			// st.execute("CREATE TABLE LOG(TIMESTAMP VARCHAR(255), NAME VARCHAR(255) , MOVE VARCHAR(255));");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Returns the size size of the database.
	 * 
	 * @Return int the size of the database.
	 */
	public int getLastId() {
		try {
			rs = st.executeQuery("SELECT * FROM INFO;");
			if (rs.last()) {
				rowCount = rs.getRow();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	/**
	 * Creates the view for adding player 1. Also changes the controller.
	 * 
	 * @Param int the id of the player being added,
	 */
	public void addToDB(int id, String name) {
		sql = "INSERT INTO INFO VALUES(" + id + ", '" + name + "', 0);";
		try {
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints the top three players from the player database.
	 * 
	 * @Return String the string with the top three players, with their name and
	 *         score.
	 */
	public String printTopThree() {
		sb.setLength(0);
		try {
			rs = st.executeQuery("SELECT * FROM INFO ORDER BY SCORE DESC LIMIT 3;");
			while (rs.next()) {
				sb.append(rs.getString("NAME") + " " + rs.getInt("SCORE") + " ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * Update the score in the player database by incrementing it with one.
	 * 
	 * @Param id the id of the corresponding player
	 */
	public void updateScore(int id) {
		try {
			st.executeUpdate("UPDATE INFO SET SCORE = SCORE + 1 WHERE id = " + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets and returns the player with the corresponding id from the player
	 * database.
	 * 
	 * @Param id the id of the corresponding player
	 * @Return String the name of the corresponding player.
	 */
	public String getPlayer(int id) {
		try {
			rs = st.executeQuery("SELECT * FROM INFO WHERE id = " + id + ";");
			if (rs.next()) {
				return rs.getString("NAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Checks the player database if the player exists.
	 * 
	 * @Param String the name of the corresponding player
	 * @Return boolean true if the player exists, false otherwise.
	 */
	public boolean hasPlayer(String text) {
		try {
			rs = st.executeQuery("SELECT * FROM INFO WHERE NAME like '%" + text + "%';");
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Adds the information to the log database.
	 * 
	 * @Param name the name of the corresponding player
	 * @Param move the information about what happened on the gameboard
	 */
	public void addToLogDB(String name, String move) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		sql = "INSERT INTO LOG VALUES('" + timestamp + "', '" + name + "', '" + move + "');";
		try {
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints the information from the log database.
	 * 
	 * @Return String the string with all the information from the log database.
	 */
	public String printLog() {
		sb.setLength(0);
		try {
			rs = st.executeQuery("SELECT * FROM LOG;");
			while (rs.next()) {
				sb.append(rs.getString("TIMESTAMP") + "\tPlayer with the name " + rs.getString("NAME") + " "
						+ rs.getString("MOVE") + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}