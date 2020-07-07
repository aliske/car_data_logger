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
	static String[] response_lines = {""};
	static Boolean ready_to_send = false;
	static Boolean started_polling = false;
	static Interpreter interpreter = new Interpreter();
	static String OS = System.getProperty("os.name").toLowerCase();
	static int obdii_setup_stage = 0;

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
			comPort.setBaudRate(baud_rate);
			comPort.setNumStopBits(1);
			comPort.setNumDataBits(8);
			comPort.openPort();
			obdii_setup();
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
						if(incoming.trim().endsWith(">"))
						{
							if(obdii_setup_stage == 0)
								obdii_setup_stage = 1;
							else if(obdii_setup_stage == 1)
								obdii_setup_stage = 2;
							else if(obdii_setup_stage == 2)
								obdii_setup_stage = 3;
							if(obdii_setup_stage == 3)
								ready_to_send = true;
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
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					
				}
			});
			
			
		}
	}
	
	private void obdii_setup() {
		Thread thread = new Thread(new Runnable() {
	        @Override
	        public void run() {
	            while (true && (obdii_setup_stage==0 || obdii_setup_stage==1 || obdii_setup_stage==2)) {
	                try {
	                	try {
	                		if(obdii_setup_stage == 0)
	                			sendStringToComm("AT Z");
	                		else if(obdii_setup_stage == 1)
	                			sendStringToComm("AT E0");
	                		else if(obdii_setup_stage == 2)
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
		} else
		{
			//Send only if ready OR if one of the setup commands
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
