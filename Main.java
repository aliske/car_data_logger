/*
 * Aaron Liske
 * Car Data Logger
 * Latest Revision: 4/11/2020
 * 
 * Puts the updater in a separate thread to avoid issues with
 * getting data from the serial port listener thread
 */

package car_data_logger;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Main {
	static Serial_Port sp = null;
	static boolean started = false;
	static Mysql_Connector mysql = new Mysql_Connector();
	public static void main(String[] args) {
		sp = new Serial_Port();
		//launch(args);
		
		UI_Screen_Main.show();
		Config_File config = new Config_File();
		config.attempt_read();
		UI_Screen_Main.selected_port_label.setText("Currently Using Port: " + sp.port);
		Timer timer = new Timer(10,500,UI_Screen_Main.timer_label);
		timer.set_started();
		
		UI_Screen_Main.port_list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				AlertBox.display("New Port Chosen", UI_Screen_Main.port_list.getSelectedValue() + " Chosen as the Current Port");
				Serial_Port.port = UI_Screen_Main.port_list.getSelectedValue();
				Serial_Port.selected = true;
				UI_Screen_Main.selected_port_label.setText("Currently Using Port: " + sp.port);
				timer.reset(10);
				started = false;
			}
		});

		Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                    	
                    	update_labels();
                    	while(true)
                    	{
                    		System.out.println(String.valueOf(timer.end_time));
	                    	try {
	                    		if(timer.end_time < 0)
	                    		{
	                    			if(started == false)
	                    			{
	                    				UI_Screen_Main.port_list.setEnabled(false);
	                    				UI_Screen_Main.database_host_text.setEnabled(false);
	                    				UI_Screen_Main.database_name_text.setEnabled(false);
	                    				UI_Screen_Main.database_username_text.setEnabled(false);
	                    				UI_Screen_Main.database_password_text.setEnabled(false);
	                    				if(sp.init()) {
		                    				if(mysql.connect())
		                    				{
		                    					System.out.println("MySQL Connected");
		                    					Config_File config = new Config_File();
		                    					config.attempt_write(UI_Screen_Main.database_host_text.getText(), UI_Screen_Main.database_name_text.getText(), UI_Screen_Main.database_username_text.getText(), UI_Screen_Main.database_password_text.getText());
		                    				}
		                    				else
		                    					System.out.println("MySQL Failed to Connect");
		                    				started = true;
	                    				}
	                    			}
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
	                    		}
	                    		else
	                    		{
	                    			//System.out.println(String.valueOf(timer.end_time));
	                    		}
	                    		Thread.sleep(200);
	            			} catch (Exception e) {
	            				e.printStackTrace();
	            			}
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
                }
                */
		            
               }
        });
		if(sp.query())
		{
			System.out.println("Done Querying");
			thread.setDaemon(true);
	        thread.start();
		}
	}
}
