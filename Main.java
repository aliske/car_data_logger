/*
 * Aaron Liske
 * Car Data Logger
 * Latest Revision: 4/11/2020
 * 
 * Puts the updater in a separate thread to avoid issues with
 * getting data from the serial port listener thread
 */

package car_data_logger;

import java.awt.Color;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;

public class Main {
	static Serial_Port sp = null;
	static boolean started = false;
	static int poll_speed = 25;
	static Mysql_Connector mysql = new Mysql_Connector();
	public static void main(String[] args) {
		sp = new Serial_Port();
		//launch(args);
		
		UI_Screen_Main.show();
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("nix") >= 0)
		{
			GpioController gpio = GpioFactory.getInstance();
			GpioPinDigitalInput inputPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01);
			UI_Screen_Main.gpio_label.setVisible(true);
		} else if(OS.indexOf("win") >= 0) {
			UI_Screen_Main.gpio_label.setVisible(false);
		}
		
		Config_File config = new Config_File();
		config.attempt_read();
		UI_Screen_Main.selected_port_label.setText("Currently Using Port: " + sp.port);
		Timer timer = new Timer(3,500,UI_Screen_Main.timer_label);
		timer.set_started();
		
		UI_Screen_Main.port_list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				AlertBox.display("New Port Chosen", UI_Screen_Main.port_list.getSelectedValue() + " Chosen as the Current Port");
				Serial_Port.port = UI_Screen_Main.port_list.getSelectedValue();
				Serial_Port.selected = true;
				UI_Screen_Main.selected_port_label.setText("Currently Using Port: " + sp.port);
				timer.reset(3);
				started = false;
			}
		});

		Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {                
            	while(true)
            	{
            		if(started == true) {
        				//System.out.println("started");
                		//05: Coolant Temp
                		//0C: Engine RPM
                		//0D: Vehicle Speed
                		//0F: Intake Air Temp
                		//45: Throttle Position
                		//2F: Fuel Level
                		//5C: Oil Temp
            			
                		if(Serial_Port.started_polling == false)
                			Serial_Port.started_polling = true;
                		if(Serial_Port.ready_to_send)
                		{
							try {
									Serial_Port.sendStringToComm("01 45 05 0C 0D 0F 2F");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                		}
        			}
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
                				started = true;
                				if(UI_Screen_Monitor.displayed == false)
                					UI_Screen_Monitor.show();
                				initialize();
                				
                				System.out.println("Past init");
                				if(mysql.connect())
                				{
                					System.out.println("MySQL Connected");
                					Config_File config = new Config_File();
                					config.attempt_write(UI_Screen_Main.database_host_text.getText(), UI_Screen_Main.database_name_text.getText(), UI_Screen_Main.database_username_text.getText(), UI_Screen_Main.database_password_text.getText());
                				}
                				else
                					System.out.println("MySQL Failed to Connect");
                				
                			}
                			
                		}
                		else
                		{
                			//System.out.println(String.valueOf(timer.end_time));
                		}
                		Thread.sleep(poll_speed);
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
                }
            }
            private void initialize() {
            	sp.init();
            }
                        
        });
		
		
		Thread thread_interpretter = new Thread(new Runnable() {
			Interpreter interpreter = new Interpreter();
            @Override
            public void run() {
            	while(true)
            	{
            		try {
            			if(UI_Data_Store.current_data.trim().length() > 0)
            			{
            				System.out.println("Current Data: " + UI_Data_Store.current_data);
            				update_labels();
            			}
            			if(UI_Data_Store.current_data != "")
            			{
            				interpreter.input_string(UI_Data_Store.current_data);
            			}
						Thread.sleep(poll_speed);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
            private void update_labels() {
            	if(UI_Data_Store.coolant_temp.equals(""))
            	{
            		UI_Screen_Monitor.coolant_value_label.setForeground(Color.red);
            		UI_Screen_Monitor.coolant_value_label.setText("N/A");
            	} else {
            		UI_Screen_Monitor.coolant_value_label.setForeground(Color.white);
            		UI_Screen_Monitor.coolant_value_label.setText(UI_Data_Store.coolant_temp);
            	}
            	if(UI_Data_Store.speed.equals(""))
            	{
            		UI_Screen_Monitor.speed_value_label.setForeground(Color.red);
            		UI_Screen_Monitor.speed_value_label.setText("N/A");
            	} else {
            		UI_Screen_Monitor.speed_value_label.setForeground(Color.white);
            		UI_Screen_Monitor.speed_value_label.setText(UI_Data_Store.speed + " kph");
            		float miles = (float) (Float.parseFloat(UI_Data_Store.speed) / 1.62);
            		UI_Screen_Monitor.speed_mph_label.setText(Math.round(miles) + " mph");
            	}
            	if(UI_Data_Store.throttle.equals(""))
            	{
            		UI_Screen_Monitor.throttle_value_label.setForeground(Color.red);
            		UI_Screen_Monitor.throttle_value_label.setText("N/A");
            	} else {
            		UI_Screen_Monitor.throttle_value_label.setForeground(Color.white);
            		UI_Screen_Monitor.throttle_value_label.setText(UI_Data_Store.throttle);
            	}
            	if(UI_Data_Store.fuel.equals(""))
            	{
            		UI_Screen_Monitor.fuel_value_label.setForeground(Color.red);
            		UI_Screen_Monitor.fuel_value_label.setText("N/A");
            	} else {
            		UI_Screen_Monitor.fuel_value_label.setForeground(Color.white);
            		UI_Screen_Monitor.fuel_value_label.setText(UI_Data_Store.fuel);
            	}
	        	
            	if(UI_Data_Store.intake_temp.equals(""))
            	{
            		UI_Screen_Monitor.intake_value_label.setForeground(Color.red);
            		UI_Screen_Monitor.intake_value_label.setText("N/A");
            	} else {
            		UI_Screen_Monitor.intake_value_label.setForeground(Color.white);
            		UI_Screen_Monitor.intake_value_label.setText(UI_Data_Store.intake_temp);
            	}
            	if(UI_Data_Store.rpm.equals(""))
            	{
            		UI_Screen_Monitor.rpm_value_label.setForeground(Color.red);
            		UI_Screen_Monitor.rpm_value_label.setText("N/A");
            	} else {
            		UI_Screen_Monitor.rpm_value_label.setForeground(Color.white);
            		UI_Screen_Monitor.rpm_value_label.setText(UI_Data_Store.rpm);
            	}
        }
		});
		thread_interpretter.start();
		if(sp.query_ports())
		{
			System.out.println("Done Querying");
			thread.setDaemon(true);
	        thread.start();
		}
	}
}
