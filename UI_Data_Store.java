/*
 * Aaron Liske
 * Car Data Logger
 * Data Storage class for label values for monitor.
 * Work around for the threading issue. 
 */

package car_data_logger;

import java.sql.Timestamp;

public class UI_Data_Store {
	static String rpm = "";
	static String intake_temp = "";
	static String coolant_temp = "";
	static String oil_temp = "";
	static String speed = "";
	static String throttle = "";
	static String fuel = "";
	static Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	static String current_data = "";
}
