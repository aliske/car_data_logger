package car_data_logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class UI_MainWindow implements EventHandler<ActionEvent> {
	
	Button btn_reset_elm327;
	Button btn_linefeed_set;
	Button btn_query_pids;
	static TextField txt_port_used;
	Label lbl_port_used;
	VBox layout1 = new VBox(10);
	
	UI_MainWindow() {
		btn_reset_elm327 = new Button("Reset ELM327 Controller");
		btn_reset_elm327.setOnAction(this);
		btn_linefeed_set = new Button("Set Line Feeds");
		btn_linefeed_set.setOnAction(this);
		btn_query_pids = new Button("Query ECU PIDs");
		btn_query_pids.setOnAction(this);
		txt_port_used = new TextField();
		txt_port_used.setDisable(true);;
		lbl_port_used = new Label("Port Used:");
		layout1.setPadding(new Insets(20,20,20,20));
		layout1.getChildren().addAll(lbl_port_used, txt_port_used, btn_reset_elm327, btn_linefeed_set, btn_query_pids);
	}
	
	public VBox get_layout()
	{
		return layout1;
	}

	public void handle(ActionEvent event) {
		try {
			if(event.getSource() == btn_linefeed_set)
				Serial_Port.sendStringToComm("AT L1");
			else if(event.getSource() == btn_query_pids)
				Serial_Port.sendStringToComm("0100");
			else if(event.getSource() == btn_reset_elm327)
				Serial_Port.sendStringToComm("AT Z");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
