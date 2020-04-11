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

public class Serial_Port  {
	static int baud_rate = 38400;
	static String port = "ttyUSB0";
	static String last_received = "";
	static String last_sent = "";
	static SerialPort comPort;
	static int chosen_port = -1;
	static Boolean ready_to_send = false;
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
			if(SerialPort.getCommPorts()[i].getSystemPortName().equals(port))
			{
				chosen_port = i;
			}
		}
		if(chosen_port == -1)
		{
			UI_StartWindow.txt_port_used.setText("[PORT NOT FOUND]");
			AlertBox.display("No Port", "Port " + port + " was not found");
			System.out.println("Port Not Found....");
			return false;
		} else
		{
			comPort = SerialPort.getCommPorts()[chosen_port];
			UI_StartWindow.txt_port_used.setText(port + " (" + comPort.getPortDescription() + ")");
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
						if(incoming.trim().equals(">"))
						{
							ready_to_send = true;
						}
						if((!incoming.trim().equals(">")) && (incoming.endsWith("\n") || incoming.endsWith("\r")))
						{
								s = incoming;
								System.out.println("Received: " + s.trim());
								interpreter.input_string(s.trim());
						}
						if(incoming.trim() == last_sent.trim())
						{
							try {
								sendStringToComm("AT E0");
							} catch (Exception e) {
								e.printStackTrace();
							}
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
			AlertBox.display("No Port", "Cannot send command \"" + command + "\".  No Port Found....");
		} else
		{
			//Send only if ready OR if reset command
			if(ready_to_send || command == "AT Z")
			{
				ready_to_send = false;
				String new_command = command + "\r";
				last_sent = command;
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
