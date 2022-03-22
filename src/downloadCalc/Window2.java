package downloadCalc;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class Window2 extends JFrame {


	JLabel txtAreaDisplay = new JLabel();
	
	Window2(String downloadTime) {
		setSize(800, 300);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Download Speed");
		setLayout(new BorderLayout());
		setResizable(false);
		add(txtAreaDisplay,BorderLayout.NORTH);
		txtAreaDisplay.setText(downloadTime);
	}
}
