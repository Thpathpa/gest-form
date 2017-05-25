package com.gestform.modele;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
	        Class.forName("org.mariadb.jdbc.Driver");
	            
	         String url = "jdbc:mariadb://vps399745.ovh.net:3306/gest-form";
	         String user = "root";
	         String password = "Btssio";
	         
	         // Informations de connexion à la base de données
	         Connection conn = DriverManager.getConnection(url, user, password);
	         // Création d'un objet Statement
	         // permet d'exécuter des instructions SQL, state interroge la base de données et retourne les résultats
	         Statement state = conn.createStatement();
	         
	         // query contient ma requête sql
	         String query = "SELECT nom, prenom, email FROM adherent WHERE 1";
	         
	         //L'objet ResultSet contient le résultat de la requête SQL
	         ResultSet result = state.executeQuery(query);
	         
	         //On récupère les MetaData constituent en réalité un ensemble de données servant à décrire une structure. Dans notre cas, elles permettent de connaître le nom des tables, des champs, leur type
	         ResultSetMetaData resultMeta = result.getMetaData();
	         
	         System.out.println("\n**********************************");
	         // On affiche le nom des colonnes
	         // Contrairement aux indices de tableaux, les indices de colonnes SQL commencent à 1 
	         for(int i = 1; i <= resultMeta.getColumnCount(); i++)
	           System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
	            
	         System.out.println("\n**********************************");
	            
	         while(result.next()){         
	           for(int i = 1; i <= resultMeta.getColumnCount(); i++)
	             System.out.print("\t" + result.getObject(i).toString() + "\t |");
	               
	           System.out.println("\n---------------------------------");

	         }
	         // On ferme nos requêtes
	         result.close();
	         state.close();

	         
	    }

	    catch (Exception e) {
	    	System.out.println("Problème de connexion à la base de données");
	        e.printStackTrace();
	      }
	
	}

}
