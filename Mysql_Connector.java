package car_data_logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql_Connector {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	
	public boolean connect() {
		try {

			@SuppressWarnings("deprecation")
			String connection_string = "jdbc:mysql://" + UI_Screen_Main.database_host_text.getText() + "/" + UI_Screen_Main.database_name_text.getText() + "?serverTimezone=America/New_York&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&user=" + UI_Screen_Main.database_username_text.getText() + "&password=" + UI_Screen_Main.database_password_text.getText();
			connect = DriverManager.getConnection(connection_string);
		} catch(SQLException e) {
			if(e.toString().contains("Unknown database"))
				AlertBox.display("MySQL Error", "Database '" + UI_Screen_Main.database_name_text.getText() + "' does not Exist");
			else if(e.toString().contains("Access denied"))
				AlertBox.display("MySQL Error", "Invalid Username / Passsword");
			else if(e.toString().contains("Communications link failure"))
				AlertBox.display("MySQL Error", "Invalid Host");
			else
				AlertBox.display("MySQL Error", e.toString());
			return false;
		}
		return true;
	}
	
	public boolean save_data(String pid1, String response1, String pid2, String response2, String pid3, String response3, String pid4, String response4, String pid5, String response5, String pid6, String response6) {
		try {
			preparedStatement = connect.prepareStatement("INSERT INTO " + UI_Screen_Main.database_name_text.getText() + ".ecu_data VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, pid1);
			preparedStatement.setString(2, response1);
			preparedStatement.setString(3, pid2);
			preparedStatement.setString(4, response2);
			preparedStatement.setString(5, pid3);
			preparedStatement.setString(6, response3);
			preparedStatement.setString(7, pid4);
			preparedStatement.setString(8, response4);
			preparedStatement.setString(9, pid5);
			preparedStatement.setString(10, response5);
			preparedStatement.setString(11, pid6);
			preparedStatement.setString(12, response6);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			AlertBox.display("MySQL Error", e.toString());
			return false;
		}
		return true;
	}
	
	public void close() {
		try {
			if(statement != null) {
				statement.close();
			}
			if(connect != null) {
				connect.close();
			}
		} catch(Exception e) {
			AlertBox.display("MySQL Error", e.toString());
		}
	}
	
}
