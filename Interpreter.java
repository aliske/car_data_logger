package car_data_logger;

public class Interpreter {
	
	
	
	Interpreter()
	{
		
	}
	
	public double standard_processor(String A, String B, String C, String D, int service, String pid)
	{
		int valueA = -1;
		int valueB = -1;
		int valueC = -1;
		int valueD = -1;
		double result = -1;
		if(! A.isEmpty())
		{
			valueA = Integer.parseInt(A, 16);
		}
		if(! B.isEmpty())
		{
			valueB = Integer.parseInt(B, 16);
		}
		if(! C.isEmpty())
		{
			valueC = Integer.parseInt(C, 16);
		}
		if(! D.isEmpty())
		{
			valueD = Integer.parseInt(D, 16);
		}
		if(service == 1 || service == 2) 		//basic PIDs (1) and saved PIDs (2)
		{
			if(pid.equals("04")) 				//Engine Load
				result = (double) valueA / 2.55;
			else if(pid.equals("05")) 			//Coolant Temp
				result = (double) valueA - 40;
			else if(pid.equals("0A")) 			// Fuel Pressure
				result = (double) valueA * 3;
			else if(pid.equals("0B"))			//Intake Manifold Pressure
				result = (double) valueA;
			else if(pid.equals("0C")) 			//Engine RPM
				result = (((double) valueA * 256) + (double) valueB) / 4;
			else if(pid.equals("0D")) 			//Vehicle Speed
				result = (double) valueA;
			else if(pid.equals("0F")) 			//Intake Air Temperature
				result = (double) valueA - 40;
			else if(pid.equals("11")) 			//Throttle Position
				result = (double) valueA / 2.55;
			else if(pid.equals("5C")) 			//Engine Oil Temp
				result = (double) valueA - 40;
			else if(pid.equals("5E")) 			//Engine Fuel Rate
				result = (((double) valueA * 256) + (double) valueB) / 20;
			else
				return -1;
		} else if (service == 5) 				//O2 Sensor Banks
		{
			if(pid.equals("0101") || pid.equals("0102") || pid.equals("0103") || pid.equals("0104") || pid.equals("0105") || pid.equals("0106") || pid.equals("0107") || pid.equals("0108") || pid.equals("0109") || pid.equals("010A") || pid.equals("010B") || pid.equals("010C") || pid.equals("010D") || pid.equals("010E") || pid.equals("010F") || pid.equals("0110") || pid.equals("0201") || pid.equals("0202") || pid.equals("0203") || pid.equals("0204") || pid.equals("0205") || pid.equals("0206") || pid.equals("0207") || pid.equals("0208") || pid.equals("0209") || pid.equals("020A") || pid.equals("020B") || pid.equals("020C") || pid.equals("020D") || pid.equals("020E") || pid.equals("020F") || pid.equals("0210"))
			{
				valueA = Integer.parseInt(A + B, 16);
				result = (valueA / 65535) * 1.275;
			}
			else
				return -1;
		} else if (service == 9) 				//vehicle and ECU information
		{
			
		}
		return result;
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
