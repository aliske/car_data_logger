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
	static String last_received = "";
	static String last_sent = "";
	static SerialPort comPort;
	static int chosen_port = -1;
	static int count_responses = 0;
	static String[] response_lines = {"", "", "", "", ""};
	static Boolean ready_to_send = false;
	static Boolean started_polling = false;
	static Interpreter interpreter = new Interpreter();
	static String OS = System.getProperty("os.name").toLowerCase();

	public boolean init() {
		System.out.println("Querying Available Ports:");
		if(OS.indexOf("win") >= 0)
		{
			port = "COM3";
		} else if (OS.indexOf("nix") >= 0)
		{
			port = "ttyUSB0";
		}
		for(int i = 0; i < SerialPort.getCommPorts().length; i++)
		{
			System.out.println(i + ": " + SerialPort.getCommPorts()[i].getSystemPortName());
			UI_Screen_Main.l1.add(i, SerialPort.getCommPorts()[i].getSystemPortName() + " : " + SerialPort.getCommPorts()[i].getPortDescription());
			if(SerialPort.getCommPorts()[i].getSystemPortName().equals(port))
			{
				chosen_port = i;
			}
		}
		if(chosen_port == -1)
		{
			//UI_StartWindow.txt_port_used.setText("[PORT NOT FOUND]");
			AlertBox.display("No Port", "Port " + port + " was not found");
			System.out.println("Port Not Found....");
			return false;
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
					
					String s = "";
					try {
						String incoming = new String(newData, "UTF-8");
						//System.out.print(incoming);
						if(incoming.trim().endsWith(">"))
						{
							ready_to_send = true;
						}
						if(incoming.indexOf("\n") >=0 || incoming.indexOf("\r") >= 0 && started_polling)
						{
							response_lines[count_responses] = response_lines[count_responses] + incoming.trim();
							s = incoming;
							count_responses++;
						} else
						{
							response_lines[count_responses] = incoming.trim();
						}
						if(count_responses == 5)
						{
							count_responses = 0;
							String final_response = response_lines[0].trim() + " " + response_lines[1].trim() + " " + response_lines[2].trim() + " " + response_lines[3].trim() + " " + response_lines[4].trim();
							final_response = final_response.replace("\r", " ");
							final_response = final_response.replace(" ", "");
							
							//System.out.println(final_response);
							interpreter.input_string(final_response);
							UI_Data_Store.timestamp = new Timestamp(System.currentTimeMillis());
						}
						
						//}
						//else
							//System.out.println("Bad Data: " + incoming);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					
				}
			});
			try {
				sendStringToComm("AT Z");
				Thread.sleep(1);
				sendStringToComm("AT E0");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	static void sendStringToComm(String command) throws Exception {
		if(chosen_port == -1)
		{
			System.out.println("Cannot send command \"" + command + "\".  No Port Found....");
			//AlertBox.display("No Port", "Cannot send command \"" + command + "\".  No Port Found....");
		} else
		{
			//Send only if ready OR if reset command
			if(ready_to_send || command == "AT Z")
			{
				ready_to_send = false;
				String new_command = command + "\r";
				last_sent = command;
				response_lines[0] = "";
				response_lines[1] = "";
				response_lines[2] = "";
				response_lines[3] = "";
				response_lines[4] = "";
				count_responses=0;
				System.out.println("Sent: " + command);
				comPort.writeBytes(new_command.getBytes(), new_command.length());
			}
		}
	}


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
