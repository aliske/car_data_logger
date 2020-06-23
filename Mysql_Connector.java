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
	
	public void connect() {
		try {

			@SuppressWarnings("deprecation")
			String connection_string = "jdbc:mysql://" + UI_Screen_Main.database_host_text.getText() + "/" + UI_Screen_Main.database_name_text.getText() + "?user=" + UI_Screen_Main.database_username_text.getText() + "&password=" + UI_Screen_Main.database_password_text.getText();
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
		}
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
