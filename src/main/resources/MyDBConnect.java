package main.resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class MyDBConnect {
	
    private Connection myConnection;
    Statement stmt;
    ResultSet rs;
    
    /** Creates a new instance of MyDBConnection */
    public MyDBConnect() {
    	init();
    }
	
	public void init() { 
	try {
        Class.forName("org.mariadb.jdbc.Driver");
            
        String url = "jdbc:mariadb://vps399745.ovh.net:3306/gest-form";
         String user = "root";
         String password = "Btssio";
            
         myConnection = DriverManager.getConnection(url, user, password);
    	}

    catch (Exception e) {
    	System.out.println("Problème de connexion à la base de données");
        e.printStackTrace();
      	}
	}
	
    public Connection getMyConnection(){
        return myConnection;
    }
    
    public void close(ResultSet rs){
        if(rs !=null){
            try{
               rs.close();
            }
            catch(Exception e){
            	System.out.println("Problème de fermeture du ResultSet");
            }
        }
    }
    
     public void close(java.sql.Statement stmt){
        
        if(stmt !=null){
            try{
               stmt.close();
            }
            catch(Exception e){
            	System.out.println("Problème de fermeture du Statement");
            }
        }
    }
     
  public void destroy(){
  
    if(myConnection !=null){
    
         try{
               myConnection.close();
            }
            catch(Exception e){
            System.out.println("Problème de déconnexion à la base de données");
            }
    }
  }
}

