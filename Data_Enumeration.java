/*
 * Aaron Liske
 * Car Data Logger
 * Enumeration class, with the data lookup values for the PIDs
 */

package car_data_logger;

public class Data_Enumeration {
	
	static String[][] service1_pids = {
			{"00", "PIDs Supported [01-20]", "4"},
			{"01", "Monitor Status", "4"},
			{"02", "Freeze DTC", "2"},
			{"03", "Fuel System Status", "2"},
			{"04", "Caluclated Engine Load", "1"},
			{"05", "Engine Coolant Temp", "1"},
			{"06", "Short Term Fuel Trim - B1", "1"},
			{"07", "Long Term Fuel Trim - B1", "1"},
			{"08", "Short Term Fuel Trim - B2", "1"},
			{"09", "Long Term Fuel Trim - B2", "1"},
			{"0A", "Fuel Pressure", "1"},
			{"0B", "Intake MAP", "1"},
			{"0C", "Engine RPM", "2"},
			{"0D", "Vehicle Speed", "1"},
			{"0E", "Timing Advance", "1"},
			{"0F", "Intake Air Temp", "1"},
			{"10", "MAF Air Flow Rate", "2"},
			{"11", "Throttle Position", "1"},
			{"12", "Commanded Secondary Air", "1"},
			{"13", "O2 Sensors Present - 2 Banks", "1"},
			{"14", "O2 Sensor 1", "2"},
			{"15", "O2 Sensor 2", "2"},
			{"16", "O2 Sensor 3", "2"},
			{"17", "O2 Sensor 4", "2"},
			{"18", "O2 Sensor 5", "2"},
			{"19", "O2 Sensor 6", "2"},
			{"1A", "O2 Sensor 7", "2"},
			{"1B", "O2 Sensor 8", "2"},
			{"1C", "OBD Standard", "1"},
			{"1D", "O2 Sensors Present - 4 Banks", "1"},
			{"1E", "Auxiliary Input Status", "1"},
			{"1F", "Run Time Since Engine Start", "2"},
			{"20", "PIDs Supported [21-40]", "4"},
			{"21", "Distance Traveled with MIL on", "2"},
			{"22", "Fuel Rail Pressure", "2"},
			{"23", "Fuel Rail Gauge Pressure", "2"},
			{"24", "O2 Sensor 1", "4"},
			{"25", "O2 Sensor 2", "4"},
			{"26", "O2 Sensor 3", "4"},
			{"27", "O2 Sensor 4", "4"},
			{"28", "O2 Sensor 5", "4"},
			{"29", "O2 Sensor 6", "4"},
			{"2A", "O2 Sensor 7", "4"},
			{"2B", "O2 Sensor 8", "4"},
			{"2C", "Commanded EGR", "1"},
			{"2D", "EGR Error", "1"},
			{"2E", "Commanded Evaporative Purge", "1"},
			{"2F", "Fuel Tank Level Input", "1"},
			{"30", "Warm Ups since Codes Cleared", "1"},
			{"31", "Distance Traveled since Codes Cleared", "1"},
			{"32", "Evap System Vapor Pressure", "2"},
			{"33", "Absolute Barometric Pressure", "2"},
			{"34", "O2 Sensor 1", "4"},
			{"35", "O2 Sensor 2", "4"},
			{"36", "O2 Sensor 3", "4"},
			{"37", "O2 Sensor 4", "4"},
			{"38", "O2 Sensor 5", "4"},
			{"39", "O2 Sensor 6", "4"},
			{"3A", "O2 Sensor 7", "4"},
			{"3B", "O2 Sensor 8", "4"},
			{"3C", "Catalyst Temp - B1 S1", "2"},
	};
	
	static String[] pid_names_1 = {"PIDs Supported","Monitor Status","Freeze DTC","Fuel System Status",
			"Engine Load","Coolant Temp","Short Term Fuel Bank Trim - Bank 1","Long Term Fuel Bank Trim - Bank 2",
			"Short Term Fuel Bank Trim - Bank 2","Long Term Fuel Bank Trim - Bank 2",
			"Fuel Pressure","Intake Manifold Pressure","Engine RPM","Vehicle Speed",
			"Timing Advance","Intake Air Temp","MAF Air Flow Rate","Throttle Position"};

	static int[] bytes_returned_1 = { //SERVICE 1 PIDs BYTES RETURNED
		1,4,2,2,1,1,1,1,1,1,1,1,2,1,1,1,2,1,1,1,2,2,2,2,2,2,2,2,1,1,1,2,4,2,2,2,4,4,
		4,4,4,4,4,4,1,1,1,1,1,2,2,1,4,4,4,4,4,4,4,4,2,2,2,2,4,4,2,2,2,1,1,1,1,1,1,1,
		1,2,2,4,4,1,1,2,2,2,2,2,2,2,1,1,1,2,2,1,4,1,1,2,5,2,5,3,7,7,5,5,5,6,5,3,9,5,
		5,5,5,7,7,5,9,9,7,7,9,1,1,13,4,21,21,5,1,10,5,5,13,41,41,7,16,1,1,5,3,5,2,3,
		12,9,9,6,4,17,4,2,9,4,9,2,9,4,4,4,4
	};
	
	static int[] bytes_returned_5 = { //SERVICE 5 PIDs BYTES RETURNED
		4,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2
	};
	
	static int[] bytes_returned_9 = { //SERVICE 9 PIDs BYTES RETURNED
		4,1,17,1,0,1,0,1,4,1,20,4	
	};
}
