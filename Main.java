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
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(query_stats, 0, 500000000, TimeUnit.NANOSECONDS);
			Stage monitorStage = new Stage();
			UI_MonitorWindow monitor = new UI_MonitorWindow();
			monitorStage.setTitle("OBDII Monitor");
			monitorStage.centerOnScreen();
			monitorStage.setScene(monitor.get_scene());
			monitorStage.show();
		}
	}
	int command_count = 0;
	Runnable query_stats = new Runnable() {
	    public void run() {
	        try {
	        	if(command_count == 0)
	        		Serial_Port.sendStringToComm("01 0C 0D 0F 11");
	        	else if(command_count == 1)
	        		Serial_Port.sendStringToComm("01 05 5C 2F");
	        	
	        	System.out.println("Command_count: " + command_count);
	        	command_count++;
	        	if(command_count == 2)
	        		command_count = 0;
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	};
}
