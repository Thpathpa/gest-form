package com.gestform.vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;

import javax.swing.JPasswordField;
import javax.swing.JButton;

import com.gestform.controleur.*;

public class Login {

	private JFrame frame;
	private JTextField emailField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
	    frame.setSize(900, 600);
	    frame.setTitle("Login");
		frame.setBounds(100, 100, 509, 503);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel imageBackgroundLabel = new JLabel("");
		imageBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logom2l.png")));
		imageBackgroundLabel.setBounds(221, 19, 264, 368);
		frame.getContentPane().add(imageBackgroundLabel);
		
		JLabel identifiezLabel = new JLabel("Identifiez-vous");
		identifiezLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		identifiezLabel.setVerticalAlignment(SwingConstants.TOP);
		identifiezLabel.setBounds(42, 109, 187, 29);
		frame.getContentPane().add(identifiezLabel);
		
		JLabel emailLabel = new JLabel("Adresse E-Mail :");
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		emailLabel.setBounds(42, 165, 166, 23);
		frame.getContentPane().add(emailLabel);
		
		emailField = new JTextField();
		emailField.setBounds(42, 193, 187, 29);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Mot de passe :");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		passwordLabel.setBounds(42, 241, 145, 23);
		frame.getContentPane().add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(42, 267, 187, 29);
		frame.getContentPane().add(passwordField);
		
		JButton connectionButton = new JButton("Connexion");
		connectionButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
				String email = emailField.getText();
				String password = passwordField.getText();
				
				// transformation du password saisi au format md5
				byte[] bytesOfMessage = null;
				try {
					bytesOfMessage = password.getBytes("UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				MessageDigest passwordMD5 = null;
				try {
					passwordMD5 = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				byte[] thedigest = passwordMD5.digest(bytesOfMessage); 
				// Le hash est présent sous forme de tableau de byte

				BigInteger bigInt = new BigInteger(1,thedigest);
				String hashtext = bigInt.toString(16);
				while(hashtext.length() < 32 ){
				  hashtext = "0"+hashtext;
				}
				// Le hash est présent sous la forme de String
				
				JOptionPane.showMessageDialog(frame, "Connexion Réussi");
				
			}
		});
		connectionButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		connectionButton.setBounds(56, 315, 148, 31);
		frame.getContentPane().add(connectionButton);
	}
}
