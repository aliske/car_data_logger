package car_data_logger;

import java.awt.Color;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UI_Screen_Main {
	static JFrame f = new JFrame();
	static JButton b = new JButton("click");
	static JLabel main_label = new JLabel("Aaron Liske's OBDII Data Recorder");
	static JLabel ports_label = new JLabel("Queried Ports:");
	static JLabel timer_label = new JLabel("999");
	static JLabel database_host_label = new JLabel("Database Host:");
	static JLabel selected_port_label = new JLabel("Currently Using Port: ");
	static JTextField database_host_text = new JTextField("localhost");
	static JLabel database_name_label = new JLabel("Database Name:");
	static JTextField database_name_text = new JTextField("obdii_data_logger");
	static JLabel database_username_label = new JLabel("Database Username:");
	static JTextField database_username_text = new JTextField("");
	static JLabel database_password_label = new JLabel("Database Password:");
	static JPasswordField database_password_text = new JPasswordField("");
	static DefaultListModel<String> l1 = new DefaultListModel<>();
	static JList<String> port_list = new JList<>(l1);
	UI_Screen_Main() {
	}
	
	public static void show() {
		main_label.setBounds(10,10,800, 30);
		ports_label.setBounds(10,60,800,24);
		port_list.setBounds(10,80,780,100);
		selected_port_label.setBounds(10,180,800,30);
		database_host_label.setBounds(10,200,800,30);
		database_host_text.setBounds(10,225,780,30);
		database_name_label.setBounds(10,255,800,30);
		database_name_text.setBounds(10,280,780,30);
		database_username_label.setBounds(10,310,790,30);
		database_username_text.setBounds(10,335,780,30);
		database_password_label.setBounds(10,365,800,30);
		database_password_text.setBounds(10,390,780,30);
		timer_label.setBounds(725,430,75,50);
		main_label.setFont(new Font("Courier", 24, 24));
		timer_label.setFont(new Font("Courier", 24, 24));
		timer_label.setForeground(Color.RED);
		b.setBounds(130,100,100,40);
		//f.add(b);
		f.add(main_label);
		f.add(ports_label);
		f.add(port_list);
		f.add(timer_label);
		f.add(database_host_label);
		f.add(database_host_text);
		f.add(database_name_label);
		f.add(database_name_text);
		f.add(database_username_label);
		f.add(database_username_text);
		f.add(database_password_label);
		f.add(database_password_text);
		f.add(selected_port_label);
		f.setSize(800,480);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setUndecorated(true);
		f.setVisible(true);
		//f.setAlwaysOnTop(true);

	}
}
