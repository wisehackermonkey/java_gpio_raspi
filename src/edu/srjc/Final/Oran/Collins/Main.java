/*
 Java Final Project Calculator using raspberry pi
    by Oran C
    20171204
    oranbusiness@gmail.com
*/

package edu.srjc.Final.Oran.Collins;
//https://embedjournal.com/interface-4x4-matrix-keypad-with-microcontroller/
//https://github.com/Fazecast/jSerialComm/wiki/Java-InputStream-and-OutputStream-Interfacing-Usage-Example
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.fazecast.jSerialComm.SerialPort;

import java.io.InputStream;
import java.util.Scanner;

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
//        SerialPort ports[] = SerialPort.getCommPorts();
//
//        System.out.println("select a port");
//        int i = 0;
//        for (SerialPort port : ports)
//        {
//            System.out.println(i++ + ": " + port.getSystemPortName());
//        }
//
//
//        SerialPort comPort = SerialPort.getCommPorts()[0];
//        comPort.openPort();
//        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
//        InputStream in = comPort.getInputStream();
//        try
//        {
//            for (int j = 0; j < 1000; ++j)
//                System.out.print((char)in.read());
//            in.close();
//        } catch (Exception e) { e.printStackTrace(); }
//        comPort.closePort();
//


//        Scanner keyboard = new Scanner(System.in);
//
//        // TODO: 12/15/2017 Error handling
//        int portSelection = keyboard.nextInt();
//
//        SerialPort port =  ports[portSelection - 1];
//
//        if( port.openPort())
//        {
//            System.out.println("Successfully opened port!");
//        }
//        else
//        {
//            System.err.println("Unable to open port selected!");
//            return;
//        }
//        //port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
//       // port = SerialPort.getCommPorts()[portSelection  - 1];
//        port.openPort();
//        port.addDataListener(new SerialPortDataListener() {
//            @Override
//            public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
//            @Override
//            public void serialEvent(SerialPortEvent event)
//            {
//                System.out.println("Serial Event !");
//                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
//                    return;
//                byte[] newData = new byte[port.bytesAvailable()];
//                int numRead = port.readBytes(newData, newData.length);
//                System.out.println("Read " + (char)numRead + " bytes.");
//            }
//        });
//        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
//        InputStream in = port.getInputStream();
//
//        try
//        {
//            for (int j = 0; j < 1000; j++)
//            {
//                 System.out.print((char) in.read());
//            }
//            in.close();
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//                port.closePort();

//        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING,0,0);
//
//        Scanner data = new Scanner(port.getInputStream());
//        while(data.hasNextLine()) {
//            int number = 0;
//            try{number = Integer.parseInt(data.nextLine());}catch(Exception e){}
//            System.out.print(String.format("%s%n",number));
//        }
        //System.err.print(String.format("ERROR %s%n",error.getMessage()));
//        System.out.println("Calculator has started please wait...");
        launch(args);
    }

}
