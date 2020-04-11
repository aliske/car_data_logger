/*
 * Aaron Liske
 * Car Data Logger
 * "Monitor" Window Class using JavaFX
 */

package car_data_logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class UI_MonitorWindow {
	
	Label lbl_speed = new Label("Speed");
	Label lbl_oil_temp = new Label("Oil Temp");
	Label lbl_coolant_temp = new Label("Coolant Temp");
	Label lbl_throttle_pos = new Label("Throttle");
	Label lbl_fuel_level = new Label("Fuel Level");
	Label lbl_intake_air_temp = new Label("Intake Temp");
	Label lbl_rpm = new Label("Engine RPM");
	
	static Label lbl_speed_value = new Label("0");
	static Label lbl_oil_temp_value = new Label("0");
	static Label lbl_coolant_temp_value = new Label("0");
	static Label lbl_throttle_pos_value = new Label("0");
	static Label lbl_fuel_level_value = new Label("0");
	static Label lbl_intake_air_temp_value = new Label("0");
	static Label lbl_rpm_value = new Label("0");
	
	GridPane gridpane = new GridPane();
	
	UI_MonitorWindow() {
		gridpane.setMinSize(640, 480);
		gridpane.setPadding(new Insets(10,10,10,10));
		gridpane.setVgap(40);
		gridpane.setHgap(75);
		gridpane.setAlignment(Pos.CENTER);
		gridpane.add(lbl_speed, 0, 0);
		gridpane.add(lbl_fuel_level, 1, 0);
		gridpane.add(lbl_throttle_pos, 2, 0);
		gridpane.add(lbl_speed_value, 0, 1);
		gridpane.add(lbl_fuel_level_value, 1, 1);
		gridpane.add(lbl_throttle_pos_value, 2, 1);
		
		gridpane.add(lbl_rpm, 1, 2);
		gridpane.add(lbl_rpm_value, 1, 3);
		
		gridpane.add(lbl_coolant_temp, 0, 4);
		gridpane.add(lbl_oil_temp, 1, 4);
		gridpane.add(lbl_intake_air_temp, 2, 4);
		gridpane.add(lbl_coolant_temp_value, 0, 5);
		gridpane.add(lbl_oil_temp_value, 1, 5);
		gridpane.add(lbl_intake_air_temp_value, 2, 5);
		
		lbl_speed.setFont(new Font(24));
		lbl_fuel_level.setFont(new Font(24));
		lbl_throttle_pos.setFont(new Font(24));
		lbl_coolant_temp.setFont(new Font(24));
		lbl_oil_temp.setFont(new Font(24));
		lbl_intake_air_temp.setFont(new Font(24));
		lbl_rpm.setFont(new Font(24));
		
		lbl_speed_value.setFont(new Font(36));
		lbl_fuel_level_value.setFont(new Font(36));
		lbl_throttle_pos_value.setFont(new Font(36));
		lbl_coolant_temp_value.setFont(new Font(36));
		lbl_oil_temp_value.setFont(new Font(36));
		lbl_intake_air_temp_value.setFont(new Font(36));
		lbl_rpm_value.setFont(new Font(36));
		
		GridPane.setHalignment(lbl_intake_air_temp, HPos.CENTER);
		GridPane.setHalignment(lbl_oil_temp, HPos.CENTER);
		GridPane.setHalignment(lbl_coolant_temp, HPos.CENTER);
		GridPane.setHalignment(lbl_throttle_pos, HPos.CENTER);
		GridPane.setHalignment(lbl_fuel_level, HPos.CENTER);
		GridPane.setHalignment(lbl_speed, HPos.CENTER);
		GridPane.setHalignment(lbl_rpm, HPos.CENTER);
		
		GridPane.setHalignment(lbl_intake_air_temp_value, HPos.CENTER);
		GridPane.setHalignment(lbl_oil_temp_value, HPos.CENTER);
		GridPane.setHalignment(lbl_coolant_temp_value, HPos.CENTER);
		GridPane.setHalignment(lbl_throttle_pos_value, HPos.CENTER);
		GridPane.setHalignment(lbl_fuel_level_value, HPos.CENTER);
		GridPane.setHalignment(lbl_speed_value, HPos.CENTER);
		GridPane.setHalignment(lbl_rpm_value, HPos.CENTER);
	}
	
	
	public Scene get_scene()
	{
		Scene scene = new Scene(get_layout(), 640, 480);
		return scene;
	}
	
	private GridPane get_layout()
	{
		return gridpane;
	}

	public void handle(ActionEvent event) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
