package test;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Test {

	private JFrame frmTest;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test window = new Test();
					window.frmTest.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTest = new JFrame();
		frmTest.setTitle("Test");
		frmTest.setBounds(100, 100, 657, 572);
		frmTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
