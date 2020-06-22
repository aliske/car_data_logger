/*
 * Aaron Liske
 * Car Data Logger
 * Latest Revision: 4/11/2020
 * 
 * Puts the updater in a separate thread to avoid issues with
 * getting data from the serial port listener thread
 */

package car_data_logger;

public class Main {
	static Serial_Port sp = null;
	
	public static void main(String[] args) {
		sp = new Serial_Port();
		//launch(args);

		Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                    	//update_labels();
                    	try {
                    		//05: Coolant Temp
                    		//0C: Engine RPM
                    		//0D: Vehicle Speed
                    		//0F: Intake Air Temp
                    		//11: Throttle Position
                    		//2F: Fuel Level
                    		//5C: Oil Temp
                    		if(Serial_Port.started_polling == false)
                    			Serial_Port.started_polling = true;
                    		if(Serial_Port.ready_to_send)
                    			Serial_Port.sendStringToComm("01 11 05 0C 0D 0F 2F");
            			} catch (Exception e) {
            				e.printStackTrace();
            			}
                    }
                    
                    private void update_labels() {
                    	/*
                    	if(UI_Data_Store.oil_temp.equals(""))
                    	{
                    		UI_MonitorWindow.lbl_oil_temp_value.setTextFill(Color.web("#ff0000"));
                    		UI_MonitorWindow.lbl_oil_temp_value.setText("N/A");
                    	} else {
                    		UI_MonitorWindow.lbl_oil_temp_value.setTextFill(Color.web("#000000"));
                    		UI_MonitorWindow.lbl_oil_temp_value.setText(UI_Data_Store.oil_temp);
                    	}
                    	if(UI_Data_Store.coolant_temp.equals(""))
                    	{
                    		UI_MonitorWindow.lbl_coolant_temp_value.setTextFill(Color.web("#ff0000"));
                    		UI_MonitorWindow.lbl_coolant_temp_value.setText("N/A");
                    	} else {
                    		UI_MonitorWindow.lbl_coolant_temp_value.setTextFill(Color.web("#000000"));
                    		UI_MonitorWindow.lbl_coolant_temp_value.setText(UI_Data_Store.coolant_temp);
                    	}
                    	if(UI_Data_Store.speed.equals(""))
                    	{
                    		UI_MonitorWindow.lbl_speed_value.setTextFill(Color.web("#ff0000"));
                    		UI_MonitorWindow.lbl_speed_value.setText("N/A");
                    	} else {
                    		UI_MonitorWindow.lbl_speed_value.setTextFill(Color.web("#000000"));
                    		UI_MonitorWindow.lbl_speed_value.setText(UI_Data_Store.speed);
                    	}
                    	if(UI_Data_Store.throttle.equals(""))
                    	{
                    		UI_MonitorWindow.lbl_throttle_pos_value.setTextFill(Color.web("#ff0000"));
                    		UI_MonitorWindow.lbl_throttle_pos_value.setText("N/A");
                    	} else {
                    		UI_MonitorWindow.lbl_throttle_pos_value.setTextFill(Color.web("#000000"));
                    		UI_MonitorWindow.lbl_throttle_pos_value.setText(UI_Data_Store.throttle);
                    	}
                    	if(UI_Data_Store.fuel.equals(""))
                    	{
                    		UI_MonitorWindow.lbl_fuel_level_value.setTextFill(Color.web("#ff0000"));
                    		UI_MonitorWindow.lbl_fuel_level_value.setText("N/A");
                    	} else {
                    		UI_MonitorWindow.lbl_fuel_level_value.setTextFill(Color.web("#000000"));
                    		UI_MonitorWindow.lbl_fuel_level_value.setText(UI_Data_Store.fuel);
                    	}
        	        	
                    	if(UI_Data_Store.intake_temp.equals(""))
                    	{
                    		UI_MonitorWindow.lbl_intake_air_temp_value.setTextFill(Color.web("#ff0000"));
                    		UI_MonitorWindow.lbl_intake_air_temp_value.setText("N/A");
                    	} else {
                    		UI_MonitorWindow.lbl_intake_air_temp_value.setTextFill(Color.web("#000000"));
                    		UI_MonitorWindow.lbl_intake_air_temp_value.setText(UI_Data_Store.intake_temp);
                    	}
                    	if(UI_Data_Store.rpm.equals(""))
                    	{
                    		UI_MonitorWindow.lbl_rpm_value.setTextFill(Color.web("#ff0000"));
                    		UI_MonitorWindow.lbl_rpm_value.setText("N/A");
                    	} else {
                    		UI_MonitorWindow.lbl_rpm_value.setTextFill(Color.web("#000000"));
                    		UI_MonitorWindow.lbl_rpm_value.setText(UI_Data_Store.rpm);
                    	}
                    	*/
        	        	
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                    }
                    // UI update is run on the Application thread
                    //Platform.runLater(updater);
                }
            }

        });
        // don't let thread prevent JVM shutdown
		if(sp.init())
		{
			thread.setDaemon(true);
        	thread.start();
		}
	}
	
}
