package com.gestform.vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import main.resources.MyDBConnect;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import javax.swing.Action;

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
	// Fonction pour tranformer le password saisie
	private static String md5(String password) {
		byte[] uniqueKey = password.getBytes();
		byte[] hash      = null;
		
        try
        {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Error("Pas de support MD5");
        }
		
		StringBuilder hashString = new StringBuilder();
		for (int i = 0; i < hash.length; i++)
		{
		        String hex = Integer.toHexString(hash[i]);
		        if (hex.length() == 1)
		        {
		                hashString.append('0');
		                hashString.append(hex.charAt(hex.length() - 1));
		        }
		        else
		                hashString.append(hex.substring(hex.length() - 2));
		}
		return hashString.toString(); // Retourne le mot de passe en format md5
	}
	
	/**
	 * Initialisation de la vérification des champs pour se connecter
	 * 
	 */
	private void connection() {
		
		// Récupération des paramètres saisis par l'utilisateur
        String email = emailField.getText();
        String password = md5(passwordField.getText());
        
        String sql;
        ResultSet Rs;
        PreparedStatement St;
        
       try { 
          MyDBConnect mdbc = new MyDBConnect();
          mdbc.init();
          Connection  cnx = mdbc.getMyConnection();
                // on verifie si la requête existe
                    sql = "SELECT idLicence FROM adherent WHERE email=? AND password =?";
                    // Création de l'objet gérant les requêtes préparées
                    St = (PreparedStatement) cnx.prepareStatement(sql);
                    // Remplissage des paramètres de la requête grâce aux méthodes
                    // setXXX() mises à disposition par l'objet PreparedStatement.
        			St.setString(1, email);
        			St.setString(2, password);
        			Rs = St.executeQuery();
	        		// s'il n'existe pas on execute la deuxième requête
	                if (!Rs.next()) {
	                    sql = "SELECT idOrganisateur FROM organisateur WHERE email=? AND password =?";
	                    St = (PreparedStatement) cnx.prepareStatement(sql);
	                    St.setString(1, email);
	                    St.setString(2, password);
	                    Rs = St.executeQuery();
	                    //si deux requêtes sont echoué c'est que l'utilisateur c'est trompé
                        if (!Rs.next()) {
                            JOptionPane.showMessageDialog(frame, "Adresse e-mail ou mot de passe incorrect");
                            }
                            // si nous avons la reponse pour la deuxième requête on se connecte au panel Organisateur
                        else {
                        	//JOptionPane.showMessageDialog(frame, "Organisateur");
                            // on ajoute son identifiant dans la variable     
                            Organisateur.idOrganisateur=Rs.getString(1);
                            // on ferme la frame courante
                            frame.dispose();
                            // on ouvre la fenêtre de la Class Organisateur
                            String[] args = null;
                    		Organisateur.main(args);
                            }  
                }
                // autrement c'est la connexion au panel Adherent
                 else {
                	 //JOptionPane.showMessageDialog(frame, "Adherent");              	 
                    // on ajoute son identifiant dans la variable     
                    Adherent.idLicence=Rs.getString(1);
                    // on ferme la frame courante
                    frame.dispose();
                    // on ouvre la fenêtre de la Class Adherent
                    String[] args = null;
            		Adherent.main(args); 
                }  

                    }
       				// Sinon problème dans la requête
                    catch (SQLException e) {
                        JOptionPane.showMessageDialog(frame, "Probleme de requête");
                    } 
}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
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
		emailField.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
				connection();
			}
		});
		emailField.setBounds(42, 193, 187, 29);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Mot de passe :");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		passwordLabel.setBounds(42, 241, 145, 23);
		frame.getContentPane().add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
				connection();
			}
		});
		passwordField.setBounds(42, 267, 187, 29);
		frame.getContentPane().add(passwordField);
		
		JButton connectionButton = new JButton("Connexion");
		connectionButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
				connection();
			}
		});
		connectionButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		connectionButton.setBounds(56, 315, 148, 31);
		frame.getContentPane().add(connectionButton);
	}
}
