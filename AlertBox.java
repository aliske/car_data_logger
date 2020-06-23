/*
 * Aaron Liske
 * Car Data Logger
 * Alert Box Class using JavaFX
 */

package car_data_logger;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AlertBox {
	private static JDialog d;  
	public static void display(String title, String message) {
		JFrame f= new JFrame();  
        d = new JDialog(f , title, true);  
        d.setLayout( new GridLayout(2,1) );  
        JButton b = new JButton ("OK");  
        b.addActionListener ( new ActionListener()  
        {  
            public void actionPerformed( ActionEvent e )  
            {  
                AlertBox.d.setVisible(false);  
            }  
        });  
        d.add( new JLabel (message));  
        d.add(b);   
        d.setSize(750,150);    
        d.setVisible(true);  
	}
}
