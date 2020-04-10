package car_data_logger;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.*;

public class Serial_Port  {
	static int baud_rate = 38400;
	static String port = "COM3";
	//String port = "/dev/ttyUSB0";
	static SerialPort comPort;
	static int chosen_port = -1;

	public boolean init() {
		System.out.println("Querying Available Ports:");
		for(int i = 0; i < SerialPort.getCommPorts().length; i++)
		{
			System.out.println(i + ": " + SerialPort.getCommPorts()[i].getSystemPortName());
			if(SerialPort.getCommPorts()[i].getSystemPortName().equals(port))
				chosen_port = i;
		}
		if(chosen_port == -1)
		{
			AlertBox.display("No Port", "Port " + port + " was not found");
			System.out.println("Port Not Found....");
			return false;
		} else
		{
			comPort = SerialPort.getCommPorts()[chosen_port];
			System.out.println(comPort.getPortDescription());
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
					int numRead = comPort.readBytes(newData, newData.length);
					String s = null;
					try {
						s = new String(newData, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					System.out.println("--> " + s);
				}
			});

			try {
				sendStringToComm("ATZ");
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
			System.out.println("<-- " + command);
			String new_command = command + "\r";
			comPort.writeBytes(new_command.getBytes(), new_command.length());
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
