package car_data_logger;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.Stage;

public class Main extends Application {
	static Serial_Port sp = null;
	
	public static void main(String[] args) {
		sp = new Serial_Port();
		Interpreter interpreter = new Interpreter();
		interpreter.input_string("41 11 2F 05 6E");
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
		sp.init();
	}
}
