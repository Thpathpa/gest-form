package com.gestform.controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;



public class MyDBConnect {
	
    /** Creates a new instance of MyDBConnection */
    public MyDBConnect() {

    }
	
	public void init() { 
	try {
        Class.forName("org.mariadb.jdbc.Driver");
            
         String url = "jdbc:mariadb://vps399745.ovh.net:3306/gest-form";
         String user = "root";
         String password = "Btssio";
            
         Connection conn = DriverManager.getConnection(url, user, password);
         //Création d'un objet Statement
         Statement state = conn.createStatement();
    }

    catch (Exception e) {
    	System.out.println("Problème de connexion à la base de données");
        e.printStackTrace();
      }
}
}

