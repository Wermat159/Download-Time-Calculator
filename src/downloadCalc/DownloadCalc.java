package downloadCalc;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class DownloadCalc extends Window implements ActionListener {


	JLabel lblDownloadSpeed = new JLabel("Download Speed: ");
	JLabel lblDownloadSize = new JLabel("Download Size: ");

	private String tabSpace = "                               ";

	JLabel lblSpeed = new JLabel(tabSpace + tabSpace + tabSpace + "What is the speed of your internet?".toUpperCase());
	JLabel lblSize = new JLabel(tabSpace + tabSpace + tabSpace + "What is the size of your download?".toUpperCase());

	JTextField txtSpeed = new JTextField();
	JTextField txtSize = new JTextField();

	Vector<String> listSpeedType = new Vector<String>();
	JComboBox<String> cmbSpeedType = new JComboBox<String>(listSpeedType);

	Vector<String> listSizeType = new Vector<String>();
	JComboBox<String> cmbSizeType = new JComboBox<String>(listSizeType);

	JPanel pnlSpeed = new JPanel(new GridLayout(1, 3));
	JPanel pnlSize = new JPanel(new GridLayout(1, 3));

	JButton btnCalculate = new JButton("CALCULATE");
	JButton btnSpeedTestSite = new JButton("Don't know your download speed?");

	public DownloadCalc() {
		Collections.addAll(listSpeedType, "", "Kbps", "Mbps", "Gbps");
		Collections.addAll(listSizeType, "", "KB", "MB", "GB");

		pnlSpeed.add(lblDownloadSpeed);
		pnlSpeed.add(txtSpeed);
		pnlSpeed.add(cmbSpeedType);

		pnlSize.add(lblDownloadSize);
		pnlSize.add(txtSize);
		pnlSize.add(cmbSizeType);

		add(lblSpeed);
		add(pnlSpeed);
		add(lblSize);
		add(pnlSize);
		add(btnCalculate);
		add(btnSpeedTestSite);
		btnCalculate.addActionListener(this);
		btnSpeedTestSite.addActionListener(this);
		cmbSizeType.setSelectedIndex(0);
		cmbSpeedType.setSelectedIndex(0);
		setVisible(true);
	}

	public static void main(String[] args) {
		new DownloadCalc();
	}

	private static double getSpeedType(JComboBox<String> cmbSpeed, double downloadSpeed) {
		if (cmbSpeed.getSelectedIndex() == 1) {
			return downloadSpeed * 0.001;
		} else if (cmbSpeed.getSelectedIndex() == 2) {
			return downloadSpeed;
		} else if (cmbSpeed.getSelectedIndex() == 3) {
			return downloadSpeed * 1000;
		}
		return 0;
	}

	private static boolean checkBoxesSelected(JComboBox<String> cmbSizeType, JComboBox<String> cmbSpeedType) {
		if (cmbSizeType.getSelectedIndex() == 0 || cmbSpeedType.getSelectedIndex() == 0) {
			return false;
		} else {
			return true;
		}
	}

	private static double getSizeType(JComboBox<String> cmbSize, double size) {
		if (cmbSize.getSelectedIndex() == 1) {
			return size * 0.008;
		} else if (cmbSize.getSelectedIndex() == 2) {
			return size * 8;
		} else if (cmbSize.getSelectedIndex() == 3) {
			return size * 8 * 1000;
		}
		return 0;
	}

	private static String formatTime(double time) {
		if (time > 3600) {
			double hour = time / 3600;
			double minute = (time % 3600) / 60;
			double second = time % 60;
			return Math.round(hour) + " hour(s), " + Math.round(minute) + " minute(s) and " + Math.round(second)
					+ " seconds.";
		} else if (time < 3600 && time > 60) {
			double minute = (time % 3600) / 60;
			double second = time % 60;
			return Math.round(minute) + " minute(s) and " + Math.round(second) + " second(s).";
		} else if (time < 60 && time > 0) {
			double second = time % 60;
			return Math.round(second) + " second(s).";
		}
		return "";
	}

	/*
	 * Check if the syntax is ok
	 * 
	 */
	
	private static boolean txtFieldChecker(JTextField txtSpeed, JTextField txtSize) {

		if (txtSpeed.getText().matches("\\d+\\.\\d+") && txtSize.getText().matches("\\d+\\.\\d+")) {
			return true;
		} else if (txtSpeed.getText().matches("\\d+") && txtSize.getText().matches("\\d+")) {
			return true;
		} else if ((txtSpeed.getText().matches("\\d+\\.\\d+") && txtSize.getText().matches("\\d+"))
				|| (txtSpeed.getText().matches("\\d+") && txtSize.getText().matches("\\d+\\.\\d+"))) {
			return true;
		} else {
			return false;
		}
	}

	private static void goToSpeedTestNet() {

		/*
		 * 
		 * When button is clicked go to speedtest.net to find out your internet speed
		 * 
		 */
		
		String url = "https://www.speedtest.net/";
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (IOException | URISyntaxException e) {
			JOptionPane.showMessageDialog(null, "An error has occurred.");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCalculate) {
			boolean checked = checkBoxesSelected(cmbSizeType, cmbSpeedType);
			if (checked == false) {
				JOptionPane.showMessageDialog(null, "Please choose a type.");
			} else if ((txtSpeed.getText().isBlank() && txtSpeed.getText().isEmpty())
					|| (txtSize.getText().isBlank() && txtSize.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null, "Both boxes must be filled.");
			} else if (txtFieldChecker(txtSpeed, txtSize) == true) {
				double calculatedSpeed = getSpeedType(cmbSpeedType, Double.parseDouble(txtSpeed.getText()));
				double calculatedSize = getSizeType(cmbSizeType, Double.parseDouble(txtSize.getText()));
				double time = (calculatedSize / calculatedSpeed);
				System.out.println(time);
				new Window2(formatTime(time)).setVisible(true);
			} else if (txtFieldChecker(txtSpeed, txtSize) == false) {
				JOptionPane.showMessageDialog(null, "Only enter numbers please.");
				txtSpeed.setText("");
				txtSize.setText("");
			}
		} else if (e.getSource() == btnSpeedTestSite) {
			goToSpeedTestNet();
		}
	}
}
