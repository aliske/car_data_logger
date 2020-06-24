package car_data_logger;

import javax.swing.JLabel;

public class Timer {
	
	float end_time = 0;
	long step_size = 500;
	boolean started = false;
	JLabel label;
	
	Timer(float seconds, long step_size, JLabel label){
		end_time = seconds;
		this.step_size = step_size;
		this.label = label;
		label.setText(String.valueOf(end_time));
		thread.start();
	}
	
	public void reset(float seconds)
	{
		end_time = seconds;
	}
	
	public void set_started()
	{
		started = true;
		System.out.println("thread started");
	}
	
	public float get_remainder()
	{
		return end_time;
	}
	
	Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                	if(end_time >= 0 && started == true)
                	{
                		if((end_time % 1) == 0)
            			{
            				label.setText(String.valueOf(end_time));
            			}
                		end_time -= (step_size / 1000f);
                	} else {
                		started = false;
                	}
                	Thread.sleep(step_size);
                } catch (InterruptedException ex) {
                }
            }
        }

    });
}
