package com.gestform.vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.gestform.controleur.MyDBConnect;

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
		return hashString.toString();
	}
	
	/**
	 * Initialisation de la vérification des champs pour se connecter
	 * 
	 */
	private void connection() {
		PreparedStatement St;
        String email = emailField.getText();
        String password = md5(passwordField.getText());
        String sql;
        ResultSet Rs;
        
        String idLicence = null;
        String idOrganisateur = null;

       try { 
           
          MyDBConnect mdbc = new MyDBConnect();
          mdbc.init();
          Connection  cnx = mdbc.getMyConnection();
                // on verifie si la requête existe
                    sql = "SELECT idLicence FROM Adherent WHERE email=? AND password =?";
                    St = (PreparedStatement) cnx.prepareStatement(sql);
                    St.setString(1, email);
                    St.setString(2, password);
                    Rs = St.executeQuery();
                    /* Mon code
                    while(Rs.next()) {
	                   idLicence=Rs.getString("idLicence"); 
                    }
                    // Si c'est bon
                    if(idLicence != null) {
                    	JOptionPane.showMessageDialog(frame, "Adherent");
                    }
                    else {
                    	
                    }
                    */
                if   (!Rs.next()) {
                // s'il n'existe pas on execute la deuxième requête
                    sql = "SELECT idOrganisateur FROM Organisateur WHERE email=? AND password =?";
                    St = (PreparedStatement) cnx.prepareStatement(sql);
                    St.setString(1, email);
                    St.setString(2, password);
                    Rs = St.executeQuery();
                            //si deux requêtes sont echoué c'est que l'utilisateur c'est trompé
                            if   (!Rs.next()) {
                            	JOptionPane.showMessageDialog(frame, "Adresse e-mail ou mot de passe incorrect");

                            }
                            // si nous avons la reponse pour deuxieme requette
                            else {
                            	JOptionPane.showMessageDialog(frame, "Organisateur");
                            	/*
                                //on met l'identifiant d'utilisateur dans la variable
                                GestForm.id=Rs.getString(1);
                                // et on affecte la valeur true à la variable org de GestForm
                                GestForm.org=true;
		                         // fermer la frame courante       
		                       	this.dispose();
		                        // nouvelle fenetre GestForm s'ouvre
		                       GestForm.FF = new GestForm();
		                       GestForm.FF.setVisible(true);
		                       */
                            }  
                }
                // autrement c'est l'utilisateur 
                 else {
                	 JOptionPane.showMessageDialog(frame, "Adherent");
                	 /*
                    // on ajoute son identifiant dans la variable     
                    GestForm.id=Rs.getString(1);
                    // et on affecte la valeur false à la variable org
                    GestForm.org=false;
                    // fermer la frame courante
                    this.dispose();
                    //on ouvre la nouvel fenetre de Class userJFrame
                    GestForm.UF = new userJFrame();
                    GestForm.UF.setVisible(true);
                    */
                }  

                    }
       
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(frame, "Adresse e-mail ou mot de passe incorrect.");
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
				//this.connexion(arg0);
				connection();
			}
		});
		/*
		connectionButton.addActionListener(this);
		public void actionPerformed(ActionEvent arg0) {
			this.connection();
		}
		*/
		connectionButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		connectionButton.setBounds(56, 315, 148, 31);
		frame.getContentPane().add(connectionButton);
	}
}
