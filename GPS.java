package car_data_logger;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class GPS {
	static SerialPort comPort;
	static String port = "ttyUSB0";
	static int chosen_port = -1;
	static String OS = System.getProperty("os.name").toLowerCase();
	static String[] response_lines = {""};
	public void init() {

		if (OS.indexOf("nix") >= 0)
		{
			port = "ttyS0";
		}
		System.out.println("Attempting to start GPS with " + port + " selected");
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
			comPort.setBaudRate(9600);
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
						if(incoming.trim().endsWith("\n") || incoming.trim().endsWith("\r"))
						{
							UI_Data_Store.current_data=response_lines[0];
							System.out.println(response_lines[0]);
							response_lines[0] = "";
						} else {
							response_lines[0] += incoming.trim().replace("\r", "").replace(" ", "");
						}					
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

				}
			});

		}
	}
	
	private void interpret(String nmea_message)
	{
		String[] message = nmea_message.split(",");
		if(message[0].equals(new String("$GNRMC"))) 
		{
			//1: Time
			//2: Status (A=Valid / V=Invalid)
			//3: Latitude
			//4: N/S
			//5: Longitude
			//6: E/W
			//7: Speed (knots)
			//8: Heading
			//9: UTC Date
			//10: Magnetic variation
			//11: E/W Magnetic Variation
			//12: Mode  (A=Autonomous / N=Not Valid) * Checksum 
			String[] mode_checksum = message[12].split("*");
			if(message[2].matches("A") && mode_checksum[0].matches("A"))
			{
				get_lat_long(message[3],message[4],message[5],message[6]);
			}
		} else if(message[0].equals(new String("$GNGGA"))) 
		{
			//1: Time
			//2: Latitude
			//3: N/S
			//4: Longitude
			//5: E/W
			//6: Fix Valid Indicator
			//	 1: GPS
			//	 2: DGPS
			//	 3: PPS
			//	 4: Real Time Kinematic
			//	 5: Float RTK
			//	 6: Estimated
			//	 7: Manual Input
			//	 8: Simulation
			//7: Number of Satelites Used (0-12)
			//8: Horizontal Dilution Of Precision
			//9: Altitude
			//10: Letter M, for some reason
			//11: Something Scientific
			//12: Another Letter M?
			//13: Blank
			//14: Checksum
			if(message[6].matches("1")) 
			{
				get_lat_long(message[2],message[3],message[4],message[5]);
			}
		} else if(message[0].equals(new String("$GNGSA"))) 
		{
			//Satellite list message.  Not implemented yet.
		}
	}
	
	private void get_lat_long(String latitude, String lat_dir, String longitude, String long_dir)
	{
		
	}	
}
