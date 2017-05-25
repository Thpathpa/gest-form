package com.gestform.modele;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import com.gestform.controleur.*;

public class Test extends Application {

    public static void main(String[] args) {
        Application.launch(Test.class, args);
    }
    
	@Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.LIGHTBLUE);
        primaryStage.setScene(scene);
        
        Circle cercle = new Circle();
        cercle.setCenterX(300);
        cercle.setCenterY(200);
        cercle.setRadius(100);
        cercle.setFill(Color.YELLOW);
        cercle.setStroke(Color.ORANGE);
        cercle.setStrokeWidth(5);
        
        Rectangle rectangle = new Rectangle();
        rectangle.setX(300);
        rectangle.setY(200);
        rectangle.setWidth(300);
        rectangle.setHeight(200);
        rectangle.setFill(Color.GREEN);
        rectangle.setStroke(Color.DARKGREEN);
        rectangle.setStrokeWidth(5);
        rectangle.setArcHeight(30);
        rectangle.setArcWidth(30);
        
        // Retenez bien la fonction groupe.getChildren().add(objet), c'est grâce à elle que l'on peut ajouter un nœud graphique à un groupe quelconque. Nous l'utiliseront très souvent
        root.getChildren().add(cercle);
        root.getChildren().add(rectangle);//On ajoute le rectangle après le cercle
        primaryStage.show();
    }

}
