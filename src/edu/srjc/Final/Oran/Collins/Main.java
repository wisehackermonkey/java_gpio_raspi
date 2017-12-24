/*
 Java Final Project Calculator using raspberry pi
    by Oran C
    20171204
    oranbusiness@gmail.com
*/

package edu.srjc.Final.Oran.Collins;
//https://embedjournal.com/interface-4x4-matrix-keypad-with-microcontroller/
//https://github.com/Fazecast/jSerialComm/wiki/Java-InputStream-and-OutputStream-Interfacing-Usage-Example
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    { 
        launch(args);
    }

}
