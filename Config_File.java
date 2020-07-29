package car_data_logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config_File {
	File configFile = new File("config.properties");
	public void attempt_read()
	{
		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			String database_host = props.getProperty("database_host");
			String database_name = props.getProperty("database_name");
			String database_username = props.getProperty("database_username");
			String database_password = props.getProperty("database_password");
			String tty_port = props.getProperty("tty_port");
			String poll_speed = props.getProperty("poll_speed");
			Main.poll_speed = Integer.parseInt(poll_speed);
			Serial_Port.port = tty_port;
			Serial_Port.selected = true;
			UI_Screen_Main.database_host_text.setText(database_host);
			UI_Screen_Main.database_name_text.setText(database_name);
			UI_Screen_Main.database_username_text.setText(database_username);
			UI_Screen_Main.database_password_text.setText(database_password);
			reader.close();
		} catch(FileNotFoundException ex) {
			System.out.println("No Config File Found");
		} catch(IOException ex) {
			System.out.println("Error Reading File");
		}
	}
	public void attempt_write(String database_host, String database_name, String database_username, String database_password)
	{
		try {
			Properties props = new Properties();
			props.setProperty("database_host", database_host);
			props.setProperty("database_name", database_name);
			props.setProperty("database_username", database_username);
			props.setProperty("database_password", database_password);
			props.setProperty("tty_port", Serial_Port.port);
			props.setProperty("poll_speed", "100");
			FileWriter writer = new FileWriter(configFile);
			props.store(writer, "database settings");
			writer.close();
		} catch(FileNotFoundException ex) {
			System.out.println("No Config File Found");
		} catch(IOException ex) {
			System.out.println("Error Reading File");
		}
	}
}
