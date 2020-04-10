package car_data_logger;

import java.text.DecimalFormat;

public class Interpreter {
	DecimalFormat df = new DecimalFormat("###.##");
	static Data_Enumeration enumerator = new Data_Enumeration();
	Interpreter()
	{
		
	}
	
	public void input_string(String data) {
		int service = -1;
		int pid = -1;
		String pid_hex = "00";
		String[] pid_parts = {"00","00","00","00","00"};
		int parts_count = 0;
		String[] parts = data.split(" ");
		if(parts[0].startsWith("4")) {
			service = Integer.parseInt(parts[0]) - 40;
			int counter = 0;
			for(int i = 1; i < parts.length; i++)
			{
				if(counter == 0)
				{
					pid_hex = parts[i];
					//System.out.println("PID Hex: " + pid_hex);
					pid = Integer.parseInt(parts[i], 16);
					//System.out.println("PID: " + pid);
					pid_parts[0]="00";
					pid_parts[1]="00";
					pid_parts[2]="00";
					pid_parts[3]="00";
					pid_parts[4]="00";
					
					if(service == 1)
					{
						counter = Data_Enumeration.bytes_returned_1[pid];
					}
					else if(service == 5)
					{
						counter = Data_Enumeration.bytes_returned_5[pid];
					}
					else if(service == 9)
					{
						counter = Data_Enumeration.bytes_returned_9[pid];
					}
					
					parts_count = counter;
				} else if (counter == 1)
				{
					pid_parts[parts_count - counter] = parts[i];
					standard_processor(pid_parts[0], pid_parts[1], pid_parts[2], pid_parts[3], pid_parts[4], service, pid_hex);
					counter--;
				} else {
					pid_parts[parts_count - counter] = parts[i];
					counter--;
				}
			}
		}
	}
	
	public void standard_processor(String A, String B, String C, String D, String E, int service, String pid)
	{
		//System.out.println(A + " " + B + " " + C + " " + D + " " + E);
		int valueA = -1;
		int valueB = -1;
		int valueC = -1;
		int valueD = -1;
		int valueE = -1;
		double result = -1;
		if(! A.isEmpty())
		{
			try {
				valueA = Integer.parseInt(A, 16);
			} catch (NumberFormatException e)
			{
				System.out.println("INVALID VALUE");
			}
		}
		if(! B.isEmpty())
		{
			try {
				valueB = Integer.parseInt(B, 16);
			} catch (NumberFormatException e)
			{
				System.out.println("INVALID VALUE");
			}
		}
		if(! C.isEmpty())
		{
			try {
				valueC = Integer.parseInt(C, 16);
			} catch (NumberFormatException e)
			{
				System.out.println("INVALID VALUE");
			}
		}
		if(! D.isEmpty())
		{
			try {
				valueD = Integer.parseInt(D, 16);
			} catch (NumberFormatException e)
			{
				System.out.println("INVALID VALUE");
			}
		}
		if(! E.isEmpty())
		{
			try {
				valueE = Integer.parseInt(E, 16);
			} catch (NumberFormatException e)
			{
				System.out.println("INVALID VALUE");
			}
		}
		if(service == 1 || service == 2) 		//basic PIDs (1) and saved PIDs (2)
		{
			if(pid.equals("04")) 				//Engine Load
				result = (double) valueA / 2.55;
			else if(pid.equals("05")) 			//Coolant Temp
			{
				result = (double) valueA - 40;
				UI_MonitorWindow.lbl_coolant_temp_value.setText(df.format(result) + " C");
			}
			else if(pid.equals("0A")) 			// Fuel Pressure
				result = (double) valueA * 3;
			else if(pid.equals("0B"))			//Intake Manifold Pressure
				result = (double) valueA;
			else if(pid.equals("0C")) 			//Engine RPM
			{
				result = (((double) valueA * 256) + (double) valueB) / 4;
				UI_MonitorWindow.lbl_rpm_value.setText(df.format(result) + "");
			}
			else if(pid.equals("0D")) 			//Vehicle Speed
			{
				result = (double) valueA;
				UI_MonitorWindow.lbl_speed_value.setText(df.format(result) + " kph");
			}
			else if(pid.equals("0F")) 			//Intake Air Temperature
			{
				result = (double) valueA - 40;
				UI_MonitorWindow.lbl_intake_air_temp_value.setText(df.format(result) + " C");
			}
			else if(pid.equals("11")) 			//Throttle Position
			{
				result = (double) valueA / 2.55;
				UI_MonitorWindow.lbl_throttle_pos_value.setText(df.format(result) + "%");
			}
			else if(pid.equals("2F")) 			//Fuel Level
			{
				result = (double) valueA / 2.55;
				UI_MonitorWindow.lbl_fuel_level_value.setText(df.format(result) + "%");
			}
			else if(pid.equals("5C")) 			//Engine Oil Temp
				result = (double) valueA - 40;
			else if(pid.equals("5E")) 			//Engine Fuel Rate
				result = (((double) valueA * 256) + (double) valueB) / 20;

		} else if (service == 5) 				//O2 Sensor Banks
		{
			if(pid.equals("0101") || pid.equals("0102") || pid.equals("0103") || pid.equals("0104") || pid.equals("0105") || pid.equals("0106") || pid.equals("0107") || pid.equals("0108") || pid.equals("0109") || pid.equals("010A") || pid.equals("010B") || pid.equals("010C") || pid.equals("010D") || pid.equals("010E") || pid.equals("010F") || pid.equals("0110") || pid.equals("0201") || pid.equals("0202") || pid.equals("0203") || pid.equals("0204") || pid.equals("0205") || pid.equals("0206") || pid.equals("0207") || pid.equals("0208") || pid.equals("0209") || pid.equals("020A") || pid.equals("020B") || pid.equals("020C") || pid.equals("020D") || pid.equals("020E") || pid.equals("020F") || pid.equals("0210"))
			{
				valueA = Integer.parseInt(A + B, 16);
				result = (valueA / 65535) * 1.275;
			}
		} else if (service == 9) 				//vehicle and ECU information
		{
			
		}
		System.out.println(Data_Enumeration.pid_names_1[Integer.parseInt(pid, 16)] + " : " + df.format(result));
	}
	
	public int[] bit_encoded_processor(String A, String B, String C, String D, int service, int pid)
	{
		int[] values = {0};
		if (service == 3) //return trouble codes
		{
			
		}
		return values;
	}

}
