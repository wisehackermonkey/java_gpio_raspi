/*
    Java Final Project Calculator
    This project uses the Arduino with a Membrane keypad
    by Oran C
    20171204
    oranbusiness@gmail.com
*/

package edu.srjc.Final.Oran.Collins;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("UIView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args)
    { 
        launch(args);
    }

}
