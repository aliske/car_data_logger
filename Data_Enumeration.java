package car_data_logger;

public class Data_Enumeration {
	
	static String[] pid_names_1 = {"","","","","Engine Load","Coolant Temp","","","","",
			"Fuel Pressure","Intake Manifold Pressure","Engine RPM","Vehicle Speed",
			"","Intake Air Temp","","Throttle Possition"};

	static int[] bytes_returned_1 = { //SERVICE 1 PIDs BYTES RETURNED
		4,4,2,2,1,1,1,1,1,1,1,1,2,1,1,1,2,1,1,1,2,2,2,2,2,2,2,2,1,1,1,2,4,2,2,2,4,4,
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
