package car_data_logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql_Connector {
	static private Connection connect = null;
	static private Statement statement = null;
	static private PreparedStatement preparedStatement = null;
	
	public boolean connect() {
		try {

			@SuppressWarnings("deprecation")
			String connection_string = "jdbc:mysql://" + UI_Screen_Main.database_host_text.getText() + "/" + UI_Screen_Main.database_name_text.getText() + "?serverTimezone=America/New_York&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&user=" + UI_Screen_Main.database_username_text.getText() + "&password=" + UI_Screen_Main.database_password_text.getText();
			connect = DriverManager.getConnection(connection_string);
		} catch(SQLException e) {
			if(e.toString().contains("Unknown database"))
			{
				System.out.println("MySQL Error");
				AlertBox.display("MySQL Error", "Database '" + UI_Screen_Main.database_name_text.getText() + "' does not Exist");
			}
			else if(e.toString().contains("Access denied"))
			{
				System.out.println("MySQL Error");
				AlertBox.display("MySQL Error", "Invalid Username / Passsword " + UI_Screen_Main.database_username_text.getText() + " " + UI_Screen_Main.database_password_text.getText());
			}
			else if(e.toString().contains("Communications link failure"))
			{
				System.out.println("MySQL Error");
				AlertBox.display("MySQL Error", "Invalid Host");
			}
			else
			{
				System.out.println("MySQL Error");
				AlertBox.display("MySQL Error", e.toString());
			}
			return false;
		}
		return true;
	}
	
	public boolean save_data(String response) {
		try {
			preparedStatement = connect.prepareStatement("INSERT INTO " + UI_Screen_Main.database_name_text.getText() + ".ecu_data (`timestamp`, response) VALUES (?, ?)");
			preparedStatement.setLong(1, System.currentTimeMillis());
			preparedStatement.setString(2, response);
			
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
