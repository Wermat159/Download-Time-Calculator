package downloadCalc;

import java.awt.GridLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {


	Window() {
		setSize(800,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Download Time Calculator");
		setLayout(new GridLayout(6,1));
		setResizable(false);
	}
}
