package car_data_logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.Stage;

public class Main extends Application {
	static Serial_Port sp = null;
	
	public static void main(String[] args) {
		sp = new Serial_Port();
		launch(args);
	}
	
	@Override
	public void start(Stage mainStage) throws Exception {
		UI_StartWindow main_window = new UI_StartWindow();
		mainStage.setTitle("OBDII Port Recorder");
		mainStage.centerOnScreen();
		Scene scene_MainWindow = new Scene(main_window.get_layout(), 300, 250);
		mainStage.setScene(scene_MainWindow);
		mainStage.show();
		if(sp.init())
		{
			Thread thread = new Thread(new Runnable() {

	            @Override
	            public void run() {
	                Runnable updater = new Runnable() {
	                	int command_count = 0;
	                    @Override
	                    public void run() {
	                    	try {
	            	        	if(command_count == 0)
	            	        		Serial_Port.sendStringToComm("01 2F 0C");
	            	        		//Serial_Port.sendStringToComm("01 0C 0F");
	            	        	else if(command_count == 1)
	            	        		Serial_Port.sendStringToComm("01 0F 0D");
	            	        	else if(command_count == 2)
	            	        		Serial_Port.sendStringToComm("01 05 11");
	            	        	command_count++;
	            	        	if(command_count == 3)
	            	        		command_count = 0;
	            	        	UI_MonitorWindow.lbl_speed_value.setText(UI_Data_Store.speed);
	            	        	UI_MonitorWindow.lbl_throttle_pos_value.setText(UI_Data_Store.throttle);
	            	        	UI_MonitorWindow.lbl_fuel_level_value.setText(UI_Data_Store.fuel);
	            	        	UI_MonitorWindow.lbl_intake_air_temp_value.setText(UI_Data_Store.intake_temp);
	            	        	UI_MonitorWindow.lbl_rpm_value.setText(UI_Data_Store.rpm);
	            	        	UI_MonitorWindow.lbl_coolant_temp_value.setText(UI_Data_Store.coolant_temp);
	            			} catch (Exception e) {
	            				e.printStackTrace();
	            			}
	                    }
	                };

	                while (true) {
	                    try {
	                        Thread.sleep(1000);
	                    } catch (InterruptedException ex) {
	                    }

	                    // UI update is run on the Application thread
	                    Platform.runLater(updater);
	                }
	            }

	        });
	        // don't let thread prevent JVM shutdown
	        thread.setDaemon(true);
	        thread.start();
	        
			Stage monitorStage = new Stage();
			UI_MonitorWindow monitor = new UI_MonitorWindow();
			monitorStage.setTitle("OBDII Monitor");
			monitorStage.centerOnScreen();
			monitorStage.setScene(monitor.get_scene());
			monitorStage.show();
		}
	}
	
}
