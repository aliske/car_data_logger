/*
 * Aaron Liske
 * Car Data Logger
 * Serial Port Class for ELM327 processor
 * ELM327 data sheet for reference:
 * https://www.elmelectronics.com/wp-content/uploads/2016/07/ELM327DS.pdf
 */

package car_data_logger;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.*;
import java.sql.Timestamp;

public class Serial_Port  {
	static int baud_rate = 38400;
	static String port = "ttyUSB0";
	static Boolean selected = false;
	static String last_received = "";
	static String last_sent = "";
	static SerialPort comPort;
	static int chosen_port = -1;
	static int count_responses = 0;
	static String[] response_lines = {"","","","","","","","","","","",""};
	static Boolean ready_to_send = false;
	static Boolean started_polling = false;
	static Interpreter interpreter = new Interpreter();
	static String OS = System.getProperty("os.name").toLowerCase();
	static int starting_stage = 0;

	public boolean query_ports()
	{
		UI_Screen_Main.l1.clear();
		for(int i = 0; i < SerialPort.getCommPorts().length; i++)
		{
			System.out.println(i + ": " + SerialPort.getCommPorts()[i].getSystemPortName());
			UI_Screen_Main.l1.add(i, SerialPort.getCommPorts()[i].getSystemPortName());
			if(SerialPort.getCommPorts()[i].getSystemPortName().equals(port))
			{
				chosen_port = i;
			}
		}
		return true;
	}
	
	public void init() {
		
		if(OS.indexOf("win") >= 0 && selected == false)
		{
			port = "COM3";
		} else if (OS.indexOf("nix") >= 0 && selected == false)
		{
			port = "ttyUSB0";
		}
		System.out.println("Attempting to start with " + port + " selected");
		for(int i = 0; i < SerialPort.getCommPorts().length; i++)
		{
			if(SerialPort.getCommPorts()[i].getSystemPortName().equals(port))
			{
				chosen_port = i;
			}
		}
		if(chosen_port == -1)
		{
			AlertBox.display("No Port", "Port " + port + " was not found");
			System.out.println("Port Not Found....");
		} else
		{
			comPort = SerialPort.getCommPorts()[chosen_port];
			//UI_StartWindow.txt_port_used.setText(port + " (" + comPort.getPortDescription() + ")");
			comPort.setBaudRate(baud_rate);
			comPort.setNumStopBits(1);
			comPort.setNumDataBits(8);
			comPort.openPort();

			comPort.addDataListener(new SerialPortDataListener() {
				@Override
				public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }

				@Override
				public void serialEvent(SerialPortEvent event) {
					if(event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
						return;
					byte[] newData = new byte[comPort.bytesAvailable()];
					
					int bytes_read = comPort.readBytes(newData, newData.length);
					
					try {
						String incoming = new String(newData, "UTF-8");
						//System.out.println(incoming);
						if(incoming.trim().endsWith(">"))
						{
							//System.out.println("> Received");
							if(starting_stage == 0)
								starting_stage = 1;
							else if(starting_stage == 1)
								starting_stage = 2;
							else if(starting_stage == 2)
								starting_stage = 3;
							if(starting_stage == 3)
								ready_to_send = true;
							//System.out.println("Ready to Send: " + ready_to_send + " (" + starting_stage + ")");
						}
						
						if(!ready_to_send)
						{
							response_lines[0] += incoming.trim().replace("\r", "").replace(" ", "");
						}
						else
						{
							System.out.println(response_lines[0]);
							UI_Data_Store.current_data=response_lines[0];
							UI_Data_Store.timestamp = new Timestamp(System.currentTimeMillis());
							response_lines[0] = "";
						}
						/*
						if(incoming.indexOf("\n") >=0 || incoming.indexOf("\r") >= 0 && started_polling)
						{
							response_lines[count_responses] = response_lines[count_responses] + incoming.trim();
							count_responses++;
						} else
						{
							response_lines[count_responses] = incoming.trim();
						}
						if(ready_to_send)
						{
							count_responses = 0;
							String final_response = "";
							System.out.println("Response Lines: " + response_lines.length);
							for(int i= 0; i<response_lines.length; i++)
								final_response += response_lines[i].trim();
									//response_lines[0].trim() + " " + response_lines[1].trim() + " " + response_lines[2].trim() + " " + response_lines[3].trim() + " " + response_lines[4].trim();
							final_response = final_response.replace("\r", " ");
							final_response = final_response.replace(" ", "");
							final_response = final_response.replace(">", "");
							
							System.out.println(final_response);
							//interpreter.input_string(final_response);
							UI_Data_Store.timestamp = new Timestamp(System.currentTimeMillis());
						}
						*/
						//}
						//else
							//System.out.println("Bad Data: " + incoming);
						
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					
				}
			});
			serial_stage();

		}
	}
	
	private void serial_stage() {
		Thread thread = new Thread(new Runnable() {
	        @Override
	        public void run() {
	            while (true && (starting_stage==0 || starting_stage==1 || starting_stage==2)) {
	                try {
	                	try {
	                		
	                		if(starting_stage == 0)
	                			sendStringToComm("AT Z");
	                		else if(starting_stage == 1)
	                			sendStringToComm("AT E0");
	                		else if(starting_stage == 2)
	                			sendStringToComm("0100");
	        			} catch (Exception e) {
	        				e.printStackTrace();
	        			}
	                	Thread.sleep(1000);
	                } catch (InterruptedException ex) {
	                }
	            }
	        }

	    });
		//thread.setDaemon(true);
		thread.start();
	}

	static void sendStringToComm(String command) throws Exception {
		if(chosen_port == -1)
		{
			System.out.println("Cannot send command \"" + command + "\".  No Port Found....");
			//AlertBox.display("No Port", "Cannot send command \"" + command + "\".  No Port Found....");
		} else
		{
			//Send only if ready OR if reset command
			if(ready_to_send || command == "AT Z" || command == "AT E0" || command == "0100")
			{
				ready_to_send = false;
				String new_command = command + "\r";
				last_sent = command;
				count_responses=0;
				System.out.println("Sent [" + port + "]: " + new_command);
				comPort.writeBytes(new_command.getBytes(), new_command.length());
			}
		}
	}


	@SuppressWarnings("static-access")
	public void set_port(String port)
	{
		this.port = port;
	}

	public String get_port()
	{
		return port;
	}

	public void set_baud_rate(int rate)
	{
		baud_rate = rate;
	}

	public int get_baud_rate()
	{
		return baud_rate;
	}

}
