package com.gestform.vue;

import java.awt.Font;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class Login2 {
	
	public Login2() {
		
		initContent();
	}
	
	
	public void initContent() {
	    Stage loginStage = null;
		
	    Group root = new Group();
	    loginStage.setTitle("Login");
        Scene scene = new Scene(root, 800, 600, Color.LIGHTGREY);
        loginStage.setScene(scene);
	    
        Font police = new Font("Arial", Font.BOLD, 20);
	    
	    final TextField emailField = new TextField();
	    final PasswordField passwordField = new PasswordField();
	    
	    emailField.setPromptText("Adresse E-mail");
	    passwordField.setPromptText("Mot de passe");
	    // On va ajoute un event qui permet en appuyant sur Enter après avoir saisi le mot de passe, d'aller lancer la connexion
	    passwordField.setOnAction(new EventHandler<ActionEvent>() { 
	        @Override public void handle(ActionEvent e) { 
	            // Validation du mot de passe.
	            // Réaction en cas de mot de passe valide.
	            // Réaction en cas de mot de passe non valide.
	            passwordField.clear();
	        }
	    });
	    // Déclaration du button de connexion
        final Button button = new Button("Connexion");
        button.setOnAction(actionEvent -> System.out.println("Connexion..."));
        
        loginStage.show();
	}
	  /**
	  * Initialise le contenu de la fenêtre
	  
	  public void initContent(){
	    //Vous connaissez ça...
	    result.setLayout(new BorderLayout());
	    split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(text), result);
	    split.setDividerLocation(100);
	    getContentPane().add(split, BorderLayout.CENTER);
	  }
	  */
}
