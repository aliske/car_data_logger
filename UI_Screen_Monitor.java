package car_data_logger;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class UI_Screen_Monitor {
	public static boolean displayed = false;
	static JFrame f = new JFrame();
	static JLabel speed_label = new JLabel("Speed");
	static JLabel fuel_label = new JLabel("Fuel Level");
	static JLabel throttle_label = new JLabel("Throttle");
	static JLabel coolant_label = new JLabel("Coolant Temp");
	static JLabel oil_label = new JLabel("Oil Temp");
	static JLabel intake_label = new JLabel("Intake Temp");
	static JLabel rpm_label = new JLabel("Engine RPM");
	
	static JLabel speed_value_label = new JLabel("0");
	static JLabel fuel_value_label = new JLabel("0");
	static JLabel throttle_value_label = new JLabel("0");
	static JLabel coolant_value_label = new JLabel("0");
	static JLabel intake_value_label = new JLabel("0");
	static JLabel rpm_value_label = new JLabel("0");
	
	public static void show() {
		f.setSize(675,400);
		speed_label.setHorizontalAlignment(SwingConstants.CENTER);
		speed_label.setBounds(0, 70, 225, 30);
		speed_label.setFont(new Font("Courier", 24, 24));
		speed_label.setForeground(Color.white);
		
		fuel_label.setHorizontalAlignment(SwingConstants.CENTER);
		fuel_label.setBounds(225, 70, 225, 30);
		fuel_label.setFont(new Font("Courier", 24, 24));
		fuel_label.setForeground(Color.white);
		
		throttle_label.setHorizontalAlignment(SwingConstants.CENTER);
		throttle_label.setBounds(450, 70, 225, 30);
		throttle_label.setFont(new Font("Courier", 24, 24));
		throttle_label.setForeground(Color.white);
		
		speed_value_label.setHorizontalAlignment(SwingConstants.CENTER);
		speed_value_label.setBounds(0, 100, 225, 60);
		speed_value_label.setFont(new Font("Courier", 56, 56));
		speed_value_label.setForeground(Color.white);
		
		fuel_value_label.setHorizontalAlignment(SwingConstants.CENTER);
		fuel_value_label.setBounds(225, 100, 225, 60);
		fuel_value_label.setFont(new Font("Courier", 56, 56));
		fuel_value_label.setForeground(Color.white);
		
		throttle_value_label.setHorizontalAlignment(SwingConstants.CENTER);
		throttle_value_label.setBounds(450, 100, 225, 60);
		throttle_value_label.setFont(new Font("Courier", 56, 56));
		throttle_value_label.setForeground(Color.white);
		
		
		
		rpm_label.setHorizontalAlignment(SwingConstants.CENTER);
		rpm_label.setBounds(225, 250, 225, 30);
		rpm_label.setFont(new Font("Courier", 24, 24));
		rpm_label.setForeground(Color.white);
		
		coolant_label.setHorizontalAlignment(SwingConstants.CENTER);
		coolant_label.setBounds(0, 250, 225, 30);
		coolant_label.setFont(new Font("Courier", 24, 24));
		coolant_label.setForeground(Color.white);
		
		intake_label.setHorizontalAlignment(SwingConstants.CENTER);
		intake_label.setBounds(450, 250, 225, 30);
		intake_label.setFont(new Font("Courier", 24, 24));
		intake_label.setForeground(Color.white);
		
		
		
		
		rpm_value_label.setHorizontalAlignment(SwingConstants.CENTER);
		rpm_value_label.setBounds(225, 280, 225, 60);
		rpm_value_label.setFont(new Font("Courier", 48, 48));
		rpm_value_label.setForeground(Color.white);
		
		coolant_value_label.setHorizontalAlignment(SwingConstants.CENTER);
		coolant_value_label.setBounds(0, 280, 225, 60);
		coolant_value_label.setFont(new Font("Courier", 56, 56));
		coolant_value_label.setForeground(Color.white);

		intake_value_label.setHorizontalAlignment(SwingConstants.CENTER);
		intake_value_label.setBounds(450, 280, 225, 60);
		intake_value_label.setFont(new Font("Courier", 56, 56));
		intake_value_label.setForeground(Color.white);
		
		
		f.getContentPane().setBackground(Color.DARK_GRAY);
		f.setLayout(null);
		f.add(speed_label);
		f.add(fuel_label);
		f.add(throttle_label);
		f.add(coolant_label);
		f.add(oil_label);
		f.add(intake_label);
		f.add(rpm_label);
		f.add(speed_value_label);
		f.add(fuel_value_label);
		f.add(throttle_value_label);
		f.add(coolant_value_label);
		f.add(intake_value_label);
		f.add(rpm_value_label);
		//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setUndecorated(true);
		f.setVisible(true);
		//f.setAlwaysOnTop(true);
		displayed = true;
	}
}
